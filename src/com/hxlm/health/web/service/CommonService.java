package com.hxlm.health.web.service;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.Airplane;
import com.hxlm.health.web.entity.Airport;
import com.hxlm.health.web.entity.SearchParams;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhangyongrun on 2015/12/30.
 * service--共同方法
 */
public interface CommonService {

    /**
     * 航段报价
     * @param searchParamsList
     * 		     航段集合
     * @param airplane
     * 			飞机
     * @return
     */
    BigDecimal price(List<SearchParams> searchParamsList, Airplane airplane);

    /**
     * 计算两个经纬度间的距离
     * @param lng1 经度1
     * @param lat1 纬度1
     * @param lng2 经度2
     * @param lat2 纬度2
     * @return
     */
    double distance(double lng1, double lat1, double lng2, double lat2);

}
