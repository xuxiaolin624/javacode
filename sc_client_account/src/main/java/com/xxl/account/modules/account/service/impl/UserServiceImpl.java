package com.xxl.account.modules.account.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xxl.account.modules.account.dao.UserDao;
import com.xxl.account.modules.account.entity.City;
import com.xxl.account.modules.account.entity.User;
import com.xxl.account.modules.account.service.SiteFeignClient;
import com.xxl.account.modules.account.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
//	@Autowired
//	private RestTemplate restTemplate;
	@Autowired
	private SiteFeignClient siteFeignClient;

	@Override
	// @HystrixCommand(fallbackMethod = "getUserByUserIdFallbackMethod")
	public User getUserByUserId(int userId) {
		User user = userDao.getUserByUserId(userId);
		// List<City> cities = restTemplate
		// .getForObject("http://CLIENT-SITE/api/cities/{countryId}", List.class, 522);
		user.setCities(siteFeignClient.getCitiesByCountryId(522));
		return user;
	}

	public User getUserByUserIdFallbackMethod(int userId) {
		User user = userDao.getUserByUserId(userId);
		user.setCities(new ArrayList<City>());
		return user;
	}

}
