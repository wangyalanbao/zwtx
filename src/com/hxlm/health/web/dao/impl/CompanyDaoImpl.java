package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.dao.CompanyDao;
import com.hxlm.health.web.entity.Company;
import org.springframework.stereotype.Repository;

/**
 * Created by guofeng on 2015/12/11.
 * daoImpl--航空公司
 */
@Repository("companyDaoImpl")
public class CompanyDaoImpl extends BaseDaoImpl<Company,Long> implements CompanyDao {
}
