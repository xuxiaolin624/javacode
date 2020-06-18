package com.xxl.SpringBootDemo.modules.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xxl.SpringBootDemo.modules.account.entity.User;
import com.xxl.SpringBootDemo.modules.account.service.UserService;
import com.xxl.SpringBootDemo.modules.common.vo.Result;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 127.0.0.1/api/user ---- post
	 */
	@PostMapping(value = "/user", consumes = "application/json")
	public Result<User> insertUser(@RequestBody User user) {
		return userService.insertUser(user);
	}
	
	
	
	/**
	 * 127.0.0.1/api/login ---- post
	 */
	@PostMapping(value = "/login", consumes = "application/json")
	public Result<User> login(@RequestBody User user) {
		return userService.login(user);
	}
}
