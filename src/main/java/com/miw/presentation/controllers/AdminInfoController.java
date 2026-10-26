package com.miw.presentation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminInfoController {

	@GetMapping("/adminInfo")
	public String adminInfo() {
		return "adminInfo";
	}

}
