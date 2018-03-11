package com.hxlm.health.web.service;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.Customer;

import java.util.List;

/**
 * Created by guofeng on 2015/12/23.
 * Service -- 客户
 */
public interface CustomerService extends BaseService<Customer,Long> {

    Page<Customer> findPage(Boolean charted, Pageable pageable);
    /**
     * 根据手机号模糊查询
     * @param telephone
     * @param count
     * @return
     */
    List<Customer> findByTelephone(String telephone, Integer count);
}
