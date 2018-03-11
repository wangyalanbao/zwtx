package com.hxlm.health.web.service;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.Result;
import com.hxlm.health.web.entity.Member;
import com.hxlm.health.web.entity.MemberChild;

import java.util.List;

/**
 * Created by delll on 2015/6/25.
 * ---子账户
 */
public interface MemberChildService extends BaseService<MemberChild,Long> {
    Result getList();
    Page<MemberChild> findPage(Member member, Pageable pageable);
//    根据会员ID查找子账户
    Result list(Member member);
//  通过关键词模糊查询，关键词keyword，数量count
    List<MemberChild> search(String keyword, Integer count);
//  通过姓名查找子账户
    MemberChild findByName(String name);
//  通过手机号判断用户是否存在
    boolean mobileExists(String mobile);
    //手机号查询对应子账户信息
    Result findMobile(String mobile);
}

