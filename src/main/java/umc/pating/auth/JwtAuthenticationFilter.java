package umc.pating.auth;



import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import umc.pating.config.oauth.provider.JwtTokenProvider;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 🔥 요청에서 Authorization 헤더를 로그로 출력
        String bearerToken = request.getHeader("Authorization");
        System.out.println("📌 요청된 Authorization 헤더: " + bearerToken);

        // ✅ 요청에서 JWT 토큰 추출
        String token = resolveToken(request);
        System.out.println("🔍 요청된 JWT: " + token); // ✅ 로그 추가

        // ✅ 토큰 검증 후 SecurityContext에 저장
//        if (token != null && jwtTokenProvider.validateToken(token)) {
//            System.out.println("✅ JWT 유효성 검사 통과");
//            Authentication auth = jwtTokenProvider.getAuthentication(token);
//            SecurityContextHolder.getContext().setAuthentication(auth);
//            System.out.println("✅ SecurityContext에 인증 정보 저장: " + auth.getName());
//        } else {
//            System.out.println("❌ JWT Token is missing or invalid");
//        }

        // ✅ 필터 체인 계속 진행
        filterChain.doFilter(request, response);
    }

    // ✅ 요청 헤더에서 JWT 토큰 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        System.out.println("📌 Authorization 헤더 값: " + bearerToken); // ✅ 로그 추가

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

