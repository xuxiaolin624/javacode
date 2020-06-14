package com.xxl.SpringBootDemo.modules.test.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xxl.SpringBootDemo.modules.test.entity.City;
import com.xxl.SpringBootDemo.modules.test.entity.Country;
import com.xxl.SpringBootDemo.modules.test.service.CityService;
import com.xxl.SpringBootDemo.modules.test.service.CountryService;

@Controller
@RequestMapping("/test")
public class TestController {
	private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

	@Value("${server.port}")
	private int port;
	@Value("${com.test.name}")
	private String name;
	@Value("${com.test.age}")
	private int age;
	@Value("${com.test.desc}")
	private String desc;
	@Value("${com.test.random}")
	private String random;
	@Autowired
	private ApplicationTest applicationTest;
	@Autowired
	private CityService cityService;
	@Autowired
	private CountryService countryService;

	@ResponseBody
	@RequestMapping("/log")
	public String logTest() {
		// TRACE<DEBUG<INFO<WARN<ERROR
		LOGGER.trace("This is trace log");
		LOGGER.debug("This is DEBUG log");
		LOGGER.info("This is INFO log");
		LOGGER.warn("This is WARN log");
		LOGGER.error("This is ERROR log");
		return "This is log test.";
	}

	@ResponseBody
	@RequestMapping("/test")
	public String testDemo() {
		return "test%%%%%%%%%%%%%%%";
	}

	@ResponseBody
	@RequestMapping("/config")
	public String configinfo() {
		StringBuffer sb = new StringBuffer();
		sb.append(port).append("111").append(name).append("--").append(age).append("--").append("<b>");

		sb.append(applicationTest.getName()).append(applicationTest.getAge());
		return sb.toString();
	}
	
	/**
	 * 127.0.0.1/test/desc?key=fuck
	 */
	@RequestMapping("/desc")
	@ResponseBody
	public String testDesc(HttpServletRequest request, @RequestParam String key) {
		String key2 = request.getParameter("key");
		return "This is test module desc.112233 " + key + "=" + key2;
	}

	@RequestMapping("/index")
	public String indexPage(ModelMap modelMap) {
		// int countryId = 522;
		List<City> cities = cityService.getCitiesByCountryId(522);
		// 限制显示10条，并以list集合返回
		cities = cities.stream().limit(10).collect(Collectors.toList());
		Country country = countryService.getCountryByCountryId(522);
		modelMap.addAttribute("thymeleafTitle", "abcdefg");
		modelMap.addAttribute("checked", true);
		modelMap.addAttribute("currentNumber", 99);
		modelMap.addAttribute("changeType", "checkbox");
		modelMap.addAttribute("baiduUrl", "/test/log");
		modelMap.addAttribute("city", cities.get(0));
		modelMap.addAttribute("shopLogo",
				"http://cdn.duitang.com/uploads/item/201308/13/20130813115619_EJCWm.thumb.700_0.jpeg");
		modelMap.addAttribute("country", country);
		modelMap.addAttribute("cities", cities);
		modelMap.addAttribute("updateCityUri", "/api/update");
		//modelMap.addAttribute("template", "test/index");
		return "index";
	}

}