package com.hxlm.health.web.service;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.PlaneBrand;
import com.hxlm.health.web.entity.PlaneType;

/**
 * Created by guofeng on 2015/12/11.
 * service--飞机型号
 */
public interface PlaneTypeService extends BaseService<PlaneType ,Long> {

    /**
     * 飞机品牌下的飞机
     * @param planeBrand
     * @param pageable
     * @return
     */
    Page<PlaneType> findPage(PlaneBrand planeBrand, Pageable pageable);
}
