package com.xxl.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class MyZuulFilter extends ZuulFilter {

	@Override 
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext requestContext = new RequestContext();
		HttpServletRequest servletRequest = requestContext.getRequest();
		String token = servletRequest.getParameter("token");
		if (token == null || token == "") {
			// 过滤该请求，不对其进行路由
			requestContext.setSendZuulResponse(false);
			// 设置响应状态码
			requestContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
			// 设置响应体
			requestContext.setResponseBody("Token is empty.");
		}

		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

}
