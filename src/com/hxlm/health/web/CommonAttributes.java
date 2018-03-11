/*
 * 
 * 
 * 
 */
package com.hxlm.health.web;

/**
 * 公共参数
 * 
 * 
 * 
 */
public final class CommonAttributes {

	/** 日期格式配比 */
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

	/** healthlm.xml文件路径 */
	public static final String HEALTHLM_XML_PATH = "/healthlm.xml";

	/** healthlm.properties文件路径 */
	public static final String HEALTHLM_PROPERTIES_PATH = "/healthlm.properties";

	/** aps_development.p12文件路径,测试用的证书文件 */
	public static final String APS_DEVELOPMENT_PATH = "/aps_development.p12";

	/** aps_development.p12文件路径,线上用的证书文件 */
	public static final String APS_PRODUCTION_PATH = "/aps_production.p12";

	/**
	 * 不可实例化
	 */
	private CommonAttributes() {
	}

}