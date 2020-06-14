package com.xxl.SpringBootDemo.modules.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xxl.SpringBootDemo.modules.test.entity.Country;
import com.xxl.SpringBootDemo.modules.test.service.CountryService;

@RestController
@RequestMapping("/api")
public class CountryController {
	@Autowired
	private CountryService countryService;
	
	@RequestMapping("/country/{countryId}")
	public Country getCountryByCountryId(@PathVariable int countryId) {
		return countryService.getCountryByCountryId(countryId);
	}

	/**
	 * 127.0.0.1/api/country?countryName=China
	 */
	@RequestMapping("/country")
	public Country getCountryByCountryName(@RequestParam String countryName) {
		return countryService.getCountryByCountryName(countryName);
	}
	

}
