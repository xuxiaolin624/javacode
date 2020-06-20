package com.xxl.SpringBootDemo.modules.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.xxl.SpringBootDemo.modules.account.entity.User;

@Mapper
public interface UserRoleDao { 

	@Delete("delete from user_role where user_id=#{userId}")
	void deleteRoleByUserId(Integer userId);

	@Insert("insert into user_role (user_id,role_id) value (#{userId},#{roleId})")
	void insertUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

	@Select("select * from user where user_id=#{userId}")
	@Results(id = "userResult", value = { @Result(column = "user_id", property = "userId"),
			@Result(column = "user_id", property = "roles", javaType = List.class, many = @Many(select = "com.xxl.SpringBootDemo.modules.account.dao."
					+ "RoleDao.getRolesByUserId")) })
	public User getUserByUserId(Integer userId);
}
