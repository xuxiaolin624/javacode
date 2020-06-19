package com.xxl.SpringBootDemo.modules.account.service;

import com.github.pagehelper.PageInfo;
import com.xxl.SpringBootDemo.modules.account.entity.User;
import com.xxl.SpringBootDemo.modules.common.vo.Result;
import com.xxl.SpringBootDemo.modules.common.vo.SearchVo;

public interface UserService {
	// 插入用户-注册
	Result<User> insertUser(User user);

	// 根据姓名查询用户
	User getUserByUserName(String userName);
 
	// 登录
	Result<User> login(User user);
	
	PageInfo<User> getUsersBySarchVo(SearchVo searchVo);
}
