package com.bridgelabz.userservices.controller;


import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.userservices.dto.Forgotpassword;
import com.bridgelabz.userservices.dto.LoginDto;
import com.bridgelabz.userservices.dto.UpdatePassword;
import com.bridgelabz.userservices.dto.UsersDto;
import com.bridgelabz.userservices.model.UsersEntity;
import com.bridgelabz.userservices.response.Response;
import com.bridgelabz.userservices.response.UserVerification;
import com.bridgelabz.userservices.services.IUsersServices;
import com.bridgelabz.userservices.utility.JWTGenerator;

import lombok.extern.slf4j.Slf4j;
/***
 * 
 * @author chaithra B N
 *
 */

@Slf4j
@RestController
public class UserController {
	private static Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UsersEntity user;
	@Autowired
	private JWTGenerator generateToken;

	@Autowired
	private IUsersServices usersService;
	
//	@Autowired
//	private ProfileServiceImp profileService;
	/**
	 * API for the Registration
	 * 
	 * @param information
	 * @return Status and the Body
	 */

	@PostMapping("/users/register")
	// @RequestMapping(method = RequestMethod.POST,value = "users/register")
	public ResponseEntity<Response> userRegistration(@RequestBody UsersDto userdto) {
		try {
			if (usersService.addUsers(userdto)) {
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new Response("U have been Registered Successfully",userdto));
			} else {
				return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
						.body(new Response("U are already Registered"));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

//	@PostMapping(value = "/users")
//	public List<UsersEntity> getUsers() {
//		return usersService.getUserDetails();
//	}
	/**
	 * API for the Verify the Token
	 * 
	 * @param token
	 * @return Body and Status
	 * @throws Exception
	 */

	@PutMapping("/users/verify/{token}")
	public ResponseEntity<Response> userVerification(@PathVariable("token") String token) {
		
		

		if (usersService.verify(token)) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("U have verified Successfully", 200));
		}

		else {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("not verified", 400));

		}
	}

	@GetMapping(value = "/users/{token}")
	public ResponseEntity<Response> getUserById(@PathVariable("token") String token) {
		UsersEntity user=usersService.getuserById(token);
		if(user!=null) {
		return ResponseEntity.status(HttpStatus.OK).body(new Response("User is fetched",user));
	}
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Unable to  fetch",user));
		}
	/**
	 * API for the Login
	 * 
	 * @param information
	 * @return Status and Body
	 */
	@PostMapping("/users/login")
	public ResponseEntity<UserVerification> login(@RequestBody LoginDto loginData) {
		UsersEntity userLogin = usersService.login(loginData);
		String token = generateToken.generateWebToken(userLogin.getUserId());
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserVerification(token,"u have been Successfully Login",userLogin));
	}
	/**
	 * API for the update the Detail
	 * 
	 * @param token
	 * @param update
	 * @return Status and Body
	 */
	@PutMapping("/users/updatePassword/{token}")
	public ResponseEntity<Response> updatePassword(@Valid @PathVariable("token") String token,
			@RequestBody UpdatePassword password) {
		
		log.info("token"+token);
		log.info("pass"+password.getNewPassword());
		log.info("pass"+password.getChangepassword());
		//System.out.println("token"+token);
		//System.out.println("pass"+password.getNewPassword());
		//System.out.println("pass"+password.getChangepassword());
		user = usersService.updatePassword(token, password);
		if (user != null)
			return ResponseEntity.status(HttpStatus.OK).body(new Response(token,"password is updated Succesfully"));
		else
		{
			//log.info("Babe boss..");
			System.out.println("Babe boss..");

			return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
					.body(new Response("password and confirm password is not matched"));
		}
	}
	
	/**
	 * API for the Forgot Password
	 * 
	 * @param email
	 * @return Respective Status and The Body
	 */


	@PostMapping("/users/forgotPassword")
	public ResponseEntity<Response> forgotPassword(@RequestBody Forgotpassword forgotPassword){
		boolean result =usersService.isUserAlreadyRegistered(forgotPassword.getEmail());
		if(result) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("user exits",200,forgotPassword.getEmail()));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("user does not exist with given email id", 400, forgotPassword.getEmail()));
		
	}
	
	@GetMapping("/users/getUser/{token}")
	public UsersEntity getUser(@PathVariable String token)
	{

		UsersEntity user = usersService.getUser(token);
		if(user!=null) {
			
			return user;
			}
		return null;
	}
	/**
	 * API to get all the Details
	 * 
	 * @return status and body
	 */
	@GetMapping("/users/getUsers")
	public ResponseEntity<Response> getUsers()
	{
		List <UsersEntity> user = usersService.fetchUsers();
		return ResponseEntity.status(HttpStatus.OK).body(new Response("users found" , user));
	}


}
