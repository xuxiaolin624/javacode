package com.xxl.SpringBootDemo.testController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class Test {
	/*
	 * 127.0.0.1
	 */
	@RequestMapping("/test/demo")
	@ResponseBody
	public String testDemo() {
		return "test";
	}

}
