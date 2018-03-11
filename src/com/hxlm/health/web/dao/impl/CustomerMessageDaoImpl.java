package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.dao.CustomerMessageDao;
import com.hxlm.health.web.entity.CustomerMessage;
import org.springframework.stereotype.Repository;

/**
 * Created by guofeng on 2015/12/24.
 * DaoImpl--发送消息
 */
@Repository("customerMessageDaoImpl")
public class CustomerMessageDaoImpl extends BaseDaoImpl<CustomerMessage,Long> implements CustomerMessageDao {
}
