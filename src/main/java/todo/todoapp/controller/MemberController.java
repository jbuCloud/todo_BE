package todo.todoapp.controller;

import todo.todoapp.dto.KakaoLoginRequest;
import todo.todoapp.dto.KakaoSignupRequest;
import todo.todoapp.entity.Member;
import todo.todoapp.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todo.todoapp.dto.KakaoLoginResponse;
import todo.todoapp.repository.MemberRepository;


@RestController
@RequestMapping("/api/auth/kakao")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/signup")
    public ResponseEntity<Member> kakaoSignup(@RequestBody KakaoSignupRequest request) {
        Member savedMember = memberService.kakaoSignup(request);
        return ResponseEntity.ok(savedMember);
    }

    @PostMapping("/login")
    public ResponseEntity<KakaoLoginResponse> kakaoLogin(@RequestBody KakaoLoginRequest request) {
        String token = memberService.kakaoLogin(request.getKakaoId());
        Member member = memberRepository.findByKakaoId(request.getKakaoId()).get();
        return ResponseEntity.ok(new KakaoLoginResponse(member.getMemberId(), token));
    }


}