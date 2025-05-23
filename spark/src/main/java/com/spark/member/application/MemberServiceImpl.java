package com.spark.member.application;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spark.member.repository.MemberDao;
import com.spark.base.exception.CustomException;
import com.spark.base.util.FileUtil;
import com.spark.base.util.JwtProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spark.member.dto.JwtToken;
import com.spark.member.dto.LikeDto;
import com.spark.member.dto.LoginResult;
import com.spark.member.dto.LogoutResult;
import com.spark.member.dto.MemberDto;
import com.spark.member.dto.TokenResult;

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
	    
	    System.out.println("로그인 :" + memberDto);
	    
	    if(memberDto != null && bcryptPwdEncoder.matches(m.getMemPwd(), memberDto.getMemPwd())){
	    	// 2. 토큰 생성
	    	String accessToken = jwtProvider.createToken(m.getMemId()); // access 토큰 발급
	    	String refreshToken = jwtProvider.createRefreshToken(m.getMemId());  // refresh 토큰 발급
	    	Map<String,Object> map = new HashMap<>();
	    	map.put("memId", m.getMemId());
	    	map.put("refreshToken", refreshToken);
	    	int result = memberDao.insertRefreshToken(map); // db에 refreshtoken 저장
	    	
	    	if(result == 0) {
	    		throw new CustomException("리프레시 토큰 저장에 실패하였습니다.",500);
	    	}
	    	
	    	JwtToken token = JwtToken.builder()
	    			.grantType("Bearer")
	    			.accessToken(accessToken)
	    			.build();
	    	
	    	Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
	    	refreshCookie.setHttpOnly(true); // javascript에서 접근 차단
	    	refreshCookie.setPath("/"); // 어디에 쿠키를 쓸지 경로 설정
	    	refreshCookie.setSecure(false); // https 환경에서만 사용가능 현재는 false로 막아둔상태 ssl적용x 
	    	refreshCookie.setMaxAge(60 * 60 * 24 * 7); // 7일
	    	
	    	memberDto.setMemPwd(null);

		    return new LoginResult(token, memberDto,refreshCookie);
	    }else {
	    	throw new CustomException("아이디 또는 비밀번호가 틀렸습니다.",401);
	    }

	}

	@Override
	public Map<String,Object> loginUserInfo(String authHeader) {
		
		Map<String,Object> map = new HashMap<>();
    	
    	String validateToken = authHeader.replace("Bearer ", ""); // 토큰만 뺴내어
    	
        if (!jwtProvider.validateToken(validateToken)) { // 유효성 검사 진행
            throw new CustomException("유효하지 않은 토큰입니다.",401);
        }
    	String token = jwtProvider.getUserId(validateToken);
    	MemberDto memberDto = memberDao.loginUserInfo(token);

    	map.put("valid", true);
    	map.put("memberDto", memberDto);
		
		return map;
	}

	@Override
	public MemberDto findById(String userId) {
		
		MemberDto result = memberDao.findById(userId);
		if(result == null) {
			throw new CustomException("해당 사용자가 존재하지 않습니다",403);			
		}	
		return result;
		
	}

	@Override
	public TokenResult insertRefreshToken(String refreshTokenHeader) {
		
		String refreshToken = refreshTokenHeader.replace("Bearer ", "");

        if (!jwtProvider.validateToken(refreshToken)) {
            throw new CustomException("해당 토큰이 유효하지 않습니다",401);
        }
		
	
        String userId = jwtProvider.getUserId(refreshToken);
        String accessToken = jwtProvider.createToken(userId);
        String newRefreshToken = jwtProvider.createRefreshToken(userId);
		
		
        Map<String, Object> map = new HashMap<>();
        map.put("memId", userId);
        map.put("refreshToken", newRefreshToken);
        int result = insertRefreshToken(map);
		
        if(result == 0) {
        	throw new CustomException("리프레시 토큰 저장에 실패하였습니다",500);
        }
        
        Cookie refreshCookie = null;

        	refreshCookie = new Cookie("refreshToken", newRefreshToken);
        	refreshCookie.setHttpOnly(true); // javascript에서 접근 차단
        	refreshCookie.setPath("/"); // 어디에 쿠키를 쓸지 경로 설정
        	refreshCookie.setSecure(false); // https 환경에서만 사용가능 현재는 false로 막아둔상태
        	refreshCookie.setMaxAge(60 * 60 * 24 * 7); // 7일

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
            throw new CustomException("유효하지 않은 토큰입니다",401);
        }
        
        String userId = jwtProvider.getUserId(refreshToken); // 토큰 안에있는 userId 가져오기
        int result = memberDao.deleteToken(userId); // 토큰 db에서 제거
    	
        if(result == 0) {
        	throw new CustomException("토큰 삭제에 실패하였습니다",500);
        }
    
        Cookie deleteCookie = new Cookie("refreshToken", null);
        deleteCookie.setPath("/");
        deleteCookie.setHttpOnly(true);
        deleteCookie.setSecure(false);
        deleteCookie.setMaxAge(0); // 0초 즉시 만료
        
    	return new LogoutResult(true,deleteCookie);
	}


	public int insertRefreshToken(Map<String,Object> map) {
		
		int result = memberDao.checkRefreshToken(map);
		
	    if (result > 0) {
	        int result2 = memberDao.updateRefreshToken(map);
	        if (result2 == 0) {
	            throw new CustomException("리프레시 토큰 업데이트에 실패했습니다.", 500);
	        }
	        return result2;
	    } else {
	        int insertResult = memberDao.insertRefreshToken(map);
	        if (insertResult == 0) {
	            throw new CustomException("리프레시 토큰 저장에 실패했습니다.", 500);
	        }
	        return insertResult;
	    }
		
	}
	
	@Override
	public String smsCode(String phone) {
		
		// 가입된 사용자인지 먼저 검사
		
		MemberDto result = memberDao.findById(phone);
		
		if(result != null) {
			throw new CustomException("현재 가입된 사용자 입니다.",409);
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
    	
    	Message message = new Message();
    	message.setFrom("01055106509");
    	message.setTo(phone);
    	message.setText("spark 인증 번호는 : [ "+ code +" ] 입니다");

    	try {
    	  
    	  messageService.send(message);
    	} catch (NurigoMessageNotReceivedException exception) {
    	  throw new CustomException("문자 발송에 실패하였습니다.",500);
    	} catch (Exception exception) {
    		throw new CustomException("문자 발송 중 예기치 못한 오류가 발생하였습니다.",500);
    	}    	
    	
    	return code;
		
	}

	@Override
	public List<MemberDto> recommendList(MemberDto m) {

	    Map<String, Object> map = new HashMap<>();
	    map.put("memId", m.getMemId());
	    map.put("interest", m.getInterest().split(","));
	    map.put("character", m.getCharacter().split(","));
	    map.put("tendencies", m.getTendencies().split(","));
	    map.put("gender",String.valueOf(m.getGender()));
	    
		List<MemberDto> list = memberDao.recommendList(map);
		
		if(list == null) {
			throw new CustomException("추천 리스트 불러오기에 실패하였습니다.",500);
		}
		
		
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
		
		if(memberDto == 0) {
			throw new CustomException("회원 가입에 실패하였습니다",500);
		}
		
		
		
		return memberDto;
	}

	@Override
	public MemberDto insertInfo(MemberDto m, MultipartFile uploadFile) {
		
		String userId = SecurityContextHolder.getContext().getAuthentication().getName(); // 현재 로그인 중인 회원의 아이디값 불러오기
		
        
        try {
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd"); // 포맷 정의 
        	LocalDate localDate = LocalDate.parse(m.getBirthDate(), formatter); // localdate 날짜전용 객체에 formatter에 정의한 형식으로 담기
        	m.setBirthDate2(Date.valueOf(localDate)); // sql.date 타입으로 변경
        }catch (Exception e) {
        	throw new CustomException("생년월일 형식이 올바르지 않습니다.", 400);
		}
        
        
		m.setMemId(userId);
		m.setInterest(String.join(",", m.getInterest2()));
		m.setCharacter(String.join(",",m.getCharacter2()));
		m.setTendencies(String.join(",",m.getTendencies2()));

		if (m.getTall().equals("140 - 145")) {
		    m.setTall("A");
		} else if (m.getTall().equals("145 - 150")) {
		    m.setTall("B");
		} else if (m.getTall().equals("150 - 155")) {
		    m.setTall("C");
		} else if (m.getTall().equals("155 - 160")) {
		    m.setTall("D");
		} else if (m.getTall().equals("160 - 165")) {
		    m.setTall("E");
		} else if (m.getTall().equals("165 - 170")) {
		    m.setTall("F");
		} else if (m.getTall().equals("170 - 175")) {
		    m.setTall("G");
		} else if (m.getTall().equals("175 - 180")) {
		    m.setTall("H");
		} else if (m.getTall().equals("180 - 185")) {
		    m.setTall("I");
		} else if (m.getTall().equals("185 - 190")) {
		    m.setTall("J");
		}
		
		if(m.getSmock().equals("자주")) {
			m.setSmock("Y");
		}else if(m.getSmock().equals("가끔")){
			m.setSmock("A");
		}else if(m.getSmock().equals("안함")){
			m.setSmock("N");
		}

		
	    if (!uploadFile.isEmpty()) { 	
	    	try {
	    		Map<String, String> map = fileUtil.fileupload(uploadFile, "profile");
	    		String filePath = map.get("filePath") + "/" + map.get("filesystemName");
	    		m.setProFile(filePath);	
	    	}catch (Exception e) {
				throw new CustomException("이미지 등록에 실패하였습니다",500);
			}
	    } else {
	        m.setProFile(null);
	    }
	    
	    int result = memberDao.insertInfo(m);
	    
	    if(result == 0) {
	    	throw new CustomException("회원 정보 추가에 실패하였습니다.",500);
	    }
	    
	    MemberDto mem = new MemberDto();
	    mem.setMemId(userId);
	   
	    MemberDto memInfo = memberDao.login(mem);
	    if(memInfo == null) {
	    	throw new CustomException("회원 정보 불러오기가 실패하였습니다.",500);
	    }
		return memInfo;
	}

	@Override
	public int recommendDelete(Map<String,String> map) {

		int result = memberDao.recommendDelete(map);
		if(result == 0) {
			throw new CustomException("추천 목록 삭제에 실패하였습니다.",500);
		}
		return result;
		
	}

	@Override
	public int likeMember(Map<String,String> map) {
		
		// 좋아요 테이블에 이미 저장되어있는지 확인
		LikeDto result = memberDao.likeMemberCheck(map);
		
		if(result != null) {
			throw new CustomException("이미 좋아요를 누른상태입니다.",401);
		}else {
			return memberDao.likeMember(map);
		}
		
	}

	@Override
	public boolean duplicateCheck(String nickName) {

	    try {
	        int result = memberDao.duplicateCheck(nickName);
	        return result <= 0;
	    } catch (Exception e) {
	        throw new CustomException("닉네임 중복 검사에 실패했습니다.", 500);
	    }

	}

	@Override
	public int interestMem(Map<String, String> map) {
		
		int check = memberDao.interestMemCheck(map); // 이미 등록한 관심회원인지 확인
		if(check > 0) {
			throw new CustomException("이미 관심이 등록된 회원입니다.",409);
		}
		
		
		int result = memberDao.interestMem(map);
		if(result == 0) {
			throw new CustomException("관심 회원 등록에 실패하였습니다.",500);
		}
		
		return result;
	}

	@Override
	public MemberDto detailInfo(String memId) {
		// 만약 내가 좋아요를 누른 상태방의 상세정보를 확인할경우
		
		MemberDto m = memberDao.detailInfo(memId);
		
		if(m == null) {
			throw new CustomException("해당 회원이 존재하지 않습니다.",404);
		}
		
		
		if (m.getTall().equals("A")) {
		    m.setTall("140 - 145");
		} else if (m.getTall().equals("B")) {
		    m.setTall("145 - 150");
		} else if (m.getTall().equals("C")) {
		    m.setTall("150 - 155");
		} else if (m.getTall().equals("D")) {
		    m.setTall("155 - 160");
		} else if (m.getTall().equals("E")) {
		    m.setTall("160 - 165");
		} else if (m.getTall().equals("F")) {
		    m.setTall("165 - 170");
		} else if (m.getTall().equals("G")) {
		    m.setTall("170 - 175");
		} else if (m.getTall().equals("H")) {
		    m.setTall("175 - 180");
		} else if (m.getTall().equals("I")) {
		    m.setTall("180 - 185");
		} else if (m.getTall().equals("J")) {
		    m.setTall("185 - 190");
		}
		
		if(m.getSmock().equals("Y")) {
			m.setSmock("자주");
		}else if(m.getSmock().equals("A")){
			m.setSmock("가끔");
		}else if(m.getSmock().equals("N")){
			m.setSmock("안함");
		}
		Map<String,String> map = new HashMap<>();
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		map.put("requestId", memId);
		map.put("responseId", userId);
		LikeDto ld = memberDao.likeMemberCheck(map);
		
		if(ld != null) {
			m.setLikeStatus("Y");
		}else {
			m.setLikeStatus("N");
		}
		
		
		
		return m;
	}

	
	
	

	

}
