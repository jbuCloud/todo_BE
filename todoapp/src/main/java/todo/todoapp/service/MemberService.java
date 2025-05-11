package todo.todoapp.service;

import todo.todoapp.dto.KakaoSignupRequest;
import todo.todoapp.entity.Member;
import todo.todoapp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import todo.todoapp.security.JwtUtil;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public Member kakaoSignup(KakaoSignupRequest request) {
        return memberRepository.save(
                Member.builder()
                        .kakaoId(request.getKakaoId())
                        .email(request.getEmail())
                        .nickname(request.getNickname())
                        .profileUrl(request.getProfileUrl())
                        .introText(request.getIntroText())
                        .build()
        );
    }
    public String kakaoLogin(Long kakaoId) {
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
        return jwtUtil.generateToken(member.getMemberId());
    }

}