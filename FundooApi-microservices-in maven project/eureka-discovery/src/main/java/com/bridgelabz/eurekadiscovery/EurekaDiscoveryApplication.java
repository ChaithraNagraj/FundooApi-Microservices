package com.bridgelabz.eurekadiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
/**
 * In this class we are providing a server or  we can say that we are creating a eureka server which we will help to register the eureka client for 
 * communication between the microservices / between the different api's
 *
 * @author chaithra B N
 *
 */

@SpringBootApplication
@EnableEurekaServer
public class EurekaDiscoveryApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(EurekaDiscoveryApplication.class, args);
	}

}
