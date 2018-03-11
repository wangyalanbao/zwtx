package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.dao.SoftwareManageDao;
import com.hxlm.health.web.entity.SoftwareManage;
import com.hxlm.health.web.service.SoftwareManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by guofeng on 2015/12/28.
 * ServiceImpl--软件类型
 */
@Service("softwareManageServiceImpl")
public class SoftwareManageServiceImpl extends BaseServiceImpl<SoftwareManage,Long> implements SoftwareManageService {

    @Resource(name = "softwareManageDaoImpl")
    private SoftwareManageDao softwareManageDao;
    @Resource(name = "softwareManageDaoImpl")
    public void setBase(SoftwareManageDao softwareManageDao){ super.setBaseDao(softwareManageDao);}

    public SoftwareManage findBySn(String sn){
        return   softwareManageDao.findBySn(sn);
    }

    //渠道编码查渠道
    public  SoftwareManage findByTypeSn(String typeSn){
        return softwareManageDao.findByTypeSn(typeSn);
    }

    //渠道编码软件名编码查软件
    public SoftwareManage findByChannelSn(String sn, String channelTypesSn){
        return softwareManageDao.findByChannelSn(sn,channelTypesSn);
    }
}
