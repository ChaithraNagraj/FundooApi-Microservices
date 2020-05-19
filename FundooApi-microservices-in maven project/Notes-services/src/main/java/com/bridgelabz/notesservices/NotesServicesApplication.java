package com.bridgelabz.notesservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * In this main class we are enabling eureka client to register with the eureka server and the swagger 
 * @author chaithra B N
 *
 */
@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
public class NotesServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesServicesApplication.class, args);
	}

}
