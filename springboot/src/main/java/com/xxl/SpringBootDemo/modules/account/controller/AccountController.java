package com.xxl.SpringBootDemo.modules.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {	
	
	//登录页
	@RequestMapping("/login")
	public String loginPage() {
		return "indexSimple";
		
	}

}
