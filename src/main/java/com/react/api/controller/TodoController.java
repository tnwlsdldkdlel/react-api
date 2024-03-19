package com.react.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.react.api.dto.PageDto;
import com.react.api.dto.ResultDto;
import com.react.api.dto.TodoDto;
import com.react.api.repository.TodoRepository;
import com.react.api.util.ResponseCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController	
@RequiredArgsConstructor
@RequestMapping("/v1/todo")
@Slf4j
public class TodoController {
	
	private final TodoRepository todoRepository;
	
	@GetMapping("/list")
	public ResultDto<List<TodoDto>> list(PageDto pageDto) {
		try {
			List<TodoDto> todos = todoRepository.findAll(pageDto);
			return ResultDto.success(todos, ResponseCode.READ_SUCCESS.getMessage(), pageDto);
		} catch (Exception e) {
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}
		
	}
	
	@GetMapping("/{seq}")
	public ResultDto<TodoDto> find(@PathVariable("seq") Long seq) {
		try {
			TodoDto todoDto = todoRepository.find(seq);
			
			if(todoDto == null) {
				return ResultDto.fail(ResponseCode.TODO_NOT_FOUND, null);
			} else {
				return ResultDto.success(todoDto, ResponseCode.READ_SUCCESS.getMessage());
			}
		} catch (Exception e) {
			log.info(e.toString());
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}
	}
	
	@PostMapping("")
	public ResultDto<TodoDto> save(@RequestBody TodoDto todoDto) {
		try {
			todoRepository.save(todoDto);
			return ResultDto.success(null, ResponseCode.CREATE_SUCCESS.getMessage());
		} catch (Exception e) {
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}
	}
	
	@PutMapping("")
	public ResultDto<TodoDto> update(@RequestBody TodoDto todoDto) {
		try {
			todoRepository.update(todoDto);
			return ResultDto.success(null, ResponseCode.UPDATE_SUCCESS.getMessage());
		} catch (Exception e) {
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}
	}
	
	@DeleteMapping("/{seq}")
	public ResultDto<TodoDto> delete(@PathVariable(name = "seq") Long seq) {
		try {
			todoRepository.delete(seq);
			return ResultDto.success(null, ResponseCode.DELETE_SUCCESS.getMessage());
		} catch (Exception e) {
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}
		
	}
}
