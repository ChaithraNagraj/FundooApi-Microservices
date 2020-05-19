package com.bridgelabz.userservices.customexception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message;
	private HttpStatus statusCode;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UserNotFoundException(String message, HttpStatus statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}


}
