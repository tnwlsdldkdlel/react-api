package com.react.api.dto;

import java.time.LocalDate;

import com.react.api.entity.Todo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoDto {

	private Long seq;

	private String title;

	private String content;

	private boolean complete;

	private LocalDate dueDate;

	public TodoDto(Todo todo) {
		this.seq = todo.getSeq();
		this.title = todo.getTitle();
		this.content = todo.getContent();
		this.complete = todo.isComplete();
		this.dueDate = todo.getDueDate();
	}

}
