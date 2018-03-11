package com.hxlm.health.web.service.impl;


import com.hxlm.health.web.dao.ResourcesDao;
import com.hxlm.health.web.entity.Resources;
import com.hxlm.health.web.service.ResourcesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by dengyang on 15/6/23.
 */

@Service("resourcesServiceImpl")
public class ResourcesServiceImpl extends BaseServiceImpl<Resources, Long> implements ResourcesService {

    @Resource(name = "resourcesDaoImpl")
    private ResourcesDao resourcesDao;

    @Resource(name = "resourcesDaoImpl")
    public void setBaseDao(ResourcesDao resourcesDao) {super.setBaseDao(resourcesDao);}

}
