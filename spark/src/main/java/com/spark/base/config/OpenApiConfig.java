package com.spark.base.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi(){

        return new OpenAPI()
            .info(new Info()
                .version("v1.0.0") // 현재 문서 버전
                .description("Spark 프로젝트에 사용되는 API 목록 문서화") // 부제목
                .title("Spark 프로젝트 API 목록")) // 제목
            .servers(List.of(
                new Server()
                    .description("개발용 서버") // 해당 url서버에 대해 해당 설명제공
                    .url("http://localhost:8888/spark")

            ))
            .components(new Components()
                .addSecuritySchemes("JWT",new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP) // http 기반 타입
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .in(SecurityScheme.In.HEADER) // JWT 위치는 header
                    .name("Authorization")) // header 키값이름
            ).addSecurityItem(new SecurityRequirement().addList("JWT")); // JWT 보안요구사항 추가;

    }

}
