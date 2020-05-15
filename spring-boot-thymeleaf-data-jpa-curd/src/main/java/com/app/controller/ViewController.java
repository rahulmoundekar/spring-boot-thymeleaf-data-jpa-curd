package com.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

	@RequestMapping("dashboard")
	public String dashboard() {
		return "employee//dashboard";
	}

	@RequestMapping("about")
	public String about() {
		return "employee//about";
	}

	@RequestMapping("contact")
	public String contact() {
		return "employee//contact";
	}
}
