package umc.pating.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import umc.pating.auth.PrincipalDetails;
import umc.pating.config.oauth.provider.KakaoUserInfo;
import umc.pating.config.oauth.provider.OAuth2UserInfo;
import umc.pating.entity.User;
import umc.pating.repository.UserRepository;

import java.util.Map;
import java.util.Optional;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("🔍 [PrincipalOauth2UserService] 실행됨");
        System.out.println("getClientRegistration = " + userRequest.getClientRegistration());
        System.out.println("getAccessToken = " + userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("getAttributes = " + oAuth2User.getAttributes());

        if (!userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            throw new OAuth2AuthenticationException("카카오 로그인만 지원합니다.");
        }

        System.out.println("카카오 로그인 요청");
        OAuth2UserInfo oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();

        if (email == null) {
            System.out.println("이메일이 제공되지 않았습니다.");
            throw new OAuth2AuthenticationException("OAuth2 provider did not provide an email.");
        }

        System.out.println("가져온 이메일: " + email);

        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            System.out.println("✅ 기존 회원 로그인: " + email);
        } else {
            user = User.builder()
                    .userName(provider + "_" + providerId)
                    .email(email)
                    .role("ROLE_USER")
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(user);
            System.out.println("✅ 신규 회원 가입: " + email);
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
