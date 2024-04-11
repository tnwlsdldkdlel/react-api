package com.react.api.repository;

import com.react.api.dto.MemberDto;
import com.react.api.entity.Member;

public interface MemberRepository {
	
	public void save(MemberDto memberDto);

	public Member getWriteRoles(String email);
}
