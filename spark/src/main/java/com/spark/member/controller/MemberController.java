package com.spark.member.controller;

import java.util.List;

import com.spark.member.config.InjectMember;
import com.spark.member.dto.*;
import com.spark.member.dto.request.*;
import com.spark.member.dto.response.*;
import com.spark.member.model.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.spark.base.util.JwtProvider;
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
        description = "입력받은 클라이언트 정보를 조회해 access와 refresh 토큰, 프로필 정보를 반환")
    @PostMapping("/login")
    public ResponseEntity<LoginInfo> MemberLogin(@RequestBody @Valid LoginRequest m, HttpServletResponse response) {
        LoginResult result = memberService.login(m);
        response.addCookie(result.getRefreshCookie());
        return ResponseEntity.ok(new LoginInfo(result.getToken(), result.getMemberDto()));

    }

    // 리프레시 토큰 발급
    @Operation(summary = "리프레시 토큰 발급",
        description = "access token 만료시 클라이언트의 refresh token을 활용해 서버에 보유한 토큰과 일치시 access,refresh token 재발급")
    @PostMapping("/refresh")
    public ResponseEntity<JwtToken> refreshToken(@CookieValue("refreshToken") @Valid TokenRequest refreshTokenHeader, HttpServletResponse response) { // 헤더에있는 Authorization 값 받아옴
        TokenResult result = memberService.insertRefreshToken(refreshTokenHeader);
        response.addCookie(result.getRefreshCookie());
        return ResponseEntity.ok(result.getJwtToken());
    }


    @Operation(
        summary = "JWT 토큰 유효성 검사",
        description = "클라이언트의 토큰을 조회해 토큰정보 일치시 해당 회원정보 반환")
    @GetMapping("/validate")
    public ResponseEntity<ValidResponse> validate(@InjectMember Member member) {
        return ResponseEntity.ok(ValidResponse.available(LoginResponse.from(member)));
    }


    // 쿨sms
    @Operation(summary = "쿨 sms api",
        description = "회원가입 진행시 아이디 확인에 필요한 문자 인증절차")
    @PostMapping("/sms")
    public ResponseEntity<String> sms(@RequestBody @Valid PhoneRequest phone) {
        return ResponseEntity.ok(memberService.smsCode(phone));
    }

    // 로그아웃
    @Operation(summary = "회원 로그아웃",
        description = "로그아웃 시 클라이언트가 보유한 토큰 정보 제거")
    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@CookieValue("refreshToken") @Valid TokenRequest refreshTokenHeader, HttpServletResponse response) {
        LogoutResult result = memberService.deleteToken(refreshTokenHeader);
        if (result.isStatus() && result.getCookie() != null) {
            response.addCookie(result.getCookie());
        }
        return ResponseEntity.ok(result.isStatus());
    }

    // 메인화면 추천목록
    @Operation(summary = "회원 추천리스트 조회",
        description = "메인화면 진입시 클라이언트가 입력한 성격,취미 등에 맞춰 공통 관심사에 해당하는 회원목록 조회")
    @PostMapping("/recommend")
    public ResponseEntity<List<RecommendResponse>> recommendList(@RequestBody @Valid RecommendRequest m, @InjectMember Member member) {
        return ResponseEntity.ok(memberService.recommendList(m));
    }

    // 회원가입
    @Operation(summary = "회원 회원가입",
        description = "서비스 이용을 위한 회원 회원가입")
    @PostMapping("/signup")
    public ResponseEntity<Integer> signup(@RequestBody @Valid SignUpRequest m) {
        return ResponseEntity.ok(memberService.signUp(m));

    }

    // 회원 정보입력
    @Operation(summary = "회원 정보입력",
        description = "본인의 나이,성격,취미 등 인적사항을 작성")
    @PatchMapping("/insertInfo")
    public ResponseEntity<LoginResponse> insertInfo(@ModelAttribute @Valid UpdateMemberInfoRequest m
                                                  , @RequestParam("uploadFile") MultipartFile uploadFile
                                                  , @InjectMember Member member) {
        return ResponseEntity.ok(memberService.insertInfo(m, uploadFile,member));
    }

    // 추천목록 삭제
    @Operation(summary = "추천회원 삭제",
        description = "추천받은 리스트에서 삭제 처리시 추후에 불러올 추천리스트에서도 해당 회원 조회x")
    @PostMapping("/recommendDelete")
    public ResponseEntity<Integer> recommendDelete(@RequestBody @Valid RecommendDeleteRequest recommendDelete) {
        return ResponseEntity.ok(memberService.recommendDelete(recommendDelete));
    }

    // 좋아요 신청
    @Operation(summary = "좋아요 신청",
        description = "좋아요 신청 시 내가 신청한 좋아요 목록에 해당 회원 추가")
    @PostMapping("/like")
    public ResponseEntity<Integer> likeMember(@RequestBody @Valid LikeSendRequest likeSend) {
        return ResponseEntity.ok(memberService.likeMember(likeSend));
    }

    // 닉네임 중복검사
    @Operation(summary = "닉네임 중복검사",
        description = "회원 정보입력 항목에서 닉네임 중복검사 진행")
    @GetMapping("/duplicateCheck")
    public ResponseEntity<Boolean> duplicateCheck(@RequestParam @Valid DuplicateCheckRequest nickName) {
        return ResponseEntity.ok(memberService.duplicateCheck(nickName));
    }


    // 관심목록 추가
    @Operation(summary = "회원 관심목록",
        description = "관심목록 추가 시 내가 추가한 관심목록에 해당 회원 추가")
    @PostMapping("/interestMem")
    public ResponseEntity<Integer> interestMem(@RequestBody @Valid InterestMemberAddRequest interestMember) {
        return ResponseEntity.ok(memberService.interestMem(interestMember));
    }

    // 상대방 상세정보 불러오기
    @Operation(summary = "회원 상세보기",
        description = "회원 상세보기 시 ")
    @PostMapping("/DetailInfo")
    public ResponseEntity<DetailMemberInfoResponse> detailInfo(@RequestBody @Valid DetailMemberInfoRequest detailMemberInfo) {
        return ResponseEntity.ok(memberService.detailInfo(detailMemberInfo));
    }

    // 좋아요 목록
    @Operation(summary = "보낸 좋아요 목록",
        description = "내가 좋아요를 보낸 회원 목록 조회")
    @PostMapping("/me/send/likeList")
    public ResponseEntity<List<LikeListResponse>> likeList(@RequestBody @Valid LikeListRequest likeList) {
        return ResponseEntity.ok(memberService.likeList(likeList));
    }

    // 관심목록 리스트
    @Operation(summary = "관심목록 리스트",
        description = "내가 관심을 두고 있는 회원 목록 조회")
    @PostMapping("/me/interestList")
    public ResponseEntity<List<InterestListResponse>> interestList(@Valid @RequestBody InterestListRequest interestList) {
        return ResponseEntity.ok(memberService.interestList(interestList));
    }

    // 좋아요 수락
    @Operation(summary = "좋아요 목록 삭제",
        description = "좋아요 목록에서 좋아요 클릭 시 해당 회원과의 채팅이 생성")
    @PostMapping("/me/likeYes")
    public ResponseEntity<Integer> likeYes(@RequestBody @Valid LikeRequest likeInfo) {
        return ResponseEntity.ok(memberService.likeYes(likeInfo));
    }


    // 좋아요 거절
    @Operation(summary = "좋아요 거절",
        description = "좋아요 거절 시 좋아요 리스트에서 해당 회원 삭제")
    @DeleteMapping("/me/likeNo")
    public ResponseEntity<Integer> likeNo(@ModelAttribute @Valid LikeRequest likeInfo) {
        return ResponseEntity.ok(memberService.likeNo(likeInfo));
    }

    // 받은 좋아요 목록
    @Operation(summary = "받은 좋아요 목록",
        description = "내가 좋아요를 받은 회원 목록 조회")
    @PostMapping("/me/get/likeList")
    public ResponseEntity<List<LikeListResponse>> getLikeList(@RequestBody @Valid LikeListRequest likeList){
        return ResponseEntity.ok(memberService.getLikeList(likeList));
    }


}
