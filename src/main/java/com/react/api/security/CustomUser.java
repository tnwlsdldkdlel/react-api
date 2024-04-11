package com.react.api.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

	private String email;
	private String pw;
	private String nickname;
	private boolean social;
	private List<String> roleNames = new ArrayList<>();

	public CustomUser(String email, String pw, String nickname, boolean social, List<String> roleNames) {
		super(email, pw,
				roleNames.stream().map(str -> new SimpleGrantedAuthority("ROLE_" + str)).collect(Collectors.toList()));
		// spring security는 role_형태로 인식을 하기때문에 변환.
		// spring객체에 부여된 권한의 표현을 저장.
		this.email = email;
		this.pw = pw;
		this.nickname = nickname;
		this.social = social;
		this.roleNames = roleNames;
	}
	
	// jwt 내용물을 claim 이라고 함.
	public Map<String, Object> getClaims() {
		Map<String, Object> dataMap = new HashMap<>();

		dataMap.put("email", email);
		dataMap.put("pw", pw);
		dataMap.put("nickname", nickname);
		dataMap.put("roleNames", roleNames);

		return dataMap;
	}
}
