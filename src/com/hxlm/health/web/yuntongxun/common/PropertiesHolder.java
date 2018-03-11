package com.hxlm.health.web.yuntongxun.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesHolder  extends PropertyPlaceholderConfigurer {
	private static Map<String, String> ctxPropertiesMap;

@Override
protected void processProperties(
		ConfigurableListableBeanFactory beanFactoryToProcess,
		Properties props) throws BeansException {
	super.processProperties(beanFactoryToProcess, props);
	ctxPropertiesMap = new HashMap<String, String>();
	for (Object key : props.keySet()) {
		String keyStr = key.toString();
		String value = props.getProperty(keyStr);
		ctxPropertiesMap.put(keyStr, value);
	}
}

public static String getValue(String keyStr) {
	return ctxPropertiesMap.get(keyStr);
}}
