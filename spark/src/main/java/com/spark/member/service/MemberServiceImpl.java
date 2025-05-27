package com.spark.member.service;

import java.time.Year;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spark.base.exception.CustomException;
import com.spark.base.util.FileUtil;
import com.spark.base.util.JwtProvider;
import com.spark.member.dto.LikeDto;
import com.spark.member.dto.LoginResult;
import com.spark.member.dto.LogoutResult;
import com.spark.member.dto.MemberDto;
import com.spark.member.dto.TokenResult;
import com.spark.member.dto.request.LoginRequest;
import com.spark.member.dto.request.PhoneRequest;
import com.spark.member.dto.request.TokenRequest;
import com.spark.member.dto.response.TokenResponse;
import com.spark.member.dto.response.ValidResponse;
import com.spark.member.repository.MemberDao;

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
	private final MemberValidator memberValidtor;
	private final TokenResponse tokenResponse;
	private final MemberPreprocessor memberPreprocessor;

	@Override
	public LoginResult login(LoginRequest m) {
		
		// 1. 유저 확인
	    MemberDto memberDto = memberDao.login(m).orElseThrow(()-> new CustomException("회원정보가 없습니다.",400));
	    
    	memberValidtor.validationPassword(m.getMemPwd(), memberDto.getMemPwd()); // 멤버 비밀번호 유효성 검사
    	// 2. 토큰 생성
    	
	    return tokenResponse.CreateToken(memberDto); // 토큰 생성 클래스 실행

	}

	@Override
	public ValidResponse loginUserInfo(TokenRequest authHeader) {
    	String validateToken = authHeader.getTokenRequest().replace("Bearer ", ""); // 토큰만 뺴내어
    	memberValidtor.validToken(validateToken); // 토큰 유효성 검사
        
    	String token = jwtProvider.getUserId(validateToken);
    	MemberDto memberDto = memberDao.loginUserInfo(token);
		return ValidResponse.available(memberDto);
		
	}

	@Override
	public MemberDto findById(String userId) {
		MemberDto result = memberDao.findById(userId).orElseThrow( () -> new CustomException("해당 사용자가 존재하지 않습니다.",403));
		return result;
		
	}

	@Override
	public TokenResult insertRefreshToken(TokenRequest refreshTokenHeader) {
		
		String refreshToken = refreshTokenHeader.getTokenRequest().replace("Bearer ", "");

        memberValidtor.validToken(refreshToken); // 리프레시 토큰 유효성검사 진행

        return tokenResponse.insertRefreshToken(refreshToken);
		
	}

	@Override
	public LogoutResult deleteToken(TokenRequest refreshTokenHeader) {
		
        String token = refreshTokenHeader.getTokenRequest().replace("Bearer ", ""); // Bearer를 제거해 token만 추출
       
        memberValidtor.validToken(token); // 토큰 유효성 검사
          
    	return tokenResponse.deleteToken(token);
	}



	
	@Override
	public String smsCode(PhoneRequest phone) {
		
		// 가입된 사용자인지 먼저 검사
		memberDao.findById(phone.getPhone()).orElseThrow( () -> new CustomException("현재 가입된 사용자 입니다.",409));

    	return memberPreprocessor.coolSms(phone);
		
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
		
		memberPreprocessor.preprocess(m, uploadFile); // 생년월일 변환 및 파일정보 입력
        memberPreprocessor.memberTallDB(m); // 멤버 키 설정
        memberPreprocessor.memberSmockDB(m); // 멤버 흡연 설정
	    
	    int result = memberDao.insertInfo(m);
	    if(result == 0) {
	    	throw new CustomException("회원 정보 추가에 실패하였습니다.",500);
	    }
	    
	    LoginRequest mem = new LoginRequest();
	    mem.setMemId(userId);
	   
	    MemberDto memInfo = memberDao.login(mem).orElseThrow( () -> new CustomException("회원 정보 불러오기가 실패하였습니다.",500));

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
		MemberDto m = memberDao.detailInfo(memId).orElseThrow(() -> new CustomException("해당 회원이 존재하지 않습니다.",400));		
		
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
