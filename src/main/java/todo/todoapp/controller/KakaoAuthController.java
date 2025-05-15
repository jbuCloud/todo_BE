package todo.todoapp.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import todo.todoapp.dto.KakaoLoginResponse;

import org.springframework.beans.factory.annotation.Value;           // 🔹 @Value
import org.springframework.beans.factory.annotation.Autowired;       // 🔹 @Autowired (선택)
import todo.todoapp.service.MemberService;                          // 🔹 MemberService

import java.util.Map;

@RestController
@RequestMapping("/api/auth/kakao")
public class KakaoAuthController {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    private final MemberService memberService;

    public KakaoAuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 카카오 로그인 url 요청

    @GetMapping("url")
    public String getKakaoLoginUrl() {
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize?response_type=code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri;
        return kakaoAuthUrl;
    }
    // 인가 코드로 로그인 처리
    @GetMapping("/callback")
    public ResponseEntity<?> kakaoCallback(@RequestParam("code")String code) {
        System.out.println("✅ [카카오 로그인] 받은 code: " + code);
        System.out.println("✅ [카카오 로그인] redirect_uri 설정값: " + redirectUri);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(params, headers);
        ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(
                "https://kauth.kakao.com/oauth/token", tokenRequest, Map.class);

        System.out.println("📦 [카카오 응답 상태] " + tokenResponse.getStatusCode());
        System.out.println("📦 [카카오 응답 전체] " + tokenResponse.getBody());

        String accessToken = (String) tokenResponse.getBody().get("access_token");
        System.out.println("✅ [카카오 로그인] access token: " + accessToken);

        HttpHeaders infoHeaders = new HttpHeaders();
        infoHeaders.setBearerAuth(accessToken);
        HttpEntity<?> infoRequest = new HttpEntity<>(infoHeaders);

        ResponseEntity<Map> userInfoResponse = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me", HttpMethod.GET, infoRequest, Map.class);

        Map<String, Object> kakaoAccount = (Map<String, Object>) userInfoResponse.getBody().get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        String email = (String) kakaoAccount.get("email");
        String nickname = (String) profile.get("nickname");
        Long kakaoId = ((Number) userInfoResponse.getBody().get("id")).longValue();

        System.out.println("👤 [카카오 사용자] email: " + email);
        System.out.println("👤 [카카오 사용자] nickname: " + nickname);
        System.out.println("👤 [카카오 사용자] kakaoId: " + kakaoId);

        KakaoLoginResponse kakaoLoginResponse = memberService.kakaoLoginOrRegister(kakaoId, email, nickname);
        return ResponseEntity.ok(kakaoLoginResponse);
    }



}
