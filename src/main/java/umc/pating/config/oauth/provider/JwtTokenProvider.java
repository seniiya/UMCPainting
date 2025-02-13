package umc.pating.config.oauth.provider;


import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import umc.pating.auth.PrincipalDetails;
import umc.pating.entity.User;
import umc.pating.repository.UserRepository;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.SystemProperties.getUserName;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}") // application.properties에서 secret 값 가져오기
    private String secretKey;

    private final long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7일

    private final UserRepository userRepository;

    // 객체 초기화, secretKey를 Base64로 인코딩
    @PostConstruct
    protected void init() {
        this.secretKey = Base64.getEncoder().encodeToString(this.secretKey.getBytes());
    }


    // 토큰 생성
    public String createToken(String userId, List<String> roles){
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles", roles);

        Date now = new Date();

        // 1시간 유효 기간
        long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60;

        return Jwts.builder()
                .setClaims(claims) // 정보저장
                .setIssuedAt(new Date()) // 토큰발행시간정보
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey) // 서명 알고리즘 및 키
                .compact();

//        String refreshToken = Jwts.builder()


    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰에서 인증 정보 추출
    public Authentication getAuthentication(String token) {
        String userName = getUserName(token);
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("유저를 찾을 수 없음: " + userName);
        }
        UserDetails userDetails = new PrincipalDetails(user);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


}