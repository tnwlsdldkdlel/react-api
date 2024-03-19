package com.react.api.util;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ResponseCode {
	
    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, "서버에 오류가 발생하였습니다."),

    // 200 OK
    READ_SUCCESS(HttpStatus.OK, true, null),
    UPDATE_SUCCESS(HttpStatus.OK, true, null),
    DELETE_SUCCESS(HttpStatus.OK, true, null),
    
    // 404 Not Found
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, false, "해당 TODO가 존재하지 않습니다."),
	
	// 201 CREATED POST
	CREATE_SUCCESS(HttpStatus.CREATED, true, null);

	private final HttpStatus httpStatus;
    private final Boolean success;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
