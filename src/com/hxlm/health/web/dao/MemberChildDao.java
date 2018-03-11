package com.hxlm.health.web.dao;


import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.Member;
import com.hxlm.health.web.entity.MemberChild;

import java.util.List;

/**
 * Created by delll on 2015/6/25.
 * dao--子账户
 */
public interface MemberChildDao extends BaseDao<MemberChild,Long> {
//    分页查询
    Page<MemberChild> findPage(Member member, Pageable pageable);
//    根据会员id查相对的子账户
    List<MemberChild> list(Member member);
//  通过id姓名关键字查询子账户
    List<MemberChild> searth(String keyword,Integer count);
//  通过姓名查找子账户
    MemberChild findByName(String name);
//  根据手机号判断用户是否存在
    boolean mobileExists(String mobile);
    //用手机号查询对应子账户
    MemberChild findMobile(String mobile);
}
