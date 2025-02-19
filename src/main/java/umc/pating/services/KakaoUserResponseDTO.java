package umc.pating.services;

// 🔹 카카오 로그인 응답 시 필요한 DTO
public class KakaoUserResponseDTO {
    private String email;
    private String accessToken;
    private String refreshToken;

    public KakaoUserResponseDTO(String email, String accessToken, String refreshToken) {
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getEmail() {
        return email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
