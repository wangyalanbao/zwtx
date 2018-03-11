package com.hxlm.health.web.service;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.Result;
import com.hxlm.health.web.entity.SoftwareManage;
import com.hxlm.health.web.entity.VersionsUpdate;

/**
 * Created by guofeng on 2015/12/28.
 * Service--版本号
 */
public interface VersionsUpdateService extends BaseService<VersionsUpdate,Long> {

    Result updateVersion(String versionsNum,String channelTypes);
    //判断版本类型是否存在
    boolean channelTypesExists(String channelTypes);

    //软件类型查找版本类型所对应的版本号softwareManage(软件类型)，channelTypes（软件渠道），versionsNum(软件版本号)
    Result vsesion(String  sn,String channelTypes,String versionsNum);

    Page<VersionsUpdate> findPage(SoftwareManage softwareManage, Pageable pageable);
}
