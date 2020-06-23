package com.xxl.account.modules.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xxl.account.modules.account.entity.User;
import com.xxl.account.modules.account.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;
	
	/*
	 * http://127.0.0.1:8762/api/user/2
	 */
	@RequestMapping(value = "/user/{userId}", produces = "application/json")
	public User getUserByUserId(@PathVariable int userId){
		return userService.getUserByUserId(userId);
	}
}
