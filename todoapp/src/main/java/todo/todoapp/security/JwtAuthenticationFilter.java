package todo.todoapp.security;

import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import todo.todoapp.entity.Member;
import todo.todoapp.repository.MemberRepository;


import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);

        // 🔍 로그 추가
        System.out.println("🛡 JWT 인증 시도 중 - 토큰: " + token);

        if (!jwtUtil.validateToken(token)) {
            System.out.println("❌ JWT 토큰 유효하지 않음");
            filterChain.doFilter(request, response);
            return;
        }

        Long memberId = jwtUtil.extractMemberId(token);
        System.out.println("🧩 memberId 추출: " + memberId);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(member, null, List.of());
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
