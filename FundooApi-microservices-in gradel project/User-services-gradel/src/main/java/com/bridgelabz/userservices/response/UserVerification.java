package com.bridgelabz.userservices.response;

import lombok.Getter;
/**
 * @getter  annotation not working so generated manualy
 * @author chaithra B N
 *
 */
@Getter
public class UserVerification {
	private String token;  
	private String message;
	private  Object obj;
	public UserVerification(String token, String message, Object obj) {
		super();
		this.token = token;
		this.message=message;
		
		this.obj = obj;
	}

}
