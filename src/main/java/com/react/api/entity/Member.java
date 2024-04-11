package com.react.api.entity;

import java.util.ArrayList;
import java.util.List;

import com.react.api.dto.MemberDto;
import com.react.api.util.Value.MEMBER_ROLE;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = "memberRoleList")
public class Member {

	@Id
	private String email;

	private String pw;

	private String nickname;

	private boolean social;

	private List<MEMBER_ROLE> memberRoleList = new ArrayList<>();

	@Builder
	public Member(String email, String pw, String nickname, boolean social, List<MEMBER_ROLE> memberRoleList) {
		this.email = email;
		this.pw = pw;
		this.nickname = nickname;
		this.social = social;
		this.memberRoleList = memberRoleList;
	}
	
	public Member(MemberDto memberDto) {
		this.email = memberDto.getEmail();
		this.pw = memberDto.getPw();
		this.nickname = memberDto.getNickname();
		this.memberRoleList = memberDto.getMemberRoleList();
	}

	public void addRole(MEMBER_ROLE role) {
		this.memberRoleList.add(role);
	}

	public void clearRole() {
		this.memberRoleList.clear();
	}

	public void changeNickname(String nickname) {
		this.nickname = nickname;
	}

	public void changePw(String pw) {
		this.pw = pw;
	}

	public void changeSocial(boolean social) {
		this.social = social;
	}

}
