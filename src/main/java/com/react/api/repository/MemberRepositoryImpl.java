package com.react.api.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.react.api.dto.MemberDto;
import com.react.api.entity.Member;

import jakarta.persistence.EntityManager;

@Repository
@Transactional(readOnly = true)
public class MemberRepositoryImpl implements MemberRepository {
	
	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	public MemberRepositoryImpl(EntityManager em, JPAQueryFactory queryFactory) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Member getWriteRoles(String email) {
		Member member = em.find(Member.class, email);
		return member;
	}

	@Override
	public void save(MemberDto memberDto) {
		Member member = new Member(memberDto);
		em.persist(member);
	}

}
