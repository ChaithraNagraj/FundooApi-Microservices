package com.bridgelabz.notesservices.configuration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced; 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * In this  class we are proving a config of rest template,rest template:comunication between service
 * @LoadBalanced:It indicates the Rest template should be based on Client Side Load Balancing 
 * using Ribbon and checks Eureka server for resolving the service name to host/port.
 * @author chaithra B N
 *
 */

@Configuration
public class RestConfig{

		@Bean
		@LoadBalanced
		public RestTemplate getTemplate()
		{
			return new RestTemplate();
		}
	
}
