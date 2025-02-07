package umc.pating.config.oauth.provider;

import java.util.LinkedHashMap;
import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes; // getAttributes()
    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getEmail() {
        Object object = attributes.get("kakao_account");
        System.out.println("kakao_account 데이터: " + object); // ✅ 디버깅용 로그 추가
        if (object == null) {
            System.out.println("kakao_account가 없습니다.");
            return null;
        }

        LinkedHashMap accountMap = (LinkedHashMap) object;

        if (!accountMap.containsKey("email")) {
            System.out.println("카카오에서 이메일을 제공하지 않았습니다.");
            return null;
        }

        return (String) accountMap.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}
