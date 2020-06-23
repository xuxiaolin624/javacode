package com.xxl.account.modules.account.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xxl.account.modules.account.entity.City;
import com.xxl.account.modules.account.service.impl.SiteFeignFallBack;

@FeignClient(value = "client‐site", fallback = SiteFeignFallBack.class)
public interface SiteFeignClient {
	
	@RequestMapping("/api/cities/{countryId}")
	List<City> getCitiesByCountryId(@PathVariable int countryId);

}
