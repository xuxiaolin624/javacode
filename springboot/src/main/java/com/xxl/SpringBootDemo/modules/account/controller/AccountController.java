package com.xxl.SpringBootDemo.modules.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xxl.SpringBootDemo.modules.account.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private UserService userService;

	// 登录页
	@RequestMapping("/login")
	public String loginPage() {
		return "indexSimple";
	}
	//注销
	@RequestMapping("/logout")
	public String logOut(ModelMap modelMap) {
		userService.logOut();
		modelMap.addAttribute("template","account/login");
		return "indexSimple";
	}

	// 注册页
	@RequestMapping("/register")
	public String registerPage() {
		return "indexSimple";
	}

	@RequestMapping("/users")
	public String userPage() {
		return "index";
	}

	@RequestMapping("/roles")
	public String rolesPage() {
		return "index";
	}

	@RequestMapping("/resources")
	public String resourcesPage() {
		return "index";
	}

	/**
	 * http://127.0.0.1/account/profile
	 */
	@RequestMapping("/profile")
	public String profilePage() {
		return "index";
	}
}
