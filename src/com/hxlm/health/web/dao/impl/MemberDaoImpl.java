/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.dao.MemberDao;
import com.hxlm.health.web.entity.*;
import com.hxlm.health.web.entity.Order;
import com.hxlm.health.web.entity.Order.OrderStatus;
import com.hxlm.health.web.entity.Order.PaymentStatus;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Dao - 会员
 * 
 * 
 * 
 */
@Repository("memberDaoImpl")
public class MemberDaoImpl extends BaseDaoImpl<Member, Long> implements MemberDao {

	private static final Pattern pattern = Pattern.compile("\\d*");

	public boolean usernameExists(String username) {
		if (username == null) {
			return false;
		}
		String jpql = "select count(*) from Member members where lower(members.username) = lower(:username)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("username", username).getSingleResult();
		return count > 0;
	}

	@Override
	public boolean nicknameExists(String nickname) {
		if (nickname == null) {
			return false;
		}
		String jpql = "select count(*) from Member members where members.nickName = :nickname";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("nickname", nickname).getSingleResult();
		return count > 0;
	}

	public boolean emailExists(String email) {
		if (email == null) {
			return false;
		}
		String jpql = "select count(*) from Member members where lower(members.email) = lower(:email)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("email", email).getSingleResult();
		return count > 0;
	}

	public Member findByUsername(String username) {
		if (username == null) {
			return null;
		}
		try {
			String jpql = "select members from Member members where lower(members.username) = lower(:username)";
			return entityManager.createQuery(jpql, Member.class).setFlushMode(FlushModeType.COMMIT).setParameter("username", username).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Member> findListByEmail(String email) {
		if (email == null) {
			return Collections.<Member> emptyList();
		}
		String jpql = "select members from Member members where lower(members.email) = lower(:email)";
		return entityManager.createQuery(jpql, Member.class).setFlushMode(FlushModeType.COMMIT).setParameter("email", email).getResultList();
	}

	public List<Object[]> findPurchaseList(Date beginDate, Date endDate, Integer count) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Member> member = criteriaQuery.from(Member.class);
		Join<Product, Order> orders = member.join("orders");
		criteriaQuery.multiselect(member.get("id"), member.get("username"), member.get("email"), member.get("point"), member.get("amount"), member.get("balance"), criteriaBuilder.sum(orders.<BigDecimal> get("amountPaid")));
		Predicate restrictions = criteriaBuilder.conjunction();
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(orders.<Date> get("createDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(orders.<Date> get("createDate"), endDate));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(orders.get("orderStatus"), OrderStatus.completed), criteriaBuilder.equal(orders.get("paymentStatus"), PaymentStatus.paid));
		criteriaQuery.where(restrictions);
		criteriaQuery.groupBy(member.get("id"), member.get("username"), member.get("email"), member.get("point"), member.get("amount"), member.get("balance"));
		criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.sum(orders.<BigDecimal> get("amountPaid"))));
		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
		if (count != null && count >= 0) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}


	//	模糊查询
	public List<Member> searth(String keyword,Integer count){

		if(StringUtils.isEmpty(keyword)){
			return Collections.<Member> emptyList();
		}
		// 创建安全查询对象
		CriteriaBuilder  criteriaBuilder=entityManager.getCriteriaBuilder();
		// 安全查询主语句
		CriteriaQuery<Member> criteriaQuery=criteriaBuilder.createQuery(Member.class);
		// Root 定义查询的From子句中能出现的类型
		Root<Member> root=criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		// Predicate 过滤条件
		Predicate restrictions=criteriaBuilder.conjunction();
		//用户名，名称查找账号
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.like(root.<String>get("username"), "%" + keyword + "%"), criteriaBuilder.like(root.<String>get("name"), "%" + keyword + "%")));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, null, null);
	}

	public void updateUA(Long id, String ua) {
		String jpql = "update Member members set members.ua = :ua,members.loginDate = :loginDate where members.id = :id";
		entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("ua", ua).setParameter("loginDate", new Date()).setParameter("id", id).executeUpdate();
	}


	// update Person o set o.name=:name where o.id=:id
	public int updateImage(Member member, String memberImage, Long memberId) {
		Assert.notNull(member);

		String jpql = "update Member member set member.memberImage=:memberImage where member.id= :id";
		entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("id",member.getId());
		return  entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("memberImage", memberImage).executeUpdate();

	}

	// 会员信息修改接口
	// 上传头像memberImage，性别gender，手机号码mobile，真实姓名name，固定电话phone，民族nation，居住地address，婚否情况（已婚，未婚）isMarried，证件类型（身份证，军官证，护照，其他）identity，证件号码idNumber  member.isMarried= :isMarried,setParameter("isMarried", isMarried).
	public void updateMember(Long id,String memberImage, Member.Gender gender,String name,String mobile,String phone,String nation,String address,Boolean isMarried,Member.IdentityType identityType,String idNumber,Boolean isMedicare,String  birthday) {
		String jpql = "update Member members set members.memberImage = :memberImage,members.gender = :gender,members.name = :name, members.mobile = :mobile,members.phone = :phone,members.nation = :nation,members.address = :address,members.isMarried = :isMarried,members.identityType = :identityType,members.idNumber = :idNumber,members.isMedicare =:isMedicare,members.birthday =:birthday  where members.id = :id";
		entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("memberImage", memberImage).setParameter("gender", gender).setParameter("name", name).setParameter("mobile", mobile).setParameter("phone", phone).setParameter("nation", nation).setParameter("address", address).setParameter("isMarried",isMarried).setParameter("identityType", identityType).setParameter("idNumber", idNumber).setParameter("isMedicare",isMedicare).setParameter("birthday",birthday).setParameter("id", id).executeUpdate();
		;
	}

	// 登录时修改设备token，deviceToken
	public void updateToken(Long id,String deviceToken){
		String jpql="update Member members set members.deviceToken = :deviceToken where members.id = :id";
		entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("deviceToken",deviceToken).setParameter("id",id).executeUpdate();
	}

	public void updateWXUserMsg(Member member) {
		String jpql="update Member members set members.username = :username,members.password = :password where members.id = :id";
		entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("username", member.getUsername()).setParameter("password",member.getPassword()).setParameter("id",member.getId()).executeUpdate();
	}

}