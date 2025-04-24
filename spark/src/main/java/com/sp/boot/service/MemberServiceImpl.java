package com.sp.boot.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sp.boot.dao.MemberDao;
import com.sp.boot.dto.JwtToken;
import com.sp.boot.dto.LikeDto;
import com.sp.boot.dto.LoginResult;
import com.sp.boot.dto.LogoutResult;
import com.sp.boot.dto.MemberDto;
import com.sp.boot.dto.TokenResult;
import com.sp.boot.exception.LoginFailException;
import com.sp.boot.util.FileUtil;
import com.sp.boot.util.JwtProvider;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	
	private final MemberDao memberDao;
	private final JwtProvider jwtProvider;
	private final BCryptPasswordEncoder bcryptPwdEncoder;
	private final FileUtil fileUtil;

	@Override
	public LoginResult login(MemberDto m) {
		
		// 1. 유저 확인
	    MemberDto memberDto = memberDao.login(m);
	    
	    if(memberDto != null && bcryptPwdEncoder.matches(m.getMemPwd(), memberDto.getMemPwd())){
	    	// 2. 토큰 생성
	    	String accessToken = jwtProvider.createToken(m.getMemId()); // access 토큰 발급
	    	String refreshToken = jwtProvider.createRefreshToken(m.getMemId());  // refresh 토큰 발급
	    	Map<String,Object> map = new HashMap<>();
	    	map.put("memId", m.getMemId());
	    	map.put("refreshToken", refreshToken);
	    	int result = memberDao.insertRefreshToken(map); // db에 refreshtoken 저장
	    	
	    	JwtToken token = JwtToken.builder()
	    			.grantType("Bearer")
	    			.accessToken(accessToken)
	    			.build();
	    	
	    	Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
	    	refreshCookie.setHttpOnly(true); // javascript에서 접근 차단
	    	refreshCookie.setPath("/"); // 어디에 쿠키를 쓸지 경로 설정
	    	refreshCookie.setSecure(false); // https 환경에서만 사용가능 현재는 false로 막아둔상태 ssl적용x 
	    	refreshCookie.setMaxAge(60 * 60 * 24 * 7); // 7일

		    return new LoginResult(token, memberDto,refreshCookie);
	    }else {
	    	throw new RuntimeException("아이디 또는 비밀번호가 틀렸습니다.");
	    }

	}

	@Override
	public Map<String,Object> loginUserInfo(String authHeader) {
		
		Map<String,Object> map = new HashMap<>();
    	
    	String validateToken = authHeader.replace("Bearer ", ""); // 토큰만 뺴내어
    	
        if (!jwtProvider.validateToken(validateToken)) { // 유효성 검사 진행
            map.put("valid", false);
            return map;
        }
    	String token = jwtProvider.getUserId(validateToken);
    	MemberDto memberDto = memberDao.loginUserInfo(token);

    	map.put("valid", true);
    	map.put("memberDto", memberDto);
		
		return map;
	}

	@Override
	public MemberDto findById(String userId) {
		return memberDao.findById(userId);
	}

	@Override
	public TokenResult insertRefreshToken(String refreshTokenHeader) {
		
		String refreshToken = refreshTokenHeader.replace("Bearer ", "");

        if (!jwtProvider.validateToken(refreshToken)) {
            throw new LoginFailException("Refresh Token이 유효하지 않습니다.");
        }
		
	
        String userId = jwtProvider.getUserId(refreshToken);
        String accessToken = jwtProvider.createToken(userId);
        String newRefreshToken = jwtProvider.createRefreshToken(userId);
		
		
        Map<String, Object> map = new HashMap<>();
        map.put("memId", userId);
        map.put("refreshToken", newRefreshToken);
        int result = insertRefreshToken(map);
		
        Cookie refreshCookie = null;
        if(result > 0 ) {
        	refreshCookie = new Cookie("refreshToken", newRefreshToken);
        	refreshCookie.setHttpOnly(true); // javascript에서 접근 차단
        	refreshCookie.setPath("/"); // 어디에 쿠키를 쓸지 경로 설정
        	refreshCookie.setSecure(false); // https 환경에서만 사용가능 현재는 false로 막아둔상태
        	refreshCookie.setMaxAge(60 * 60 * 24 * 7); // 7일

        }
        JwtToken jwtToken = JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();

        
        return new TokenResult(jwtToken,refreshCookie);
		
	}

	@Override
	public LogoutResult deleteToken(String refreshTokenHeader) {
		
		
        String refreshToken = refreshTokenHeader.replace("Bearer ", ""); // Bearer를 제거해 token만 추출

        if (!jwtProvider.validateToken(refreshToken)) { // 유효성 검사
            return new LogoutResult(false,null);
        }

        String userId = jwtProvider.getUserId(refreshToken); // 토큰 안에있는 userId 가져오기
        int result = memberDao.deleteToken(userId); // 토큰 db에서 제거
    	
        if(result > 0) {
            Cookie deleteCookie = new Cookie("refreshToken", null);
            deleteCookie.setPath("/");
            deleteCookie.setHttpOnly(true);
            deleteCookie.setSecure(false);
            deleteCookie.setMaxAge(0); // 0초 즉시 만료
            
        	return new LogoutResult(true,deleteCookie);
        }
		
		return new LogoutResult(false,null);
	}


	public int insertRefreshToken(Map<String,Object> map) {
		
		int result = memberDao.checkRefreshToken(map);
		
		if(result > 0) {
			int result2 = memberDao.updateRefreshToken(map);
			return result2;

		}else {
			return memberDao.insertRefreshToken(map);
		}
		
	}
	
	@Override
	public String smsCode(String phone) {
		
		// 가입된 사용자인지 먼저 검사
		
		MemberDto result = memberDao.findById(phone);
		
		if(result != null) {
			throw new LoginFailException("이미 가입된 회원입니다.");
		}
		
		
    	int num[] = new int[6];
    	
    	for(int i = 0;i< num.length;i++) {
    		num[i] = (int)(Math.random() * 9) + 1;
    	}
    	
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < num.length; i++) {
    	    sb.append(num[i]);
    	}
    	String code = sb.toString();

    	DefaultMessageService messageService =  NurigoApp.INSTANCE.initialize("NCSERQEIBVBBZJKR", "NPEW4QVQX3KP5A5V7EQLJKJ8M7PHWOWO", "https://api.coolsms.co.kr");
    	// Message 패키지가 중복될 경우 net.nurigo.sdk.message.model.Message로 치환하여 주세요
    	Message message = new Message();
    	message.setFrom("01055106509");
    	message.setTo(phone);
    	message.setText("spark 인증 번호는 : [ "+ code +" ] 입니다");

    	try {
    	  // send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
    	  messageService.send(message);
    	} catch (NurigoMessageNotReceivedException exception) {
    	  // 발송에 실패한 메시지 목록을 확인할 수 있습니다!
    	  System.out.println(exception.getFailedMessageList());
    	  System.out.println(exception.getMessage());
    	} catch (Exception exception) {
    	  System.out.println(exception.getMessage());
    	}    	
    	
    	return code;
		
	}

	@Override
	public List<MemberDto> recommendList(MemberDto m) {

	    Map<String, Object> map = new HashMap<>();
	    map.put("memId", m.getMemId());
		
	    String[] interestArr = m.getInterest().split(",");
	    String[] characterArr = m.getCharacter().split(",");
	    String[] tendenciesArr = m.getTendencies().split(",");
	    
	    map.put("interest", interestArr);
	    map.put("character", characterArr);
	    map.put("tendencies", tendenciesArr);
	    
	    
		List<MemberDto> list = memberDao.recommendList(map);
		
		Collections.shuffle(list);
		
		List<MemberDto> result = list.subList(0, Math.min(50, list.size()));
		
		// 현재 년도
		int currentYear = Year.now().getValue();
	
		
		for(int i = 0; i < result.size(); i++) {
			int birthYear = result.get(i).getBirthDate2().toLocalDate().getYear();
			result.get(i).setAge(currentYear - birthYear);
		}
		
		
		return result;
	}

	@Override
	public int signUp(MemberDto m) {
		
		// 비밀번호 암호화
		m.setMemPwd(bcryptPwdEncoder.encode(m.getMemPwd()));
		
		int memberDto = memberDao.signUp(m);
		
		
		return memberDto;
	}

	@Override
	public int insertInfo(MemberDto m, MultipartFile uploadFile) {
		
		String userId = SecurityContextHolder.getContext().getAuthentication().getName(); // 현재 로그인 중인 회원의 아이디값 불러오기
		
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd"); // 포맷 정의 
        LocalDate localDate = LocalDate.parse(m.getBirthDate(), formatter); // localdate 날짜전용 객체에 formatter에 정의한 형식으로 담기
        m.setBirthDate2(Date.valueOf(localDate)); // sql.date 타입으로 변경
		m.setMemId(userId);
		
	    if (!uploadFile.isEmpty()) {
	        Map<String, String> map = fileUtil.fileupload(uploadFile, "profile");
	        String filePath = map.get("filePath") + "/" + map.get("filesystemName");
	        m.setProFile(filePath);
	    } else {
	        m.setProFile(null);
	    }
		
		return memberDao.insertInfo(m);
	}

	@Override
	public int recommendDelete(Map<String,String> map) {		
		return memberDao.recommendDelete(map);
	}

	@Override
	public int likeMember(Map<String,String> map) {
		
		// 좋아요 테이블에 이미 저장되어있는지 확인
		LikeDto result = memberDao.likeMemberCheck(map);
		
		if(result != null) {
			throw new LoginFailException("이미 좋아요를 누른상태입니다.");
		}else {
			return memberDao.likeMember(map);
		}
		
	}

	@Override
	public String duplicateCheck(String nickName) {
		
		int result = memberDao.duplicateCheck(nickName);
		
		if(result>0) {
			return "이미 사용중인 닉네임입니다.";
		}else {
			return "사용 가능한 닉네임입니다.";
		}

	}

	
	
	

	

}
