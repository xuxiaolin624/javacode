package com.xxl.SpringBootDemo.modules.test.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xxl.SpringBootDemo.config.ResourceConfigBean;
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
	@Autowired
	private ResourceConfigBean resourceConfigBean;
//	@Autowired
//	private ResponseEntity<Resource> responseEntity;

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
		modelMap.addAttribute("shopLogo", "/upload/11.jpg");
		modelMap.addAttribute("country", country);
		modelMap.addAttribute("cities", cities);
		modelMap.addAttribute("updateCityUri", "/api/update");
		// modelMap.addAttribute("template", "test/index");
		return "index";
	}

	// 单文件上传
	@PostMapping(value = "/file", consumes = "multipart/form-data")
	public String uploadFile(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes) {
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select file");
			return "redirect:/test/index";
		}
		String resourcePath = resourceConfigBean.getResourcePath() + file.getOriginalFilename();
		try {
			File destFile = new File(ResourceUtils.getURL(resourcePath).getPath());
			file.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "Upload fail.");
			return "redirect:/test/index";
		}
		redirectAttributes.addFlashAttribute("message", "Upload success.");
		redirectAttributes.addFlashAttribute("resourcePath", resourcePath);
		return "redirect:/test/index";
	}

	// 多文件上传
	@PostMapping(value = "/files", consumes = "multipart/form-data")
	public String uploadFiles(@RequestParam MultipartFile[] files, RedirectAttributes redirectAttributes) {
		boolean isEmpty = true;
		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue;
			}
			try {
				String destFilePath = resourceConfigBean.getResourcePath() + file.getOriginalFilename();
				File destFile = new File(ResourceUtils.getURL(destFilePath).getPath());
				file.transferTo(destFile);
				isEmpty = false;
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("message", "Upload fail.");
				return "redirect:/test/index";
			}
		}
		if (isEmpty) {
			redirectAttributes.addFlashAttribute("message", "Please select file");
		} else {
			redirectAttributes.addFlashAttribute("message", "Upload success.");
		}
		return "redirect:/test/index";
	}

	// 文件下载
	@RequestMapping("/download")
	@ResponseBody
	public ResponseEntity<Resource> download(@RequestParam String fileName) {
		try {
			String resourcePath = resourceConfigBean.getResourcePathPattern() + fileName;
			Resource resource = new UrlResource(ResourceUtils.getURL(resourcePath));
			//Resource resource = new UrlResource(Paths.get(ResourceUtils.getURL(resourcePath).getPath()).toUri());
			//Resource resource = new UrlResource(Paths.get(resourceConfigBean.getResourcePathPattern() + fileName).toUri());
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
					.header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", fileName))
					.body(null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将文件以BufferedInputStream的方式读取到byte[]里面，然后用OutputStream.write输出文件
	 */
	@RequestMapping("/download1")
	public void downloadFile1(HttpServletRequest request, HttpServletResponse response, @RequestParam String fileName) {
		String filePath = "D:/upload" + File.separator + fileName;
		File downloadFile = new File(filePath);
		if (downloadFile.exists()) {
			response.setContentType("application/octet-stream");
			response.setContentLength((int) downloadFile.length());
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", fileName));
			byte[] buffer = new byte[1024];
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				fis = new FileInputStream(downloadFile);
				bis = new BufferedInputStream(fis);
				OutputStream os = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1) {
					os.write(buffer, 0, i);
					i = bis.read(buffer);
				}
			} catch (Exception e) {
				LOGGER.debug(e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					if (fis != null) {
						fis.close();
					}
					if (bis != null) {
						bis.close();
					}
				} catch (Exception e2) {
					LOGGER.debug(e2.getMessage());
					e2.printStackTrace();
				}
			}
		}
	}

	/**
	 * 以包装类 IOUtils 输出文件
	 */
	@RequestMapping("/download2")
	public void downloadFile2(HttpServletRequest request, HttpServletResponse response, @RequestParam String fileName) {
		String filePath = "D:/upload" + File.separator + fileName;
		File downloadFile = new File(filePath);
		try {
			if (downloadFile.exists()) {
				response.setContentType("application/octet-stream");
				response.setContentLength((int) downloadFile.length());
				response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
						String.format("attachment; filename=\"%s\"", fileName));
				InputStream is = new FileInputStream(downloadFile);
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			e.printStackTrace();
		}
	}
}