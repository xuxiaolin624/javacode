package com.xxl.SpringBootDemo.modules.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Test01 {

	@Value("${server.port}")
	private int port;
	private String name;
	private int age;

	@Autowired
	private ApplicationTest applicationTest;

//	@RequestMapping("/tes/con")
	@ResponseBody
	public String configinfo() {
		StringBuffer sb = new StringBuffer();
		sb.append(port).append("111")
			.append(name).append("--")
			.append(age).append("--").append("<b>");
		
		sb.append(applicationTest.getName())
			.append(applicationTest.getAge());
		return sb.toString();
	}

}
