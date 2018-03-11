package com.hxlm.health.web.dao.impl;


import com.hxlm.health.web.dao.ResourcesWarehouseDao;
import com.hxlm.health.web.entity.ResourcesWarehouse;
import org.hibernate.annotations.FlushModeType;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

/**
 * Created by guofeng on 2016/2/17.
 */
@Repository("resourcesWarehouseDaoImpl")
public class ResourcesWarehouseDaoImpl extends BaseDaoImpl<ResourcesWarehouse,Long> implements ResourcesWarehouseDao {


}
