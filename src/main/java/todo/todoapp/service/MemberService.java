package todo.todoapp.service;

import todo.todoapp.dto.KakaoLoginResponse;
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

    // ✅ 새로 추가: 존재하면 로그인, 아니면 회원가입 후 로그인
    public KakaoLoginResponse kakaoLoginOrRegister(Long kakaoId, String email, String nickname) {
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseGet(() -> {
                    Member newMember = new Member();
                    newMember.setKakaoId(kakaoId);
                    newMember.setEmail(email);
                    newMember.setNickname(nickname);
                    return memberRepository.save(newMember);
                });

        String token = jwtUtil.generateToken(member.getMemberId());

        return new KakaoLoginResponse(member.getMemberId(), token);
    }


}