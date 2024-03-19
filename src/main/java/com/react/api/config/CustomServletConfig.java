package com.react.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomServletConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 경로
				.maxAge(500) // 캐싱시간
				.allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS") // 메소드
				.allowedOrigins("*"); // origin 또는 도메인
	}
}
