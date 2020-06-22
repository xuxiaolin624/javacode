package com.xxl.SpringBootDemo.modules.common.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/common")
public class CommonContoller {
	
	@RequestMapping("/dashboard")
	public String dashboardPage() {
		return "index";
	}
	
	@RequestMapping("/403")
	public String errorPageFor403() {
		return "index";
	}

}
