/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.Member;
import com.hxlm.health.web.entity.Receiver;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Dao - 收货地址
 * 
 * 
 * 
 */
public interface ReceiverDao extends BaseDao<Receiver, Long> {

	/**
	 * 查找默认收货地址
	 * 
	 * @param member
	 *            会员
	 * @return 默认收货地址，若不存在则返回最新收货地址
	 */
	Receiver findDefault(Member member);

	/**
	 * 查找收货地址分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 收货地址分页
	 */
	Page<Receiver> findPage(Member member, Pageable pageable);

	List<Receiver> findList(Member member);

	void updateIsDefault(Member member, Boolean isDefault);
}