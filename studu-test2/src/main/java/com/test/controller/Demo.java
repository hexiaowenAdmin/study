package com.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Demo {
	
	@GetMapping("demo")
	public ModelAndView demo(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("a");
		return modelAndView;
	}
}
