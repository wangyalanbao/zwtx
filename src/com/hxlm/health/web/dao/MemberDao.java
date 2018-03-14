/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.Member;

import java.util.Date;
import java.util.List;

/**
 * Dao - 会员
 * 
 * 
 * 
 */
public interface MemberDao extends BaseDao<Member, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 判断昵称是否存在
	 */
	boolean nicknameExists(String nickname);

	/**
	 * 判断E-mail是否存在
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return E-mail是否存在
	 */
	boolean emailExists(String email);

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
	 * 修改图片
	 * @param member
	 * @param memberImage
	 * @param memberId
	 * @return
	 */
	int updateImage(Member member, String memberImage, Long memberId);

	//	根据ID和名称查询，keyword输入参数
	List<Member> searth(String keyword,Integer count);

	void updateUA(Long id, String ua);
	//	用户修改界面
	void updateMember(Long id,String memberImage, Member.Gender gender,String name,String mobile,String phone,String nation,String address,Boolean isMarried,Member.IdentityType identityType,String idNumber,Boolean isMedicare,String birthday);
	//	修改设备Token
	void updateToken(Long id,String deviceToken);

	//	微信用户绑定后修改用户信息
	void updateWXUserMsg(Member member);


}