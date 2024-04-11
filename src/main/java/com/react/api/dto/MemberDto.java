package com.react.api.dto;

import java.util.ArrayList;
import java.util.List;

import com.react.api.entity.Member;
import com.react.api.util.Value.MEMBER_ROLE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MemberDto {

	private String email;
	private String pw;
	private String nickname;
	private boolean social;
	private List<MEMBER_ROLE> memberRoleList = new ArrayList<>();

	public MemberDto(Member member) {
		this.email = member.getEmail();
		this.pw = member.getPw();
		this.nickname = member.getNickname();
		this.memberRoleList = member.getMemberRoleList();
	}

	public void addRole(MEMBER_ROLE role) {
		this.memberRoleList.add(role);
	}

}
