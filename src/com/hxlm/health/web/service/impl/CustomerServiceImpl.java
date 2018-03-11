package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.dao.CustomerDao;
import com.hxlm.health.web.entity.Customer;
import com.hxlm.health.web.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by guofeng on 2015/12/23.
 * ServiceImpl--客户
 */
@Service("customerServiceImpl")
public class CustomerServiceImpl extends BaseServiceImpl<Customer,Long> implements CustomerService {


    @Resource(name = "customerDaoImpl")
    private CustomerDao customerDao;
    @Resource(name = "customerDaoImpl")
    public void setBaseDao(CustomerDao customerDao){
        super.setBaseDao(customerDao);
    }
    public Page<Customer> findPage(Boolean charted, Pageable pageable){
        return customerDao.findPage(charted,pageable);
    }

    /**
     * 根据手机号模糊查询
     *
     * @param telephone
     * @param count
     * @return
     */
    @Override
    public List<Customer> findByTelephone(String telephone, Integer count) {
        return customerDao.findByTelephone(telephone,count);
    }
}
