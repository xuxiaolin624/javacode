package com.xxl.SpringBootDemo.modules.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.xxl.SpringBootDemo.modules.account.entity.Role;

@Mapper
public interface RoleDao {
	@Select("select * from role")
	List<Role> getRoles();

	@Insert("insert into role (role_name) values (#{roleName})")
	@Options(useGeneratedKeys = true, keyColumn = "role_id", keyProperty = "roleId")
	public void insertRole(Role role);

	@Select("select * from role role left join user_role userRole on role.role_id = userRole.role_id where userRole.user_id=#{userId}")
	public List<Role> getRolesByUserId(int userId);

}


















