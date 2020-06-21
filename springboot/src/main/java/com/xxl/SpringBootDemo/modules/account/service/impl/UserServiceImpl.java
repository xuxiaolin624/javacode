package com.xxl.SpringBootDemo.modules.account.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxl.SpringBootDemo.config.ResourceConfigBean;
import com.xxl.SpringBootDemo.modules.account.dao.UserDao;
import com.xxl.SpringBootDemo.modules.account.dao.UserRoleDao;
import com.xxl.SpringBootDemo.modules.account.entity.Role;
import com.xxl.SpringBootDemo.modules.account.entity.User;
import com.xxl.SpringBootDemo.modules.account.service.UserService;
import com.xxl.SpringBootDemo.modules.common.vo.Result;
import com.xxl.SpringBootDemo.modules.common.vo.Result.ResultStatus;
import com.xxl.SpringBootDemo.modules.common.vo.SearchVo;
import com.xxl.SpringBootDemo.utils.FileUtil;
import com.xxl.SpringBootDemo.utils.MD5Util;

@Service
public class UserServiceImpl implements UserService {

	private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private ResourceConfigBean resourceConfigBean;

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
			userRoleDao.deleteRoleByUserId(user.getUserId());
			List<Role> roles = user.getRoles();
			if (roles != null && roles.size() > 0) {
				for (Role role : roles) {
					userRoleDao.insertUserRole(user.getUserId(), role.getRoleId());
				}
			}
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
	@Transactional
	public PageInfo<User> getUsersBySearchVo(SearchVo searchVo) {
		searchVo.initSearchVo();
		PageHelper.startPage(searchVo.getCurrentPage(), searchVo.getPageSize());
		return new PageInfo<User>(
				Optional.ofNullable(userDao.getUsersBySearchVo(searchVo)).orElse(Collections.emptyList()));
	}

	@Override
	public User getUserByUserId(int userId) {
		return userDao.getUserByUserId(userId);
	}

	@Override
	public Result<User> updateUser(User user) {
		User userTemp = getUserByUserName(user.getUserName());
		if (userTemp != null && userTemp.getUserId() != user.getUserId()) {
			return new Result<User>(ResultStatus.FAILD.status, "账户已存在，请重新输入。");
		} else {
			userDao.updateUser(user);
			userRoleDao.deleteRoleByUserId(user.getUserId());
			List<Role> roles = user.getRoles();
			if (roles != null && roles.size() > 0) {
				for (Role role : roles) {
					userRoleDao.insertUserRole(user.getUserId(), role.getRoleId());
				}
			}
			return new Result<User>(ResultStatus.SUCCESS.status, "修改成功。", user);
		}
	}

	@Override
	public Result<Object> deleteUser(int userId) {
		userDao.deleteUser(userId);
		userRoleDao.deleteRoleByUserId(userId);
		return new Result<Object>(ResultStatus.SUCCESS.status, "删除成功。");

	}

	// 完成修改和新增操作
	@Override
	@Transactional
	public Result<User> editUser(User user) {
		User userTemp = getUserByUserName(user.getUserName());
		if (userTemp != null && userTemp.getUserId() != user.getUserId()) {
			return new Result<User>(ResultStatus.FAILD.status, "账户已存在，请重新输入。");
		}
		user.setPassword(MD5Util.getMD5(user.getPassword()));
		user.setCreateDate(new Date());
		userDao.updateUser(user);
		userRoleDao.deleteRoleByUserId(user.getUserId());
		if (user.getUserId() != null) {
			userDao.updateUser(user);
			userRoleDao.deleteRoleByUserId(user.getUserId());
		} else {
			userDao.insertUser(user);
		}
		List<Role> roles = user.getRoles();
		if (roles != null && roles.size() > 0) {
			for (Role role : roles) {
				userRoleDao.insertUserRole(user.getUserId(), role.getRoleId());
			}

		}
		
		/*
		 * // 管理员编辑用户信息时，只修改用户角色 if (user.getUserId() > 0) { userDao.updateUser(user);
		 * // userDao.updateUser(user);
		 * userRoleDao.deletUserRoleByUserId(user.getUserId()); } else {
		 * userDao.insertUser(user); } List<Role> roles = user.getRoles(); if (roles !=
		 * null && roles.size() > 0) { for (Role role : roles) {
		 * userRoleDao.addUserRole(user.getUserId(), role.getRoleId()); } }
		 */
		
		return new Result<User>(ResultStatus.SUCCESS.status, "编辑成功。", user);
	}

	@Override
	public Result<String> uploadUserImage(MultipartFile userImage) {

		if (userImage.isEmpty()) {
			return new Result<>(ResultStatus.FAILD.status, "用户图片为空。");
		}
		if (!FileUtil.isImage(userImage)) {
			return new Result<>(ResultStatus.FAILD.status, "所选择的文件不是图片。");
		}
		String originalFilename = userImage.getOriginalFilename();
		String relatedPath = resourceConfigBean.getResourcePath() + originalFilename;
		String destPath = String.format("%s%s", resourceConfigBean.getLocalPathForWindow(), originalFilename);
		try {
			File destFile = new File(destPath);
			userImage.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			LOGGER.debug(e.getMessage());
			e.printStackTrace();
			return new Result<>(ResultStatus.FAILD.status, "文件上传失败。");
		}
		return new Result<>(ResultStatus.SUCCESS.status, "文件上传成功。", relatedPath);
	}

	@Override
	@Transactional
	public Result<User> updateUserProfile(User user) {
		User userTemp = getUserByUserName(user.getUserName());
		if (userTemp != null && userTemp.getUserId() != user.getUserId()) {
			return new Result<User>(ResultStatus.FAILD.status, "用户名已存在。");
		}
		userDao.updateUser(user);
		return new Result<User>(ResultStatus.SUCCESS.status, "编辑成功。", user);
	}

}











