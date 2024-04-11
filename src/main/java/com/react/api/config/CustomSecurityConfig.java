package com.react.api.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.react.api.security.filter.JWTCheckFilter;
import com.react.api.security.handler.APILoginFailHandler;
import com.react.api.security.handler.APILoginSuccessHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class CustomSecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		log.info("======================= security config =======================");
		
		httpSecurity.cors(httpSecurityCorsConfigurer -> {
			httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
		});

		// 세션을 생성하지 않음 -> api와 같이 상태를 유지하지않고 각 요청이 독립적으로 처리되는 경우 사용.
		httpSecurity.sessionManagement(
				sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.NEVER));

		httpSecurity.csrf(config -> config.disable()); // csrf: 리퀘스트 위조, 위조를 막기위해 토근을 하여하지만 api서버 만들땐 사용안함.

		httpSecurity.formLogin(config -> {
			// 로그인 파라미터 : username, password가 디폴트값.
			config.usernameParameter("email");
			config.passwordParameter("pw");
			
			// 로그인 api , post인 경우 loadUserByUsername를 호출.
			config.loginPage("/v1/member/login");
			
			// 성공했을 경우 호출 -> 따로 bean등록 안해도됨.
			config.successHandler(new APILoginSuccessHandler());
			
			// 실했을 경우
			config.failureHandler(new APILoginFailHandler());
		});
		
		// JWTCheckFilter는 UsernamePasswordAuthenticationFilter 이전에 실행
		httpSecurity.addFilterBefore(new JWTCheckFilter(), UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();
	}

	// 계정 암호화
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// cors setting
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(Arrays.asList("*")); // 모두 허용
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}
