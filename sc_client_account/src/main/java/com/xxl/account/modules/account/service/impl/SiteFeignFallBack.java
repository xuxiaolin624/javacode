package com.xxl.account.modules.account.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.xxl.account.modules.account.entity.City;
import com.xxl.account.modules.account.service.SiteFeignClient;

@Component
public class SiteFeignFallBack implements SiteFeignClient {

	@Override
	public List<City> getCitiesByCountryId(int countryId) {
		return new ArrayList<City>();
	}

}
