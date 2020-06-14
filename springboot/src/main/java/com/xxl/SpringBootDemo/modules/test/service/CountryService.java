package com.xxl.SpringBootDemo.modules.test.service;

import com.xxl.SpringBootDemo.modules.test.entity.Country;

public interface CountryService {
	Country getCountryByCountryId(int countryId);

	Country getCountryByCountryName(String countryName);
	
	
}
