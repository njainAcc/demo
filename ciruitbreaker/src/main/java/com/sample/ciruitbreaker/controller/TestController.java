package com.sample.ciruitbreaker.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("circuit")
public class TestController {
	
	  @Autowired
	    private CircuitBreakerFactory circuitBreakerFactory;

	  @GetMapping
	  public String getName()
	  {
		  return "neelam";
	  }
	  
	  @GetMapping("/test")
		public String testRestTemplateWithCircuitBreaker()
		{
			System.out.println("-----------getAll--- Changes-------");
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			
			/*
			HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8080/fileupload/emp", HttpMethod.GET, httpEntity, String.class);
			System.out.println("respimseEntity.....123......."+ responseEntity.getBody());
			*/
			
			CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
		    String url = "http://localhost:8089/circuit1";

		    return circuitBreaker.run(() -> restTemplate.getForObject(url, String.class));
			
			//return "string";
		}


}
