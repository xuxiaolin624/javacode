package com.xxl.SpringBootDemo.test.testcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Test {
	@RequestMapping("/test/demo")
	@ResponseBody
	public String testDemo() {
		return "test";
	}

}
