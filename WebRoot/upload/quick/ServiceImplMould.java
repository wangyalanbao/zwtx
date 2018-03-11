package com.hxlm.health.web.service.impl;

import javax.annotation.Resource;

import com.hxlm.health.web.entity.TestMould;
// 从这里引入包
import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.dao.TestMouldDao;
import com.hxlm.health.web.service.TestMouldService;
import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 快捷模板
 *
 * Created by testPerson on createTime.
 *
 */
@Service("testMouldServiceImpl")
public class TestMouldServiceImpl extends BaseServiceImpl<TestMould, Long> implements TestMouldService {

    @Resource(name = "testMouldDaoImpl")
    private TestMouldDao testMouldDao;

    @Resource(name = "testMouldDaoImpl")
    public void setBaseDao(TestMouldDao TestMouldDao) {
        super.setBaseDao(TestMouldDao);
    }


    // 按条件查询
}