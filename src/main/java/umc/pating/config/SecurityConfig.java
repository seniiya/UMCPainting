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

        System.out.println("🚀 SecurityFilterChain 실행됨! 🔥");
        http.csrf(csrf -> {
            System.out.println("📌 CSRF 설정: 비활성화됨");
            csrf.disable();
        });

        http.cors(cors -> {
            System.out.println("📌 CORS 설정 적용됨");
            cors.configurationSource(request -> {
                org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
                config.setAllowedOrigins(java.util.List.of("*")); // 모든 도메인 허용
                config.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(java.util.List.of("Authorization", "Content-Type"));
                config.setExposedHeaders(java.util.List.of("Authorization")); // Authorization 헤더 노출
                return config;
            });
        });

        http.authorizeHttpRequests(authorize -> {
            System.out.println("✅ 인증 및 권한 설정 적용됨");
            authorize
                    .requestMatchers(HttpMethod.GET, "/record/**").authenticated()  // GET 요청도 인증 필요
                    .requestMatchers("/user/**").authenticated()
                    .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                    .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                    .requestMatchers("/token/**").permitAll() // 🔥 추가: JWT 토큰 관련 엔드포인트 전체 허용
                    .anyRequest().permitAll();
        });

        http.formLogin(form -> {
            form.
                    loginPage("/loginForm")
                    .loginProcessingUrl("/login") // login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인을 진행
                    .defaultSuccessUrl("/", true);
        });

//        http.oauth2Login(form -> {
//            form
//                    .loginPage("/loginForm")
//                    .userInfoEndpoint(userInfoEndpointConfig -> {
//                        userInfoEndpointConfig.userService(principalOauth2UserService);
//                    }); // 구글 로그인 완료된 뒤의 후처리가 필요함. Tip. 코드X(엑세스토큰 + 사용자프로필정보 O)
//        });



//        // 로그인 시 토큰 생성 코드
//        http.oauth2Login(oauth2 -> oauth2
//                .userInfoEndpoint(userInfo -> userInfo
//                        .userService(principalOauth2UserService))
//                .successHandler(oAuth2LoginSuccessHandler)); // 로그인 성공 시 JWT 생성 및 반환
////                        .defaultSuccessUrl("/", true));
//
//
//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.oauth2Login(oauth2 -> {
            System.out.println("✅ OAuth2 로그인 설정 적용됨");
            oauth2
                    .userInfoEndpoint(userInfo -> {
                        System.out.println("🔍 사용자 정보 엔드포인트 설정됨");
                        userInfo
                                .userService(principalOauth2UserService);
                        System.out.println("✅ OAuth2LoginSuccessHandler가 등록됨");
                    })
                    .successHandler(oAuth2LoginSuccessHandler);
        }); // 로그인 성공 시 JWT 생성 및 반환

        System.out.println("✅ JWT 인증 필터 추가됨");
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
