package com.xxl.SpringBootDemo.modules.common.contoller;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xxl.SpringBootDemo.modules.common.vo.Result;
import com.xxl.SpringBootDemo.modules.common.vo.Result.ResultStatus;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(value = AuthorizationException.class)
	@ResponseBody
	public Result<String> exceptionHandlerFor304() {
		return new Result<String>(ResultStatus.FAILED.status, "访问权限有误。", "/common/403");
	}
}