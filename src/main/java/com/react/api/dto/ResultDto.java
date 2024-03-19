package com.react.api.dto;

import com.react.api.util.ResponseCode;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@Getter
public class ResultDto<T> {

	private int code;
	private String msg;
	private T data;
	private PageDto page;

	private ResultDto(int code, String msg, T data, PageDto page) {
		this.code = code;
		this.data = data;
		this.msg = msg;
		this.page = page;
	}
	
	private ResultDto(int code, String msg, T data) {
		this.code = code;
		this.data = data;
		this.msg = msg;
	}

	public static <T> ResultDto<T> success(T data, String message, PageDto page) {
		return new ResultDto<T>(200, message, data, page);
	}
	
	public static <T> ResultDto<T> success(T data, String message) {
		return new ResultDto<T>(200, message, data);
	}

	public static <T> ResultDto<T> fail(ResponseCode responseCode, T data) {
		return new ResultDto<T>(responseCode.getHttpStatusCode(), responseCode.getMessage(), data);
	}

}
