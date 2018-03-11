package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.dao.MsgCodeDao;
import com.hxlm.health.web.entity.MsgCode;
import com.hxlm.health.web.service.MsgCodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("msgCodeServiceImpl")
public class MsgCodeServiceImpl extends BaseServiceImpl<MsgCode,Long> implements MsgCodeService {

    @Resource(name = "msgCodeDaoImpl")
    private MsgCodeDao msgCodeDao;

    @Resource(name = "msgCodeDaoImpl")
    public void setBaseDao(MsgCodeDao msgCodeDao) {
        super.setBaseDao(msgCodeDao);
    }

    @Transactional(readOnly = true)
    public MsgCode findOne(String mobile) {
        return msgCodeDao.findOne(mobile);
    }
}
