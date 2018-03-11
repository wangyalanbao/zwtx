/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service;

import com.hxlm.health.web.entity.Sn;

/**
 * Service - 序列号
 * 
 * 
 * 
 */
public interface SnService {

	/**
	 * 生成序列号
	 * 
	 * @param type
	 *            类型
	 * @return 序列号
	 */
	String generate(Sn.Type type);

}