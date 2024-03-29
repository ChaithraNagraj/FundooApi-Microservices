package com.bridgelabz.userservices.services;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.userservices.controller.UserController;
import com.bridgelabz.userservices.customexception.ExitsEmailException;
import com.bridgelabz.userservices.customexception.MailNotFoundException;
import com.bridgelabz.userservices.customexception.UserNotFoundException;
import com.bridgelabz.userservices.customexception.UserNotVerifiedException;
import com.bridgelabz.userservices.dto.LoginDto;
import com.bridgelabz.userservices.dto.UpdatePassword;
import com.bridgelabz.userservices.dto.UsersDto;
import com.bridgelabz.userservices.model.UsersEntity;
import com.bridgelabz.userservices.repository.IUsersRepository;
import com.bridgelabz.userservices.repository.UsersRepository;
import com.bridgelabz.userservices.response.EmailData;
import com.bridgelabz.userservices.utility.EmailProviderService;
import com.bridgelabz.userservices.utility.JWTGenerator;

import lombok.extern.slf4j.Slf4j;
/*
 * Implementation for the service declaration
 */

	@Slf4j
	@Service
	public class UserImplementation implements IUsersServices {
		private static Logger log = LoggerFactory.getLogger(UserImplementation.class);

		private UsersEntity user = new UsersEntity();
		@Autowired
		private IUsersRepository userRepository;
		@Autowired
		private UsersRepository repository;
		@Autowired
		private BCryptPasswordEncoder encryptPass;
		@Autowired
		private JWTGenerator generateToken;
		@Autowired
		private EmailData emailData;

	//   
//		@Autowired
//		private RedisCacheRepository redis;

		@Autowired
		private EmailProviderService em;

//		@Autowired
//		private RabbitMQSender rabbitMqSender;

		
		@Transactional
		public boolean addUsers(UsersDto usersdto) {
			// UsersEntity user = new UsersEntity();
			Optional<UsersEntity> emailExists = userRepository.findOneByEmail(usersdto.getEmail());
			if (emailExists.isPresent()) {
				throw new ExitsEmailException("u have been already registered", HttpStatus.NOT_FOUND);
			}

			BeanUtils.copyProperties(usersdto, user);

			user.setPassword(encryptPass.encode(usersdto.getPassword()));

			user.setDate(LocalDateTime.now());

			user.setVerified(false);

			userRepository.save(user);
			/*
			 * redisCache saving
			 */
			// redis.save(user); //need serialization

			String token = generateToken.generateWebToken(user.getUserId());
			/*
			 * token to collect
			 */
			log.info("your token to collect-" + token);
			//System.out.println("your token to collect-" + token);

			//String url = "http://localhost:4200/userVerification/";
			String url = "http://localhost:8081/fundoo/User-Services/users/verify/";
			String body = url + token;

			emailData.setEmail(usersdto.getEmail());

			emailData.setSubject("click below link to verify your registration");

			emailData.setBody(body);

			em.sendMail(emailData.getEmail(), emailData.getSubject(), emailData.getBody());
			// rabbitMqSender.send(emailData);
			return true;
		}

		@Transactional
		public UsersEntity getuserById(String token) {
			return user;
			// return redis.findByuserId(token);
		}

		@Override
		@Transactional
		public boolean verify(String token) {

			long userid = generateToken.parseJWTToken(token);

			Optional<UsersEntity> checkUser = userRepository.findById(userid);

			if (checkUser.isPresent()) {
				checkUser.get().setVerified(true);
				userRepository.save(checkUser.get());
				return true;
			} else {
				return false;
			}
		}
	//
//		@Transactional
//		public UsersEntity findByEmail(String email_id) {
//			Session session = entityManager.unwrap(Session.class);
//			Query<?> query = session.createQuery("from UsersEntity where email=:email_id");
//			query.setParameter("email_id", email_id);
//			return (UsersEntity) query.uniqueResult();
	//
//		}

		@Override
		public UsersEntity login(LoginDto logindto) {
			UsersEntity userPresent =repository.getusersByemail(logindto.getEmail());
			
			//Optional<UsersEntity> userPresent = userRepository.findOneByEmail(logindto.getEmail());
			log.info("user------------sdgisdgifgsdfiuiugf----------------------------------------" + userPresent);
			//System.out.println("user------------sdgisdgifgsdfiuiugf----------------------------------------" + userPresent);

			if (userPresent!=null) {
				log.info("Check------------------------------------odhwfonefonfonf");
				log.info("------------------------password getted:----" + logindto.getPassword());
				//System.out.println("Check------------------------------------odhwfonefonfonf");
				//System.out.println("------------------------password getted:----" + logindto.getPassword());


				if (userPresent.isVerified()
						&& encryptPass.matches(logindto.getPassword(), userPresent.getPassword())) {
					return userPresent;
				} else {
//					String body = "http://localhost:4200/userVerification/"
//							+ generateToken.generateWebToken(userPresent.getUserId());
					
					String body ="http://localhost:8081/fundoo/User-Services/users/verify/"
							+ generateToken.generateWebToken(userPresent.getUserId());
					em.sendMail(logindto.getEmail(), "login failed click here for verification", body);
					throw new UserNotVerifiedException("Not a valid credentials", HttpStatus.NOT_ACCEPTABLE);
				}
			} // if
			else {

				throw new MailNotFoundException("user not found", HttpStatus.NOT_FOUND);
			}

		}

//		@Override
//		public List<UsersEntity> getUserDetails() {
//			List<UsersEntity> users = new ArrayList<>();
//			userRepository.findAll().forEach(users::add);
//			return users;
//		}

		@Override
		public boolean isUserAlreadyRegistered(String email) {

			Optional<UsersEntity> isUserAlreadyRegistered = userRepository.findOneByEmail(email);
			log.info("my token:" + generateToken.generateWebToken(isUserAlreadyRegistered.get().getUserId()));
			//System.out.println("my token:" + generateToken.generateWebToken(isUserAlreadyRegistered.get().getUserId()));

			if (isUserAlreadyRegistered.isPresent() && isUserAlreadyRegistered.get().isVerified()) {
				log.info("----------present-------" + isUserAlreadyRegistered.get().isVerified());
				//System.out.println("----------present-------" + isUserAlreadyRegistered.get().isVerified());

				String body = "http://localhost:8081/fundoo/user-service/updatePassword/"
						+ generateToken.generateWebToken(isUserAlreadyRegistered.get().getUserId());

				em.sendMail(isUserAlreadyRegistered.get().getEmail(), "Updated", body);

				return true;
			} else {
				return false;
			}
		}

		@Override
		public UsersEntity updatePassword(String token, UpdatePassword password) {

			if (password.getNewPassword().equals(password.getChangepassword())) {

				long userId = generateToken.parseJWTToken(token);

				log.info("Bosssssssssss----------");
				//System.out.println("Bosssss----------");

				UsersEntity isUserPresent = repository.getusersByid(userId);// userRepository.findById(userId);
				
				log.info("-----------------------is user is present is null or not check============== = = = = =  = = ="
						+ isUserPresent);
				//System.out.println("-----------------------is user is present is null or not check============== = = = = =  = = ="
					//+ isUserPresent);

				
				if (isUserPresent != null) {

					String encryptpass = encryptPass.encode(password.getNewPassword());

					isUserPresent.setPassword(encryptpass);

					userRepository.save(isUserPresent);

					return isUserPresent;

				}

			}
			return null;
		}

		@Override
		public UsersEntity getUser(String token) {
			long userId = generateToken.parseJWTToken(token);
			UsersEntity user=repository.getusersByid(userId);
			if(user!=null){
			return user;
			}
			else
			throw new UserNotFoundException("user Not found",HttpStatus.NOT_FOUND);
		
		}

		@Override
		public List<UsersEntity> fetchUsers() {
			List<UsersEntity> users =repository.getAllUsers();
			return users;
		}


		
	}