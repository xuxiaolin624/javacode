package com.xxl.SpringBootDemo.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.xxl.SpringBootDemo.filter.ParameterFilter;
import com.xxl.SpringBootDemo.interceptor.UrlInterceptor;

/**
 * WebMvc相关配置器
 * 
 * @author xxl
 *
 */
@Configuration
@AutoConfigureAfter({ WebMvcAutoConfiguration.class })
public class WebMvcConfig implements WebMvcConfigurer {
	@Value("${server.http.port}")
	private int httpPort;
	@Autowired
	private UrlInterceptor urlInterceptor;
	@Autowired
	private ResourceConfigBean resourceConfigBean;

	/*
	 * 为 HTTP 添加连接器，并将连接器加入 servlet 容器里面
	 */
	@Bean
	public Connector connector() {
		Connector connector = new Connector();
		connector.setScheme("http");
		connector.setPort(httpPort);
		return connector;
	}

	@Bean
	public ServletWebServerFactory servletWebServerFactory() {
		TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
		// 连接器注入到factory
		serverFactory.addAdditionalTomcatConnectors(connector());
		return serverFactory;
	}

	/*
	 * 注册过滤器
	 */
	@Bean
	public FilterRegistrationBean<ParameterFilter> filter() {
		FilterRegistrationBean<ParameterFilter> register = new FilterRegistrationBean<ParameterFilter>();
		register.setFilter(new ParameterFilter());
		return register;
	}

	/*
	 * 重写添加拦截器方法
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(urlInterceptor).addPathPatterns("/**");

	}

	// 配置静态资源文件夹
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String systemName = System.getProperty("os.name");// 得到系统属性
		if (systemName.toLowerCase().startsWith("win")) {
			registry.addResourceHandler(resourceConfigBean.getResourcePathPattern())
					.addResourceLocations(ResourceUtils.FILE_URL_PREFIX + resourceConfigBean.getLocalPathForWindow());
		} else {
			registry.addResourceHandler(resourceConfigBean.getResourcePathPattern())
					.addResourceLocations(ResourceUtils.FILE_URL_PREFIX + resourceConfigBean.getLocalPathForLinux());
		}
	}

}
