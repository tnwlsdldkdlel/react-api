package com.react.api.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.react.api.util.CustomJWTException;
import com.react.api.util.JWTUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
public class APIRefreshController {

	@GetMapping("/v1/member/refresh")
	public Map<String, Object> refresh (@RequestHeader("Authorization") String authHeader,
			@RequestParam("refreshToken") String refreshToken) {
		if(refreshToken == null) {
			throw new CustomJWTException("NULL_REFRESH");
		}
		
		if(authHeader == null || authHeader.length() < 7) {
			throw new CustomJWTException("INVALID STRING");
		}
		
		// accessToken 만료여부확인
		String accessToken = authHeader.substring(7);
		if(!JWTUtil.checkExpiredToken(accessToken)) {
			return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
		}
		
		// refreshToken 검증
		Map<String, Object> claims = JWTUtil.validateToken(refreshToken);
		
		log.info("refresh .... claims:" + claims);
		
		// accessToken만료되었으므로 생성
		String newAccessToken = JWTUtil.generateToken(claims, 10);
		
		// refreshToken도 만료시간이 1시간전이면 생성
		String newRefreshToken = JWTUtil.checkTime((Integer) claims.get("exp")) == true ? JWTUtil.generateToken(claims, 60*24) : refreshToken;
		
		return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
	}
}
