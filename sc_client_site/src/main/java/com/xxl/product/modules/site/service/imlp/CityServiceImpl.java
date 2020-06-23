package com.xxl.product.modules.site.service.imlp;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xxl.product.modules.site.dao.CityDao;
import com.xxl.product.modules.site.entity.City;
import com.xxl.product.modules.site.service.CityService;

@Service
public class CityServiceImpl implements CityService {
	private final static Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);

	@Value("${server.port}")
	private int port;

	@Autowired
	private CityDao cityDao;

	@Override
	public List<City> getCitiesByCountryId(int countryId) {
		LOGGER.debug("Server port:" + port);
		return Optional.ofNullable(cityDao.getCitiesByCountryId(countryId)).orElse(Collections.emptyList());
	}

}
