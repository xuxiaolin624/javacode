package com.xxl.SpringBootDemo.modules.test.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xxl.SpringBootDemo.modules.common.vo.SearchVo;
import com.xxl.SpringBootDemo.modules.test.entity.City;

@Mapper
public interface CityDao {
	// 根据id查询
	//List<City> getCitiesByCountryId(int countryId);
	// 注解实现
	@Select("select * from m_city where country_id = #{countryId}")
	List<City> getCitiesByCountryId(int countryId);

	@Select("select * from m_city where city_name=#{cityName} and local_city_name=#{localCityName}")
	City getCityByName(String cityName, String localCityName);

	//多条件查询
	@Select("<script>" + 
			"select * from m_city "
			+ "<where> "
			+ "<if test='keyWord != \"\" and keyWord != null'>"
			+ " and (city_name like '%${keyWord}%' or local_city_name like '%${keyWord}%') "
			+ "</if>"
			+ "</where>"
			+ "<choose>"
			+ "<when test='orderBy != \"\" and orderBy != null'>"
			+ " order by ${orderBy} ${sort}"
			+ "</when>"
			+ "<otherwise>"
			+ " order by city_id desc"
			+ "</otherwise>"
			+ "</choose>"
			+ "</script>")
	List<City> getCitiesBySarchVo(SearchVo searchVo);
	
	@Insert("insert into m_city (city_name,local_city_name,country_id,date_created) "
			+ "values (#{cityName},#{localCityName},#{countryId},#{dateCreated})")
	@Options(useGeneratedKeys = true,keyColumn = "country_id",keyProperty = "countryId")
	void insertCity(City city);
	
	@Update("update m_city set local_city_name=#{localCityName} where city_id = #{cityId}")
	void updateCity(City city);
	
	@Update("DELETE FROM m_city  WHERE city_id = #{cityId}")
	void deleteCity(int cityId);
	
}






