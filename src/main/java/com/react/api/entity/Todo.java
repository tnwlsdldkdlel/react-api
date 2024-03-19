package com.react.api.entity;

import java.time.LocalDate;

import com.react.api.dto.TodoDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Column(length = 500, nullable = false)
	private String title;

	private String content;

	private boolean complete;

	private LocalDate dueDate;
	
	public Todo(TodoDto todoDto) {
		this.title = todoDto.getTitle();
		this.content = todoDto.getContent();
		this.complete = false;
		this.dueDate = todoDto.getDueDate();
	}
	
	public void update(TodoDto todoDto) {
		this.title = todoDto.getTitle();
		this.content = todoDto.getContent();
		this.complete = todoDto.isComplete();
		this.dueDate = todoDto.getDueDate();
	}
}
