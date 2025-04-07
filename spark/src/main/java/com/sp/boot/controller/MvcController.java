package com.sp.boot.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class MvcController {
	
	@GetMapping("/")
	public String mainPage() {
		
		
		return "mainPage";
	
	}
	
	

}
