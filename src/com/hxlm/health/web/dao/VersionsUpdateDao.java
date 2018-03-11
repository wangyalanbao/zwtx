package com.hxlm.health.web.dao;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.SoftwareManage;
import com.hxlm.health.web.entity.VersionsUpdate;

import java.util.List;

/**
 * Created by guofeng on 2015/12/28.
 * dao--版本号
 */
public interface VersionsUpdateDao extends BaseDao<VersionsUpdate,Long> {

    //类型查下载地址
    String address(String channelTypes);
    //类型查对象
    VersionsUpdate versions(String channelTypes);
    //判断版本类型是否存在
    boolean channelTypesExists(String channelTypes);
    //软件类型查找版本类型所对应的版本号softwareManage(软件类型)，channelTypes（软件渠道）
//    VersionsUpdate vsesion(SoftwareManage softwareManage,String channelTypes);

    Page<VersionsUpdate> findPage(SoftwareManage softwareManage, Pageable pageable);


    //软件编码sn与类型编码channelTypesSn查询软件
    List<VersionsUpdate> vsesionUpdate(String sn, String channelTypesSn);

}
