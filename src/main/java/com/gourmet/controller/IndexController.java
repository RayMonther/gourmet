package com.gourmet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/gourmet")
public class IndexController {
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String showProducts() {		
		return "home";		
	}
}
