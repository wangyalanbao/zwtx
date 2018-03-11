/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.entity;

/** import -- start */
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
/** import -- end */

/**
 * Entity - 快捷模板
 * 
 * Created by testPerson on createTime.
 * 
 */
@Entity
@Table(name = "lm_test_mould")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_test_mould_sequence")
public class TestMould extends OrderEntity {

	/** 开始增加属性 */
	/** 开始增加get,set方法 */
}