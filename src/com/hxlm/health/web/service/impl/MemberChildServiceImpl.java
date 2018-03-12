package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.*;
import com.hxlm.health.web.dao.MemberChildDao;
import com.hxlm.health.web.entity.Member;
import com.hxlm.health.web.entity.MemberChild;
import com.hxlm.health.web.service.MemberChildService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by delll on 2015/6/25.
 */
@Service("memberChildServiceImpl")
public class MemberChildServiceImpl extends BaseServiceImpl<MemberChild,Long> implements MemberChildService {

    @Resource(name = "memberChildDaoImpl")
    private MemberChildDao memberChildDao;
    @Resource(name = "memberChildDaoImpl")
    public void setBaseDao(MemberChildDao memberChildDao){
        super.setBaseDao(memberChildDao);
    }

    /**
     *获得子账户列表
     * @return
     */
    public ErrorMsg getList() {
        Result result=new Result();
        result.setData(super.findAll());
        result.setCode(Status.SUCCESS);

        return result;
    }

    @Transactional(readOnly = true)
    public Page<MemberChild> findPage(Member member, Pageable pageable) {
        return memberChildDao.findPage(member, pageable);
    }

//    根据会员查找相对子账户
    public Result list(Member member) {
        Result  result=new Result();
//        判断获取到的会员是否为空，空返回一个空集合，否则返回子账户列表
        if (member == null) {
            result.setData(new ArrayList());
            result.setCode(Status.INVALID_MEMBER);
        } else {
            result.setData(memberChildDao.list(member));
            result.setCode(Status.SUCCESS);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<MemberChild> search(String keyword, Integer count) {
        return memberChildDao.searth(keyword,count);
    }

//   通过姓名查找会员
    @Transactional(readOnly = true)
    public MemberChild findByName(String name) {
        return memberChildDao.findByName(name);
    }


    @Transactional(readOnly = true)
    public boolean mobileExists(String mobile) {
        return memberChildDao.mobileExists(mobile);
    }

    //手机号查询对应子账户
    @Transactional(readOnly = true)
    public Result findMobile(String mobile){
        Result  result=new Result();
        if(mobile == null){
            result.setData("手机号不能为空");
            result.setCode(Status.INVALID_MOBILE);
        } else if (!memberChildDao.mobileExists(mobile)) {
            result.setData("该子账户不存在");
            result.setCode(Status.INVALID_MEMBER);
        } else {
            result.setCode(Status.SUCCESS);
            result.setData(memberChildDao.findMobile(mobile));
        }
        return result;
    }
}
