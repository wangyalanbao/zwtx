package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.dao.PlaneBrandDao;
import com.hxlm.health.web.entity.PlaneBrand;
import com.hxlm.health.web.service.PlaneBrandService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by guofeng on 2015/12/11.
 * ServiceImpl--飞机品牌
 */
@Service("planeBrandServiceImpl")
public class PlaneBrandServiceImpl extends BaseServiceImpl<PlaneBrand,Long> implements PlaneBrandService {

    @Resource(name = "planeBrandDaoImpl")
    private PlaneBrandDao planeBrandDao;
    @Resource(name = "planeBrandDaoImpl")
    public void setBaseDao(PlaneBrandDao planeBrandDao) {super.setBaseDao(planeBrandDao);}
}
