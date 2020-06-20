package com.xxl.SpringBootDemo.modules.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xxl.SpringBootDemo.modules.account.entity.Role;
import com.xxl.SpringBootDemo.modules.account.service.RoleService;

@RestController
@RequestMapping("/api")
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/roles")
	List<Role> getRoles(){
		return roleService.getRoles();
	}
	

}
