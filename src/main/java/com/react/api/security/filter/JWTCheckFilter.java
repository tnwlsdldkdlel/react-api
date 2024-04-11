package com.react.api.security.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;
import com.react.api.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

// OncePerRequestFilter : 모든 리퀘스트에서 동작.
@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {

	// 리퀘스트 중에서 check해야할 것가 아닌거 구분.
	// return 값이 false이면 체크하는거 true이면 체크안하는 거.
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();
		log.info("check uri => " + path);

		return false;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("================================");

		log.info("================================");

		log.info("================================");

		try {
			// 일반적으로 토큰은 요청 헤더의 Authorization 필드에 담아져 보내집니다. Authorization: <type>
			// <credentials>
			// HTTP Authorization 헤더의 Bearer 자격 증명으로 액세스 토큰을 API에 전송해야 합니다. -> 클라이언트와 서버 모두
			// 구현하기 쉬움
			String authHeaderStr = request.getHeader("Authorization");

			// Bearer : JWT 혹은 OAuth에 대한 토큰을 사용한다. (RFC 6750) , jwt문자열 Bearer는 <type>에
			// bearer //공백포함 7문자 + jwt토큰 -> 앞의 7문자를 잘라줘야함
			String asscessToken = authHeaderStr.substring(7);
			Map<String, Object> claims = JWTUtil.validateToken(asscessToken);

			log.info(claims);

		} catch (Exception e) {
			Gson gson = new Gson();
			String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));
			response.setContentType("application/json");
			PrintWriter printWriter = response.getWriter();
			printWriter.println(msg);
			printWriter.close();

		}

		filterChain.doFilter(request, response);
	}

}
