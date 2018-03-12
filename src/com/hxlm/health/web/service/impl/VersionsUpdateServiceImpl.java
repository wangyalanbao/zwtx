package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.*;
import com.hxlm.health.web.dao.VersionsUpdateDao;
import com.hxlm.health.web.entity.SoftwareManage;
import com.hxlm.health.web.entity.VersionsUpdate;
import com.hxlm.health.web.service.VersionsUpdateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guofeng on 2015/12/28.
 * ServiceImpl--版本号
 */
@Service("versionsUpdateServiceImpl")
public class VersionsUpdateServiceImpl extends BaseServiceImpl<VersionsUpdate,Long> implements VersionsUpdateService{

    @Resource(name = "versionsUpdateDaoImpl")
    private VersionsUpdateDao versionsUpdateDao;
    @Resource(name = "versionsUpdateDaoImpl")
    public void setBaseDao(VersionsUpdateDao versionsUpdateDao){
        super.setBaseDao(versionsUpdateDao);
    }

    //判断版本类型是否存在
    @Transactional(readOnly = true)
    public boolean channelTypesExists(String channelTypes){
        return  versionsUpdateDao.channelTypesExists(channelTypes);
    }

    /*
    根据前台传入版本信息判断是否更新
   */
    public ErrorMsg updateVersion(String versionsNum,String channelTypes){
        Result result=new Result();
        Map map=new HashMap();
        //如果类型为apple则为苹果手机，判断与苹果系统版本是否相同
        VersionsUpdate versionsUpdate=versionsUpdateDao.versions(channelTypes);
        if(versionsNum == versionsUpdate.getVersionsNum()|| versionsNum.equals(versionsUpdate.getVersionsNum())){
            map.put("isUpdate",true);
//                map.put("downurl", VersionsUpdate.APPLEVERSIONSAddress);
            map.put("downurl",versionsUpdateDao.address(channelTypes));
            result.setData(map);
            result.setCode(Status.SUCCESS);
        }else {
            map.put("isUpdate",false);
            map.put("downurl",versionsUpdateDao.address(channelTypes));
            result.setData(map);
            result.setCode(Status.SUCCESS);
        }
        return result;
    }

    //软件类型查找版本类型所对应的版本号softwareManage(软件类型)，channelTypes（软件渠道），versionsNum(软件版本号)
    public Result vsesion(String softwareManageSn,String channelTypesSn,String versionsNum){
        Result result=new Result();
        Map map=new HashMap();
        //通过软件名编码和软件渠道编码查询对应的软件列表，列表以最新时间排序，取出第一个，与传来的版本号比较
        //如果相同返回false，不用更新，如果不相同则返回true
        List<VersionsUpdate> versionsUpdates=versionsUpdateDao.vsesionUpdate(softwareManageSn, channelTypesSn);
        //判断软件所对应版本是否为空，为空返回true；前台传来版本号不存在，不需要更新
        if(versionsUpdates.size() <=0){
            map.put("isUpdate", false);
            map.put("downurl", null);
            result.setData(map);
            result.setCode(Status.SUCCESS);
        }else {//版本不为空则用版本号做对比，与传来的版本号比较如果相同返回false，不用更新;如果不相同则返回true
            VersionsUpdate versions = versionsUpdates.get(0);
            if(versions.getVersionsNum() == versionsNum || versions.getVersionsNum().equals(versionsNum)){
                map.put("isUpdate", false);
                map.put("downurl", versions.getDownurl());
                result.setData(map);
                result.setCode(Status.SUCCESS);
                return result;

            }else {
                map.put("isUpdate",true);
                map.put("downurl",versions.getDownurl());
                result.setData(map);
                result.setCode(Status.SUCCESS);
                return result;
            }
        }
        return result;
    }

    public Page<VersionsUpdate> findPage(SoftwareManage softwareManage, Pageable pageable){
        return versionsUpdateDao.findPage(softwareManage,pageable);
    }
}
