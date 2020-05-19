package com.bridgelabz.userservices.dto;

import javax.persistence.Embedded;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class UsersDto {
@Embedded
private String name;
private String email;
private String password;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
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
public Long getMobileNumber() {
	return mobileNumber;
}
public void setMobileNumber(Long mobileNumber) {
	this.mobileNumber = mobileNumber;
}
private Long mobileNumber;
 
}
 
