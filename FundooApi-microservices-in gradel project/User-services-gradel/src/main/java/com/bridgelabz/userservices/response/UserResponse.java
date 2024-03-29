package com.bridgelabz.userservices.response;

import lombok.Data;
/**
 * 
 * @author chaithra B N
 * 
 *
 */
@Data
public class UserResponse {

	private String message;
	private Object object;
	
	public UserResponse(String message, Object object) {
		super();
		this.message = message;
		this.object = object;
	}
}
