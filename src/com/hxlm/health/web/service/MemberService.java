/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service;

import com.hxlm.health.web.ErrorMsg;
import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.Result;
import com.hxlm.health.web.entity.Admin;
import com.hxlm.health.web.entity.Member;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Service - 会员
 * 
 * 
 * 
 */
public interface MemberService extends BaseService<Member, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 判断用户名是否禁用
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否禁用
	 */
	boolean usernameDisabled(String username);

	/**
	 * 判断E-mail是否存在
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return E-mail是否存在
	 */
	boolean emailExists(String email);

	/**
	 * 判断E-mail是否唯一
	 * 
	 * @param previousEmail
	 *            修改前E-mail(忽略大小写)
	 * @param currentEmail
	 *            当前E-mail(忽略大小写)
	 * @return E-mail是否唯一
	 */
	boolean emailUnique(String previousEmail, String currentEmail);

	/**
	 * 保存会员
	 * 
	 * @param member
	 *            会员
	 * @param operator
	 *            操作员
	 */
	void save(Member member, Admin operator);

	Member saveMember(Member member, Admin operator);
	Member saveMembers(Member member);

	/**
	 * 更新会员
	 * 
	 * @param member
	 *            会员
	 * @param modifyPoint
	 *            修改积分
	 * @param modifyBalance
	 *            修改余额
	 * @param depositMemo
	 *            修改余额备注
	 * @param operator
	 *            操作员
	 */
	void update(Member member, Integer modifyPoint, BigDecimal modifyBalance, String depositMemo, Admin operator);

	/**
	 * 根据用户名查找会员
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	Member findByUsername(String username);

	/**
	 * 根据E-mail查找会员
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	List<Member> findListByEmail(String email);

	/**
	 * 查找会员消费信息
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @param count
	 *            数量
	 * @return 会员消费信息
	 */
	List<Object[]> findPurchaseList(Date beginDate, Date endDate, Integer count);

	/**
	 * 判断会员是否登录
	 * 
	 * @return 会员是否登录
	 */
	boolean isAuthenticated();

	/**
	 * 获取当前登录会员
	 * 
	 * @return 当前登录会员，若不存在则返回null
	 */
	Member getCurrent();

	/**
	 * 获取当前登录用户名
	 * 
	 * @return 当前登录用户名，若不存在则返回null
	 */
	String getCurrentUsername();


	/**
	 * 会员列表
	 */

	ErrorMsg getList();

//	重写save方法，返回MemberID
	Long saveMember(Member member);


	//  通过关键词模糊查询id和姓名，关键词keyword，数量count
	List<Member> search(String keyword, Integer count);

	// 更新ua信息
	void updateUA(Long id, String ua);

	/**
	 *用户修改信息
	 * @param id
	 *头像
	 * @param memberImage
		性别
	 * @param gender
	 * 姓名
	 * @param name
	 * 手机
	 * @param mobile
	 * 座机
	 * @param phone
	 * 民族
	 * @param nation
	 * 地址
	 * @param address
	 * 婚否
	 * @param isMarried
	 * 证件类型
	 * @param identityType
	 * 证件号
	 * @param idNumber
	 */
	void updateMeber(Long id,String memberImage, Member.Gender gender,String name,String mobile,String phone,String nation,String address,Boolean isMarried,Member.IdentityType identityType,String idNumber,Boolean isMedicare,String birthday);

	/**
	 *修改设备Token
	 * @param id
	 * memberid
	 * @param deviceToken
	 * 设备token
	 */
	void updateToken(Long id,String deviceToken);

	void updateWXUserMsg(Member member);


	}

