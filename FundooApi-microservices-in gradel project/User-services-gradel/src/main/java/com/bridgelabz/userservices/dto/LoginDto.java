package com.bridgelabz.userservices.dto;

import org.springframework.stereotype.Component;

import lombok.Data;
@Data
@Component
public class LoginDto {
	private String email;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private  String password;

	}
