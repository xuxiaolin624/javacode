package com.xxl.product.modules.site.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xxl.product.modules.site.entity.City;
import com.xxl.product.modules.site.service.CityService;

@RestController
@RequestMapping("/api")
public class CityController {
	@Autowired
	private CityService cityService;

	/*
	 * http://127.0.0.1:8761/api/cities/522
	 */
	@RequestMapping(value = "/cities/{countryId}", produces = "application/json")
	public List<City> getCitiesByCountryId(@PathVariable int countryId) {
		return cityService.getCitiesByCountryId(countryId);
	}

}
