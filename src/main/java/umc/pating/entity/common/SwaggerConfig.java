package umc.pating.entity.common;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        String jwtSchemeName = "jwtAuth";

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.APIKEY) // APIKEY 방식으로 설정
                                .type(SecurityScheme.Type.HTTP)  // http bearer 방식
                                .scheme("bearer")  // Bearer 인증 방식 추가
                                .bearerFormat("JWT")  // JWT 사용 명확히 지정
                                .in(SecurityScheme.In.HEADER) // 헤더에서 인증 값 읽기
                                .description("Enter your JWT token without 'Bearer ' prefix")))
                .addSecurityItem(new SecurityRequirement().addList(jwtSchemeName))
                .addServersItem(new Server().url("http://13.124.52.202:8080").description("배포된 서버"))
                .addServersItem(new Server().url("http://localhost:8080").description("로컬"))
                // url 안에 api.도메인 주소.com =>
                .info(new Info()
                        .title("API Documentation")
                        .version("1.0.0")
                        .description("API Documentation without Bearer prefix in Authorization header"));
    }
}
