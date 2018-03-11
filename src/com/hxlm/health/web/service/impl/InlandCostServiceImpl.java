package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.dao.InlandCostDao;
import com.hxlm.health.web.entity.InlandCost;
import com.hxlm.health.web.service.InlandCostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by guofeng on 2016/1/5.
 */
@Service("inlandCostServiceImpl")
public class InlandCostServiceImpl extends BaseServiceImpl<InlandCost,Long> implements InlandCostService {

    @Resource(name = "inlandCostDaoImpl")
    private InlandCostDao inlandCostDao;
    @Resource(name = "inlandCostDaoImpl")
    public void setBaseDao(InlandCostDao inlandCostDao){
        super.setBaseDao(inlandCostDao);
    }
}
