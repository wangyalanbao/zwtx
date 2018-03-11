package com.hxlm.health.web.service;

import com.hxlm.health.web.entity.MsgCode;

public interface MsgCodeService extends BaseService<MsgCode,Long> {

    //查最新的一条验证码消息
    MsgCode findOne(String mobile);
}
