package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.dao.CustomerFeedbackDao;
import com.hxlm.health.web.entity.CustomerFeedback;
import org.springframework.stereotype.Repository;

/**
 * Created by guofeng on 2015/12/25.
 * DaoImpl--客户反馈
 */
@Repository("customerFeedbackDaoImpl")
public class CustomerFeedbackDaoImpl extends BaseDaoImpl<CustomerFeedback,Long> implements CustomerFeedbackDao {
}
