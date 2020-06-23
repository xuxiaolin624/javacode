package com.xxl.product.modules.site.service;

import java.util.List;

import com.xxl.product.modules.site.entity.City;

public interface CityService {
	List<City> getCitiesByCountryId(int countryId);

}
