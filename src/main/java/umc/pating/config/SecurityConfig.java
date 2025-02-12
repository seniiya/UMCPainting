package umc.pating.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import umc.pating.auth.JwtAuthenticationFilter;
import umc.pating.auth.OAuth2LoginSuccessHandler;
import umc.pating.config.oauth.PrincipalOauth2UserService;

// 구글 로그인 완료된 뒤의 후처리가 필요함.
// 1. 코드받기(인증됨)
// 2. 엑세스 토큰(사용자 정보에 접근할 권한받음)
// 3. 사용자 프로필 정보를 가져오고
// 4-1. 그 정보를 토대로 회원가입을 자동으로 진행시키기도 함
// 4-2. (이메일, 전화번호, 이름, 아이디) 쇼핑몰 -> (집주소), 백화점몰 -> (vip등급, 일반등급)
@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록됨
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화, preAuthorize 어노테이션 활성화
public class SecurityConfig {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;
//    private final JwtAuthenticationFilter jwtAuthenticationFilter; // JWT 필터 추가

    // 해당 메서드의 리턴되는 오브젝트를 IOC로 등록해준다.
    /*@Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter, OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler) throws Exception {
//        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());

        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(authorize -> {
            authorize
                    .requestMatchers(HttpMethod.GET, "/record/**").authenticated()  // GET 요청도 인증 필요
                    .requestMatchers("/user/**").authenticated()
                    .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                    .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                    .requestMatchers("/token/**").permitAll() // 🔥 추가: JWT 토큰 관련 엔드포인트 전체 허용
                    .anyRequest().permitAll();
        });

//        http.formLogin(form -> {
//            form.
//                    loginPage("/loginForm")
//                    .loginProcessingUrl("/login") // login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인을 진행
//                    .defaultSuccessUrl("/");
//        });

//        http.oauth2Login(form -> {
//            form
//                    .loginPage("/loginForm")
//                    .userInfoEndpoint(userInfoEndpointConfig -> {
//                        userInfoEndpointConfig.userService(principalOauth2UserService);
//                    }); // 구글 로그인 완료된 뒤의 후처리가 필요함. Tip. 코드X(엑세스토큰 + 사용자프로필정보 O)
//        });



        // 로그인 시 토큰 생성 코드
        http.oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(principalOauth2UserService))
                .successHandler(oAuth2LoginSuccessHandler)); // 로그인 성공 시 JWT 생성 및 반환
//                        .defaultSuccessUrl("/", true));


        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
