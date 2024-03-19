package com.react.api.repository;

import java.util.List;

import com.react.api.dto.PageDto;
import com.react.api.dto.TodoDto;

public interface TodoRepository {

	public List<TodoDto> findAll(PageDto pageDto);
	
	public TodoDto find(Long seq);
	
	public void save(TodoDto todoDto);
	
	public void update(TodoDto todoDto);
	
	public void delete(Long seq);
}
