/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity - entity敏捷开发
 * 
 * Created by ZYR-PC on 2017-02-20.
 * 
 */
public class QuickDevelopment {

	/** 名称 */
	private String name;

	/** 中文名称 */
	private String chineseName;

	/** 属性 */
	private List<EntityAttribute> entityAttributes = new ArrayList<EntityAttribute>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public List<EntityAttribute> getEntityAttributes() {
		return entityAttributes;
	}

	public void setEntityAttributes(List<EntityAttribute> entityAttributes) {
		this.entityAttributes = entityAttributes;
	}
}
