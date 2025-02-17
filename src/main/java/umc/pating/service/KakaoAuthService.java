package umc.pating.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import umc.pating.services.KakaoUserRequestDTO;

import java.util.Map;

@Service
public class KakaoAuthService {

    public KakaoUserRequestDTO getUserInfoFromKakao(String accessToken) {
        // ✅ 카카오 API 호출 URL
        String kakaoUserInfoUrl = "https://kapi.kakao.com/v2/user/me";

        // ✅ HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // ✅ 요청 보내기
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                kakaoUserInfoUrl,
                HttpMethod.GET,
                entity,
                Map.class
        );

        // ✅ 응답이 성공적이지 않다면 null 반환
        if (response.getStatusCode() != HttpStatus.OK) {
            return null;
        }

        // ✅ 응답에서 사용자 정보 추출
        Map<String, Object> body = response.getBody();
        if (body == null) return null;

        // ✅ "kakao_account"에서 이메일 추출
        Map<String, Object> kakaoAccount = (Map<String, Object>) body.get("kakao_account");
        String email = kakaoAccount.get("email").toString();

        return new KakaoUserRequestDTO(email);
    }
}

