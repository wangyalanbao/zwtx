/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.dao.MemberAttributeDao;
import com.hxlm.health.web.entity.Member;
import com.hxlm.health.web.entity.MemberAttribute;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Dao - 会员注册项
 * 
 * 
 * 
 */
@Repository("memberAttributeDaoImpl")
public class MemberAttributeDaoImpl extends BaseDaoImpl<MemberAttribute, Long> implements MemberAttributeDao {

	public Integer findUnusedPropertyIndex() {
		for (int i = 0; i < Member.ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
			String jpql = "select count(*) from MemberAttribute memberAttribute where memberAttribute.propertyIndex = :propertyIndex";
			Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("propertyIndex", i).getSingleResult();
			if (count == 0) {
				return i;
			}
		}
		return null;
	}

	public List<MemberAttribute> findList() {
		String jpql = "select memberAttribute from MemberAttribute memberAttribute where memberAttribute.isEnabled = true order by memberAttribute.order asc";
		return entityManager.createQuery(jpql, MemberAttribute.class).setFlushMode(FlushModeType.COMMIT).getResultList();
	}

	/**
	 * 清除会员注册项值
	 * 
	 * @param memberAttribute
	 *            会员注册项
	 */
	@Override
	public void remove(MemberAttribute memberAttribute) {
		if (memberAttribute != null && (memberAttribute.getType() == MemberAttribute.Type.text || memberAttribute.getType() == MemberAttribute.Type.select || memberAttribute.getType() == MemberAttribute.Type.checkbox)) {
			String propertyName = Member.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
			String jpql = "update Member members set members." + propertyName + " = null";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).executeUpdate();
			super.remove(memberAttribute);
		}
	}


	/**
	 * 生成四位验证码
	 */
	public String randomVcode(){
		//验证码
		String vcode=null ;
		for (int i = 0; i < 4; i++) {
			vcode = vcode + (int)(Math.random() * 9);
		}
		return vcode;
	}

	/**
	 * 手机号验证
	 *
	 *
	 * @return 验证通过返回true
	 */
	 boolean isMobile(String phone) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/"); // 验证手机号
		m = p.matcher(phone);
		b = m.matches();
		return b;
	}

}