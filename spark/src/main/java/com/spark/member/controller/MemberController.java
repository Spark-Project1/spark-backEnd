package com.spark.member.controller;

import java.util.List;
import java.util.Map;

import com.spark.member.dto.*;
import com.spark.member.dto.request.*;
import com.spark.member.dto.response.InterestListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spark.base.util.JwtProvider;
import com.spark.member.dto.response.ValidResponse;
import com.spark.member.service.MemberService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Tag(name = "Member API", description = "회원 관련 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {


    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    // 로그인
    @Operation(summary = "회원 로그인",
        description = "클라이언트가 회원의 아이디(memId)와 비밀번호(memPwd)를 입력하여 로그인 요청을 수행합니다." +
                      "로그인 성공 시, accessToken과 refreshToken이 발급되며 회원 프로필 정보도 함께 반환" +
                      "실패시 400 오류 발생",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "회원 데이터",
            required = true,
            content = @Content(
                schema = @Schema(implementation = LoginRequest.class)
            )
        )
    )
    @PostMapping("/login")
    public ResponseEntity<LoginInfo> MemberLogin(@RequestBody @Valid LoginRequest m, HttpServletResponse response) {
        LoginResult result = memberService.login(m);
        response.addCookie(result.getRefreshCookie());
        return ResponseEntity.ok(new LoginInfo(result.getToken(), result.getMemberDto()));

    }

    // 리프레시 토큰 발급
    @PostMapping("/refresh")
    public ResponseEntity<JwtToken> refreshToken(@CookieValue("refreshToken") @Valid TokenRequest refreshTokenHeader, HttpServletResponse response) { // 헤더에있는 Authorization 값 받아옴
        TokenResult result = memberService.insertRefreshToken(refreshTokenHeader);
        response.addCookie(result.getRefreshCookie());
        return ResponseEntity.ok(result.getJwtToken());
    }


    @Operation(
        summary = "JWT 토큰 유효성 검사",
        description = "클라이언트의 토큰을 조회해 토큰정보 일치시 해당 회원정보 반환",
        security = @SecurityRequirement(name = "JWT"),
        parameters = {
            @Parameter(
                name = "Authorization",
                description = "Authorization 헤더에 JWT를 담아 보낸후 토큰이 유효한지 확인하고 해당 회원 정보를 반환",
                required = true,
                in = ParameterIn.HEADER // 파라미터 위치
            )
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "성공",
                content = @Content(
                    schema = @Schema(implementation = ValidResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "실패"
            )
        }
    )
    @GetMapping("/validate")
    public ResponseEntity<ValidResponse> validate(@RequestHeader("Authorization") @Valid TokenRequest authHeader) {
        return ResponseEntity.ok(memberService.loginUserInfo(authHeader));
    }


    // 쿨sms
    @PostMapping("/sms")
    public ResponseEntity<String> sms(@RequestBody @Valid PhoneRequest phone) {
        return ResponseEntity.ok(memberService.smsCode(phone));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@CookieValue("refreshToken") @Valid TokenRequest refreshTokenHeader, HttpServletResponse response) {
        LogoutResult result = memberService.deleteToken(refreshTokenHeader);
        if (result.isStatus() && result.getCookie() != null) {
            response.addCookie(result.getCookie());
        }
        return ResponseEntity.ok(result.isStatus());
    }

    // 메인화면 추천목록
    @PostMapping("/recommend")
    public ResponseEntity<List<MemberDto>> recommendList(@RequestBody @Valid RecommendRequest m) {
        return ResponseEntity.ok(memberService.recommendList(m));
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Integer> signup(@RequestBody @Valid SignUpRequest m) {
        return ResponseEntity.ok(memberService.signUp(m));

    }

    // 회원 정보입력
    @PatchMapping("/insertInfo")
    public ResponseEntity<MemberDto> insertInfo(@ModelAttribute @Valid InsertMemberInfoRequest m, @RequestParam("uploadFile") MultipartFile uploadFile) {
        return ResponseEntity.ok(memberService.insertInfo(m, uploadFile));
    }

    // 추천목록 삭제
    @PostMapping("/recommendDelete")
    public ResponseEntity<Integer> recommendDelete(@RequestBody @Valid RecommendDeleteRequest recommendDelete) {
        return ResponseEntity.ok(memberService.recommendDelete(recommendDelete));
    }

    // 좋아요 신청
    @PostMapping("/like")
    public ResponseEntity<Integer> likeMember(@RequestBody LikeSendRequest likeSend) {
        return ResponseEntity.ok(memberService.likeMember(likeSend));
    }

    // 닉네임 중복검사
    @GetMapping("/duplicateCheck")
    public ResponseEntity<Boolean> duplicateCheck(@RequestParam String nickName) {
        return ResponseEntity.ok(memberService.duplicateCheck(nickName));
    }


    // 관심목록 추가
    @Operation(
        security = @SecurityRequirement(name = "JWT")
    )
    @PostMapping("/interestMem")
    public ResponseEntity<Integer> interestMem(@RequestBody @Valid InterestMemberAddRequest interestMember) {
        return ResponseEntity.ok(memberService.interestMem(interestMember));
    }

    // 상대방 상세정보 불러오기
    @PostMapping("/DetailInfo")
    public ResponseEntity<MemberDto> detailInfo(@RequestBody Member m) {
        return ResponseEntity.ok(memberService.detailInfo(m));
    }

    // 좋아요 목록
    @PostMapping("/me/likeList")
    public ResponseEntity<List<MemberDto>> likeList(@RequestBody @Valid likeListRequest likeList) {
        return ResponseEntity.ok(memberService.likeList(likeList));
    }

    // 관심목록 리스트
    @GetMapping("/me/interestList")
    public ResponseEntity<List<InterestListResponse>> interestList(@Valid @RequestParam InterestListRequest interestList) {
        return ResponseEntity.ok(memberService.interestList(interestList));
    }


}
