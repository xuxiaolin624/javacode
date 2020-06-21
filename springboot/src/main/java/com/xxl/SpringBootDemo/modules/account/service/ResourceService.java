package com.xxl.SpringBootDemo.modules.account.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.xxl.SpringBootDemo.modules.account.entity.Resource;
import com.xxl.SpringBootDemo.modules.common.vo.Result;
import com.xxl.SpringBootDemo.modules.common.vo.SearchVo;

public interface ResourceService {

	Result<Resource> editResource(Resource resource);

	Result<Resource> deleteResource(int resourceId);

	PageInfo<Resource> getResources(SearchVo searchVo);

	List<Resource> getResourcesByRoleId(int roleId);
  
	Resource getResourceById(int resourceId);
}