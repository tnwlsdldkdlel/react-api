package com.react.api.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.react.api.dto.PageDto;
import com.react.api.dto.TodoDto;
import com.react.api.entity.QTodo;
import com.react.api.entity.Todo;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

@Repository
@Transactional(readOnly = true)
@Slf4j
public class TodoRepositoryImpl implements TodoRepository {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	public TodoRepositoryImpl(EntityManager em, JPAQueryFactory queryFactory) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<TodoDto> findAll(PageDto pageDto) {
		QTodo qTodo = QTodo.todo;

		QueryResults<TodoDto> queryResults = queryFactory
			.select(Projections.constructor(TodoDto.class, qTodo))
			.from(qTodo)
			.offset((pageDto.getPage()- 1) * pageDto.getSize())
			.limit(pageDto.getSize())
			.orderBy(qTodo.seq.desc())
			.fetchResults();
		
		pageDto.setAmount((int)queryResults.getTotal());
		return queryResults.getResults();
	}

	@Override
	public TodoDto find(Long seq) {
		Todo todo = em.find(Todo.class, seq);
		
		if(todo == null) {
			return null;
		} else {
			return new TodoDto(todo);
		}
	}

	@Transactional
	@Override
	public void save(TodoDto todoDto) {
		em.persist(new Todo(todoDto));
	}

	@Transactional
	@Override
	public void update(TodoDto todoDto) {
		Todo todo = em.find(Todo.class, todoDto.getSeq());
		
		if(todo != null) {
			todo.update(todoDto);
		}
	}

	@Transactional
	@Override
	public void delete(Long seq) {
		QTodo qTodo = QTodo.todo;
		
		queryFactory
			.delete(qTodo)
			.where(qTodo.seq.eq(seq))
			.execute();
	}

}
