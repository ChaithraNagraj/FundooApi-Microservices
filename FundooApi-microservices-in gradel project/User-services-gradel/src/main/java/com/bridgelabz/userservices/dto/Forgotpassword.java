package com.bridgelabz.userservices.dto;

import lombok.Data;

@Data
public class Forgotpassword {
 public String email;

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

}
