package com.hxlm.health.web.service;

import com.hxlm.health.web.entity.SoftwareManage;

/**
 * Created by guofeng on 2015/12/28.
 *  Service--软件类型
 */
public interface SoftwareManageService extends BaseService<SoftwareManage,Long> {

    //软件编码查询软件
    SoftwareManage findBySn(String sn);

    //渠道编码查软件
    SoftwareManage findByTypeSn(String typeSn);

    /**
     * 渠道编码软件名编码查软件
     * @param sn 名称編碼
     * @param channelTypesSn 渠道编码
     * @return
     */
    SoftwareManage findByChannelSn(String sn, String channelTypesSn);
}
