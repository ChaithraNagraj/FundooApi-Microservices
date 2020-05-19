package com.bridgelabz.zuulservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
/**
 * Zuul Server is a gateway application that handles all the requests and does the dynamic routing of microservice applications
 * @author chaithra B N 
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy

public class ZuulserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulserviceApplication.class, args);
	}

}
