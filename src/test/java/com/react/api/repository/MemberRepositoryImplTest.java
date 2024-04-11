package com.react.api.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.react.api.dto.MemberDto;
import com.react.api.util.Value.MEMBER_ROLE;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Transactional
@Log4j2
public class MemberRepositoryImplTest {

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Test
	@Rollback(false)
	public void test() {
		for (int i = 0; i < 10; i++) {
			List<MEMBER_ROLE> test = new ArrayList<>();
			MemberDto memberDto = MemberDto.builder().email("user" + i + "@aaa.com").pw(passwordEncoder.encode("1111"))
					.nickname("USER" + i)
					.build();
			
			test.add(MEMBER_ROLE.USER);
			//memberDto.addRole(MEMBER_ROLE.USER);
			if (i >= 5)
				test.add(MEMBER_ROLE.MANAGER);
				//memberDto.addRole(MEMBER_ROLE.MANAGER);
			if (i >= 8)
				test.add(MEMBER_ROLE.ADMIN);
				//memberDto.addRole(MEMBER_ROLE.ADMIN);
			memberDto.setMemberRoleList(test);
			
			memberRepository.save(memberDto);
		}

	}

}
