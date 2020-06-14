package com.xxl.SpringBootDemo.modules.test.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.xxl.SpringBootDemo.modules.common.vo.Result;
import com.xxl.SpringBootDemo.modules.common.vo.SearchVo;
import com.xxl.SpringBootDemo.modules.test.entity.City;

public interface CityService {

	List<City> getCitiesByCountryId(int countryId);

	City getCityByName(String cityName, String localCityName);

	PageInfo<City> getCitiesByPage(int currentPage, int pageSize, int countryId);

	PageInfo<City> getCitiesBySarchVo(SearchVo searchVo);
	//插入
	Result<City> insertCity(City city);
	//修改
	Result<City> updateCity(City city);
	//删除
	Result<Object> deleteCity(int cityId);

	Object migrateCitiesByCountryId(int countryId);
}
