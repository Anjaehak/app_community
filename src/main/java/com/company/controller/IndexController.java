package com.company.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/app_community/v1/index")
	public String gotoSwaggerUI() {
		return "redirect:/swagger-ui/index.html";
	}
}
