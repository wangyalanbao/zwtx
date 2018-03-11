/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service.impl;

import javax.annotation.Resource;

import com.hxlm.health.web.dao.ParameterDao;
import com.hxlm.health.web.entity.Parameter;
import com.hxlm.health.web.service.ParameterService;

import org.springframework.stereotype.Service;

/**
 * Service - 参数
 * 
 * 
 * 
 */
@Service("parameterServiceImpl")
public class ParameterServiceImpl extends BaseServiceImpl<Parameter, Long> implements ParameterService {

	@Resource(name = "parameterDaoImpl")
	public void setBaseDao(ParameterDao parameterDao) {
		super.setBaseDao(parameterDao);
	}

}