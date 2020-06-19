package com.xxl.SpringBootDemo.modules.account.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxl.SpringBootDemo.modules.account.dao.UserDao;
import com.xxl.SpringBootDemo.modules.account.entity.User;
import com.xxl.SpringBootDemo.modules.account.service.UserService;
import com.xxl.SpringBootDemo.modules.common.vo.Result;
import com.xxl.SpringBootDemo.modules.common.vo.Result.ResultStatus;
import com.xxl.SpringBootDemo.modules.common.vo.SearchVo;
import com.xxl.SpringBootDemo.utils.MD5Util;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	// 根据名字查询当前注册用户是否存在
	@Override
	public User getUserByUserName(String userName) {
		return userDao.getUserByUserName(userName);
	}

	@Override
	@Transactional
	public Result<User> insertUser(User user) {
		User userTemp = getUserByUserName(user.getUserName());
		// 判断是否存在
		if (userTemp != null) {
			return new Result<User>(ResultStatus.FAILD.status, "账户已存在，请重新输入。");
		} else {
			user.setCreateDate(new Date());
			user.setPassword(MD5Util.getMD5(user.getPassword()));
			userDao.insertUser(user);
			return new Result<User>(ResultStatus.SUCCESS.status, "注册成功。", user);
		}
	}

	@Override
	public Result<User> login(User user) {
		User userTemp = userDao.getUserByUserName(user.getUserName());
		if (userTemp == null || !userTemp.getPassword().equals(MD5Util.getMD5(user.getPassword()))) {
			return new Result<User>(ResultStatus.SUCCESS.status, "用户名或密码错误。");
		}
		return new Result<User>(ResultStatus.SUCCESS.status, "登录成功。");
	}

	// 分页查询
	@Override
	public PageInfo<User> getUsersBySarchVo(SearchVo searchVo) {
		searchVo.initSearchVo();
		PageHelper.startPage(searchVo.getCurrentPage(), searchVo.getPageSize());
		return new PageInfo<User>(
				Optional.ofNullable(userDao.getUsersBySarchVo(searchVo)).orElse(Collections.emptyList()));
	}

}
