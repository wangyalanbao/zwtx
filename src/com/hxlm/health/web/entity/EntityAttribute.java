/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.entity;

/**
 * Entity - entity属性
 * 
 * Created by ZYR-PC on 2017-02-20.
 * 
 */
public class EntityAttribute {

	/** 类型 */
	private String type;

	/** 名称 */
	private String name;

	/** 备注 */
	private String memo;

	/** 是否必填 */
	private Boolean isRequired;

	/** 最大长度 */
	private Integer length;

	/** 是否以此为筛选 */
	private Boolean isSelect;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Boolean getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Boolean getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(Boolean isSelect) {
		this.isSelect = isSelect;
	}
}
