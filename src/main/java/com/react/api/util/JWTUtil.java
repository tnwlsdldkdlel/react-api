package com.react.api.util;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JWTUtil {

	private static String key = "1234567890123456789012345678901234567890"; // 30글자 이상 사용.

	public static String generateToken(Map<String, Object> valueMap, int min) {
		SecretKey key = null;

		try {
			key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		String jwtStr = Jwts.builder().setHeader(Map.of("typ", "JWT")).setClaims(valueMap)
				.setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
				.setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant())).signWith(key).compact();

		return jwtStr;
	}

	public static Map<String, Object> validateToken(String token) {
		Map<String, Object> claim = null;

		try {
			SecretKey key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
			claim = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token) // 파싱 및 검증, 실패 시 에러
					.getBody();
		} catch (MalformedJwtException malformedJwtException) {
			throw new CustomJWTException("MalFormed");
		} catch (ExpiredJwtException expiredJwtException) {
			throw new CustomJWTException("Expired");
		} catch (InvalidClaimException invalidClaimException) {
			throw new CustomJWTException("Invalid");
		} catch (JwtException jwtException) {
			throw new CustomJWTException("JWTError");
		} catch (Exception e) {
			throw new CustomJWTException("Error");
		}

		return claim;
	}

	public static boolean checkTime(Integer exp) {
		// JWT exp를 날짜로 변환
		java.util.Date expDate = new java.util.Date((long) exp * (1000));
		// 현재 시간과의 차이 계산 - 밀리세컨즈
		long gap = expDate.getTime() - System.currentTimeMillis();
		// 분단위 계산
		long leftMin = gap / (1000 * 60);
		// 1시간도 안남았는지..
		return leftMin < 60;
	}

	public static boolean checkExpiredToken(String token) {
		try {
			validateToken(token);
		} catch (CustomJWTException ex) {
			if (ex.getMessage().equals("Expired")) {
				return true;
			}
		}
		return false;
	}

}
