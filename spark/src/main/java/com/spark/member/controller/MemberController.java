package com.spark.member.controller;

import java.util.List;
import java.util.Map;

import com.spark.base.util.JwtProvider;
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

import com.spark.member.dto.JwtToken;
import com.spark.member.dto.LoginInfo;
import com.spark.member.dto.LoginResult;
import com.spark.member.dto.LogoutResult;
import com.spark.member.dto.MemberDto;
import com.spark.member.dto.TokenResult;
import com.spark.member.application.MemberService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {


  private final MemberService memberService;
  private final JwtProvider jwtProvider;

  // 로그인
  @PostMapping("/login")
  public ResponseEntity<LoginInfo> MemberLogin(@RequestBody MemberDto m, HttpServletResponse response) {
    LoginResult result = memberService.login(m);
    response.addCookie(result.getRefreshCookie());
    return ResponseEntity.ok(new LoginInfo(result.getToken(), result.getMemberDto()));

  }

  // 리프레시 토큰 발급
  @PostMapping("/refresh")
  public ResponseEntity<JwtToken> refreshToken(@CookieValue("refreshToken") String refreshTokenHeader, HttpServletResponse response) { // 헤더에있는 Authorization 값 받아옴
    TokenResult result = memberService.insertRefreshToken(refreshTokenHeader);
    response.addCookie(result.getRefreshCookie());
    return ResponseEntity.ok(result.getJwtToken());
  }

  // 유효성 검사
  @GetMapping("/validate")
  public ResponseEntity<Map<String, Object>> validate(@RequestHeader("Authorization") String authHeader) {
    return ResponseEntity.ok(memberService.loginUserInfo(authHeader));
  }


  // 쿨sms
  @PostMapping("/sms")
  public ResponseEntity<String> sms(@RequestBody String phone) {
    return ResponseEntity.ok(memberService.smsCode(phone));
  }

  // 로그아웃
  @PostMapping("/logout")
  public ResponseEntity<Boolean> logout(@CookieValue("refreshToken") String refreshTokenHeader, HttpServletResponse response) {
    LogoutResult result = memberService.deleteToken(refreshTokenHeader);
    if (result.isStatus() && result.getCookie() != null) {
      response.addCookie(result.getCookie());
    }
    return ResponseEntity.ok(result.isStatus());
  }

  // 메인화면 추천목록
  @PostMapping("/recommend")
  public ResponseEntity<List<MemberDto>> recommendList(@RequestBody MemberDto m) {
    return ResponseEntity.ok(memberService.recommendList(m));
  }

  // 회원가입
  @PostMapping("/signup")
  public ResponseEntity<Integer> signup(@RequestBody MemberDto m) {
    return ResponseEntity.ok(memberService.signUp(m));

  }

  // 회원 정보입력
  @PatchMapping("/insertInfo")
  public ResponseEntity<MemberDto> insertInfo(@ModelAttribute MemberDto m, @RequestParam("uploadFile") MultipartFile uploadFile) {
    return ResponseEntity.ok(memberService.insertInfo(m, uploadFile));
  }

  // 추천목록 삭제
  @PostMapping("/recommendDelete")
  public ResponseEntity<Integer> recommendDelete(@RequestBody Map<String, String> map) {
    return ResponseEntity.ok(memberService.recommendDelete(map));
  }

  // 좋아요 신청
  @PostMapping("/like")
  public ResponseEntity<Integer> likeMember(@RequestBody Map<String, String> map) {
    System.out.println(map);
    return ResponseEntity.ok(memberService.likeMember(map));
  }

  // 닉네임 중복검사

  @GetMapping("/duplicateCheck")
  public ResponseEntity<Boolean> duplicateCheck(@RequestParam String nickName) {
    return ResponseEntity.ok(memberService.duplicateCheck(nickName));
  }


  // 관심목록 추가
  @PostMapping("/interestMem")
  public ResponseEntity<Integer> interestMem(@RequestBody Map<String, String> map) {
    return ResponseEntity.ok(memberService.interestMem(map));
  }

  // 상대방 상세정보 불러오기
  @GetMapping("/DetailInfo")
  public ResponseEntity<MemberDto> detailInfo(@RequestParam String memId) {
    return ResponseEntity.ok(memberService.detailInfo(memId));
  }


}
