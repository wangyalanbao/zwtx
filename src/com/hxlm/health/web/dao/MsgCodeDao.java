package com.hxlm.health.web.dao;

import com.hxlm.health.web.entity.MsgCode;

public interface MsgCodeDao extends BaseDao<MsgCode,Long> {

    //根据手机号查最新的一条验证码消息
    MsgCode findOne(String mobile);

}
