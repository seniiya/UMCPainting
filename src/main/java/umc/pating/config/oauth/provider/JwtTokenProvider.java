package umc.pating.config.oauth.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}") // application.properties에서 secret 값 가져오기
    private String secretKey;

    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간 유효 기간
    private final long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7일

    private Key getSigningKey() {
        System.out.println("jwt.secret 값: " + secretKey); // jwt 시크릿 값 출력
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // JWT 생성
    public String createToken(String userId, List<String> roles) {
        System.out.println("✅ JWT 생성 시작 - userId: " + userId + ", roles: " + roles); // ✅ 로그 추가
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles", roles);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        System.out.println("✅ 생성된 JWT: " + token); // ✅ JWT 확인 로그 추가
        return token;
    }

    // JWT 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ **refreshToken으로 새로운 accessToken 발급**
    public String regenerateAccessToken(String refreshToken) {
        if (!validateRefreshToken(refreshToken)) {
            throw new RuntimeException("Invalid Refresh Token");
        }

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();

        String userId = claims.getSubject();
        List<String> roles = (List<String>) claims.get("roles");

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}