package umc.pating.services;

public class KakaoUserRequestDTO {
    private String email;
    public KakaoUserRequestDTO(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
}
