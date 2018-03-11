package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.dao.CompanyDao;
import com.hxlm.health.web.entity.Company;
import com.hxlm.health.web.service.CompanyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by guofeng on 2015/12/11.
 * ServiceImpl--航空公司
 */
@Service("companyServiceImpl")
public class CompanyServiceImpl extends BaseServiceImpl<Company,Long> implements CompanyService {

    @Resource(name = "companyDaoImpl")
    private CompanyDao companyDao;
    @Resource(name = "companyDaoImpl")
    public void setBaseDao(CompanyDao companyDao) {super.setBaseDao(companyDao);}
}
