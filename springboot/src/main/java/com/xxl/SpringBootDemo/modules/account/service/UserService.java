package com.xxl.SpringBootDemo.modules.account.service;

import com.github.pagehelper.PageInfo;
import com.xxl.SpringBootDemo.modules.account.entity.User;
import com.xxl.SpringBootDemo.modules.common.vo.Result;
import com.xxl.SpringBootDemo.modules.common.vo.SearchVo;

public interface UserService { 
	// 插入用户-注册
	public Result<User> insertUser(User user);

	// 根据姓名查询用户
	public User getUserByUserName(String userName);

	// 登录
	public Result<User> login(User user);

	public PageInfo<User> getUsersBySearchVo(SearchVo searchVo);

	public User getUserByUserId(int userId);

	public Result<User> updateUser(User user);

	public Result<Object> deleteUser(int userId);
	
	
	
	
}
