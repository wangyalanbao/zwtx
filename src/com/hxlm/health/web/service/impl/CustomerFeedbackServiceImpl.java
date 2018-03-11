package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.dao.CustomerFeedbackDao;
import com.hxlm.health.web.entity.CustomerFeedback;
import com.hxlm.health.web.service.BaseService;
import com.hxlm.health.web.service.CustomerFeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by guofeng on 2015/12/25.
 * ServiceImpl--客户反馈
 */
@Service("customerFeedbackServiceImpl")
public class CustomerFeedbackServiceImpl extends BaseServiceImpl<CustomerFeedback,Long> implements CustomerFeedbackService {

    @Resource(name = "customerFeedbackDaoImpl")
    private CustomerFeedbackDao customerFeedbackDao;
    @Resource(name = "customerFeedbackDaoImpl")
    public void setBaseDao(CustomerFeedbackDao customerFeedbackDao){
        super.setBaseDao(customerFeedbackDao);
    }

}
