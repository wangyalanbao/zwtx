package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.dao.MemberChildDao;
import com.hxlm.health.web.entity.Member;
import com.hxlm.health.web.entity.MemberChild;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by delll on 2015/6/25.
 */
@Repository("memberChildDaoImpl")
public class MemberChildDaoImpl extends BaseDaoImpl<MemberChild,Long> implements MemberChildDao {

    private static final Pattern pattern = Pattern.compile("\\d*");

   public Page<MemberChild> findPage(Member member, Pageable pageable) {
        // 创建安全查询，CriteriaBuilder为安全查询创建工厂
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        // 创建安全查询的对象
        CriteriaQuery<MemberChild> criteriaQuery = criteriaBuilder.createQuery(MemberChild.class);
        // Root 定义查询的From子句中能出现的类型，它与SQL查询中的FROM子句类似
        Root<MemberChild> root = criteriaQuery.from(MemberChild.class);
        criteriaQuery.select(root);
        // Predicate 过滤条件
        Predicate restrictions = criteriaBuilder.conjunction();

        if (member != null) {
            // 构建表查询条件
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
        }

        criteriaQuery.where(restrictions);

        return super.findPage(criteriaQuery, pageable);
    }
//    查询每个会员id所对应的子账户
    public List<MemberChild> list(Member member){
        String jpql="select MemberChild from MemberChild memberChild where memberChild.member= :member";
        return entityManager.createQuery(jpql,MemberChild.class).setFlushMode(FlushModeType.COMMIT).setParameter("member",member).getResultList();
    }



    public List<MemberChild> searth(String keyword,Integer count){

        if(StringUtils.isEmpty(keyword)){
            return Collections.<MemberChild> emptyList();
        }
        // 创建安全查询对象
        CriteriaBuilder  criteriaBuilder=entityManager.getCriteriaBuilder();
        //   安全查询主语句
        CriteriaQuery<MemberChild> criteriaQuery=criteriaBuilder.createQuery(MemberChild.class);
//        Root 定义查询的From子句中能出现的类型
        Root<MemberChild> root=criteriaQuery.from(MemberChild.class);
        criteriaQuery.select(root);
//        Predicate 过滤条件
        Predicate restrictions=criteriaBuilder.conjunction();

        if(pattern.matcher(keyword).matches()){//通过ID。和姓名查找子账户
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.get("id"), Long.valueOf(keyword)), criteriaBuilder.like(root.<String> get("name"), "%" + keyword + "%")));
        }else {//通过名字查询
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.like(root.<String>get("name"), "%" + keyword + "%")));
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery, null, count, null, null);
    }


//    通过姓名查找子账户
    public MemberChild findByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        try {
            String jpql = "select memberChild from MemberChild memberChild where lower(memberChild.name) = lower(:name)";
            return entityManager.createQuery(jpql, MemberChild.class).setFlushMode(FlushModeType.COMMIT).setParameter("name",name).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    //判断手机号是否已注册
    public boolean mobileExists(String mobile){

        if(StringUtils.isEmpty(mobile)){
            return false;
        }
        String jpql="select count(*) from MemberChild memberChild where memberChild.mobile= :mobile";
        Long count=entityManager.createQuery(jpql,Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("mobile",mobile).getSingleResult();
        return count>0;
    }

    //根据手机号查询相对的会员子账户
    public MemberChild findMobile(String mobile){
        if(StringUtils.isEmpty(mobile)){
            return null;
        }try {
            String jpql="select MemberChild from MemberChild memberChild where lower(memberChild.mobile) = lower(:mobile)";
            return entityManager.createQuery(jpql,MemberChild.class).setFlushMode(FlushModeType.COMMIT).setParameter("mobile",mobile).getSingleResult();
        }catch (Exception e){
            return  null;
        }
    }

}
