package com.xxl.SpringBootDemo.modules.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.xxl.SpringBootDemo.modules.common.vo.Result;
import com.xxl.SpringBootDemo.modules.common.vo.SearchVo;
import com.xxl.SpringBootDemo.modules.test.entity.City;
import com.xxl.SpringBootDemo.modules.test.service.CityService;

@RestController
@RequestMapping("/api")
public class CityController {

	@Autowired
	private CityService cityService;

	/**
	 * 127.0.0.1/api/cities/
	 */
	@RequestMapping("/cities/{countryId}")
	public List<City> getCitiesByCountryId(@PathVariable int countryId) {
		return cityService.getCitiesByCountryId(countryId);
	}

	/**
	 * 127.0.0.1/api/city?cityName=Shanghai&localCityName=1111 ---- get
	 */
	@RequestMapping("/city")
	public City getCityByName(@RequestParam String cityName, @RequestParam(required = false) String localCityName) {
		return cityService.getCityByName(cityName, localCityName);
	}

	/*
	 * 127.0.0.1/api/cities?currentPage=1$pageSize=5$countryId=522
	 */
	@RequestMapping("/cities")
	public PageInfo<City> getCitiesByPage(@RequestParam int currentPage, @RequestParam int pageSize,
			@RequestParam int countryId) {
		return cityService.getCitiesByPage(currentPage, pageSize, countryId);
	}

	/**
	 * 127.0.0.1/api/cities
	 * 
	 * @param searchVo
	 * @return
	 */
	// @RequestMapping(value = "/cities",method = RequestMethod.POST,consumes =
	// "application/json")//默认get请求
	@PostMapping(value = "/cities", consumes = "application/json")
	public PageInfo<City> getCitiesBySarchVo(@RequestBody SearchVo searchVo) {
		return cityService.getCitiesBySarchVo(searchVo);
	}

	/*
	 * 127.0.0.1/api/insert
	 */
	@PostMapping(value = "/insert", consumes = "application/json")
	public Result<City> insertCity(@RequestBody City city) {
		return cityService.insertCity(city);
	}

	/*
	 * 127.0.0.1/api/update-put
	 */
	@PutMapping(value = "/update", consumes = "application/x-www-form-urlencoded")
	public Result<City> updateCity(@ModelAttribute City city) {
		return cityService.updateCity(city);
	}

	/*
	 * 127.0.0.1/api/delete/2258-put
	 */
	@DeleteMapping(value = "/delete/{cityId}")
	public Result<Object> deleteCity(@PathVariable int cityId) {
		return cityService.deleteCity(cityId);
	}

	/*
	 * 127.0.0.1/api/redis/cities/522
	 */
	@RequestMapping("/redis/cities/{countryId}")
	public Object migrateCitiesByCountryId(@PathVariable int countryId) {
		return cityService.migrateCitiesByCountryId(countryId);
	}

}
