package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.dao.CustomerMessageDao;
import com.hxlm.health.web.entity.CustomerMessage;
import com.hxlm.health.web.service.CustomerMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by guofeng on 2015/12/24.
 * ServiceImpl--发送消息
 */
@Service("customerMessageServiceImpl")
public class CustomerMessageServiceImpl extends BaseServiceImpl<CustomerMessage,Long> implements CustomerMessageService {

    @Resource(name = "customerMessageDaoImpl")
    private CustomerMessageDao customerMessageDao;
    @Resource(name = "customerMessageDaoImpl")
    public void setBase(CustomerMessageDao customerMessageDao){
        super.setBaseDao(customerMessageDao);
    }

}
