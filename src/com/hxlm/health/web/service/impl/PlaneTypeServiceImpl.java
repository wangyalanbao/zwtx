package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.dao.PlaneTypeDao;
import com.hxlm.health.web.entity.PlaneBrand;
import com.hxlm.health.web.entity.PlaneType;
import com.hxlm.health.web.service.PlaneTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by guofeng on 2015/12/11.
 * serviceImpl--飞机型号
 */
@Service("planeTypeServiceImpl")
public class PlaneTypeServiceImpl extends BaseServiceImpl<PlaneType , Long> implements PlaneTypeService {

    @Resource(name = "planeTypeDaoImpl")
    private PlaneTypeDao planeTypeDao;
    @Resource(name = "planeTypeDaoImpl")
    public void setBaseDao(PlaneTypeDao planeTypeDao){
        super.setBaseDao(planeTypeDao);
    }

    @Transactional(readOnly = true)
    public Page<PlaneType> findPage(PlaneBrand planeBrand, Pageable pageable) {
        return planeTypeDao.findPage(planeBrand, pageable);
    }
}
