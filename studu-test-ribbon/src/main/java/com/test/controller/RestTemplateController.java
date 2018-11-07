package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class RestTemplateController {
	
	@Autowired
    private RestTemplate restTemplate;
	
	@GetMapping("test")
	public String demo(){
		 return restTemplate.getForObject("http://STUDY/study/userController/getUser", String.class);  
	}
	
}
