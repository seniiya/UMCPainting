package umc.pating.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import umc.pating.config.oauth.provider.JwtTokenProvider;
import umc.pating.repository.UserRepository;
import umc.pating.service.KakaoAuthService;
import umc.pating.services.KakaoTokenRequestDTO;
import umc.pating.services.KakaoTokenResponseDTO;
import umc.pating.services.KakaoUserRequestDTO;
import java.util.List;
import java.util.Optional;
import umc.pating.entity.User;  // User 엔티티 클래스

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final KakaoAuthService kakaoAuthService;

    @PostMapping("/kakao")
    public ResponseEntity<KakaoTokenResponseDTO> receiveKakaoAccessToken(
            @RequestBody KakaoTokenRequestDTO request) {
        // 🔥 요청 바디에서 액세스 토큰 추출
        String token = request.getAccessToken();

        System.out.println("🔑 받은 카카오 Access Token: " + token);

        // ✅ 카카오 API 호출하여 사용자 정보 가져오기
        KakaoUserRequestDTO kakaoUser = kakaoAuthService.getUserInfoFromKakao(token);
        if (kakaoUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // ✅ 기존 유저인지 확인 (없으면 새로 저장)
        Optional<User> optionalUser = userRepository.findByEmail(kakaoUser.getEmail());
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            System.out.println("✅ 기존 회원 로그인: " + user.getEmail());
        } else {
            user = User.builder()
                    .userName("kakao_" + kakaoUser.getEmail())
                    .email(kakaoUser.getEmail())
                    .role("ROLE_USER")
                    .provider("kakao")
                    .providerId(kakaoUser.getEmail()) // providerId는 email로 설정
                    .build();
            userRepository.save(user);
            System.out.println("✅ 신규 회원 가입: " + user.getEmail());
        }

        // ✅ JWT 생성
        String jwtToken = jwtTokenProvider.createToken(user.getEmail(), List.of(user.getRole()));
        System.out.println("✅ JWT 발급 완료: " + jwtToken);

        KakaoTokenResponseDTO responseDTO = new KakaoTokenResponseDTO(jwtToken, user.getUserId());
        return ResponseEntity.ok(new KakaoTokenResponseDTO(jwtToken, user.getUserId()));
    }

}