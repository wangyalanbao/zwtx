package com.hxlm.health.web.dao;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.PlaneBrand;
import com.hxlm.health.web.entity.PlaneType;

/**
 * Created by guofeng on 2015/12/11.
 * dao--飞机型号
 */
public interface PlaneTypeDao extends BaseDao<PlaneType,Long>{

    Page<PlaneType> findPage(PlaneBrand planeBrand, Pageable pageable);
}
