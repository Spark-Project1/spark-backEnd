package com.spark.member.config;

import com.spark.base.exception.CustomException;
import com.spark.base.util.JwtProvider;
import com.spark.member.model.Member;
import com.spark.member.repository.MemberDao;
import com.spark.member.service.MemberValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class InjectMemberArgument implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;
    private final MemberDao memberDao;
    private final MemberValidator memberValidator;


    public InjectMemberArgument(JwtProvider jwtProvider, MemberDao memberDao, MemberValidator memberValidator) {
        this.jwtProvider = jwtProvider;
        this.memberDao = memberDao;
        this.memberValidator = memberValidator;
    }


    // 해당 파라미터가 이 Argument Resolver에 의해 처리될 수 있는지 여부를 결정
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
    // InjectMember 어노테이션이 붙어있고, 파라미터 타입이 Member인 경우에만 처리
        return parameter.hasParameterAnnotation(InjectMember.class)
            && parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {


        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = request.getHeader("Authorization").replace("Bearer ", "");

        // 토큰 유효성 검사
        memberValidator.validToken(token);

        // 토큰에서 사용자 아이디 추출
        String memId = jwtProvider.getUserId(token);

        return memberDao.findById(memId).orElseThrow(() -> new CustomException("회원 정보가 없습니다.",403));

    }
}
