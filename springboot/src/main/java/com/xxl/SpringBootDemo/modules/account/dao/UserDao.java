package com.xxl.SpringBootDemo.modules.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.xxl.SpringBootDemo.modules.account.entity.User;
import com.xxl.SpringBootDemo.modules.common.vo.SearchVo;

@Mapper
public interface UserDao {

	@Insert("insert into user (user_name, password, create_date) "
			+ "values (#{userName}, #{password}, #{createDate})")
	@Options(useGeneratedKeys = true, keyColumn = "user_id", keyProperty = "userId")
	void insertUser(User user);
	 
	@Select("select * from user where user_name = #{userName}")
	User getUserByUserName(String userName);
	
	//多条件查询
		@Select("<script>" + 
				"select * from user "
				+ "<where> "
				+ "<if test='keyWord != \"\" and keyWord != null'>"
				+ " and (user_id like '%${keyWord}%' or user_name like '%${keyWord}%'  ) "//单匹配
				+ "</if>"
				+ "</where>"
				+ "<choose>"
				+ "<when test='orderBy != \"\" and orderBy != null'>"
				+ " order by ${orderBy} ${sort}"
				+ "</when>"
				+ "<otherwise>"
				+ " order by userId desc"
				+ "</otherwise>"
				+ "</choose>"
				+ "</script>")
		List<User> getUsersBySarchVo(SearchVo searchVo);
}
