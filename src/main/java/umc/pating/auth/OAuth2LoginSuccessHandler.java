package umc.pating.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import umc.pating.config.oauth.provider.JwtTokenProvider;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain chain,
                                        Authentication authentication) throws IOException, ServletException {
        System.out.println("✅ [OAuth2LoginSuccessHandler] 실행됨!!");

        if (!(authentication.getPrincipal() instanceof PrincipalDetails)) {
            System.out.println("❌ PrincipalDetails 타입이 아님!");
            return;
        }

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("✅ OAuth2 로그인 성공 - 사용자: " + principalDetails.getUsername());

        List<String> roles = principalDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        System.out.println("✅ 사용자 권한 목록: " + roles);

        String jwtToken = jwtTokenProvider.generateToken(principalDetails.getUsername(), roles);
        System.out.println("✅ JWT 발급 성공: " + jwtToken);

        if (jwtToken == null || jwtToken.isEmpty()) {
            throw new RuntimeException("❌❌❌ JWT 생성 실패!!");
        }

        // ✅ JWT를 Response Header에 추가
        response.addHeader("Authorization", "Bearer " + jwtToken);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(jwtToken));

    }

}
