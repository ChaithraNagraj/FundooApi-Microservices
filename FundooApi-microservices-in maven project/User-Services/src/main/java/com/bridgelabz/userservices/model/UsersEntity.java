package com.bridgelabz.userservices.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Entity
@Table(name = "Userdata")
@Component
public class UsersEntity {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Column(name = "user_id")
	// @NotNull
	private long userId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	@Column(name = "user_Name")
	private String name;

	@Column(name = "email", unique = true)
	// @Size(min = 8)
	// @NotNull
	private String email;

	// @Column(name = "Password" )
	// @Size(min = 8)
	private String password;

//	@Column(name = "mobile_number")
	// @Size(min = 10, max = 12)
//	@NotNull
	private Long mobileNumber;

//	@Column(name = "Registered_date")
	// @NotNull
	private LocalDateTime date;

	@Column(name = "is_verified")
	// @NotNull
	private boolean isVerified;
}