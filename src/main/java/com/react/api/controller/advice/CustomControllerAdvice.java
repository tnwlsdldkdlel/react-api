package com.react.api.controller.advice;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.react.api.util.CustomJWTException;

@RestController
public class CustomControllerAdvice {

	@ExceptionHandler(CustomJWTException.class)
	protected ResponseEntity<?> handleJWTException(CustomJWTException e) {
		String msg = e.getMessage();
		return ResponseEntity.ok().body(Map.of("error", msg));
	}
}
