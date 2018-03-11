package com.hxlm.health.web.dao;

import com.hxlm.health.web.entity.SoftwareManage;

/**
 * Created by guofeng on 2015/12/28.
 * Dao--软件类型
 */
public interface SoftwareManageDao extends BaseDao<SoftwareManage,Long> {

    //编码查软件
    SoftwareManage findBySn(String sn);
    //渠道编码查软件
    SoftwareManage findByTypeSn(String typeSn);
    //渠道编码软件名编码查软件
    SoftwareManage findByChannelSn(String sn, String channelTypesSn);
}
