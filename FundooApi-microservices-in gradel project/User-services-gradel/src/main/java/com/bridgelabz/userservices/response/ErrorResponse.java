package com.bridgelabz.userservices.response;

import org.springframework.http.HttpStatus;

import lombok.Data;
/***
 * 
 * @author chaithra B N
 *
 */
@Data
public class ErrorResponse {
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

	public ErrorResponse(String message, HttpStatus statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}
	
	public ErrorResponse() {
	
	}

}
