/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.Message;
import com.hxlm.health.web.Result;
import com.hxlm.health.web.Status;
import com.hxlm.health.web.dao.ReceiverDao;
import com.hxlm.health.web.dao.PushContentDao;
import com.hxlm.health.web.entity.Payment;
import com.hxlm.health.web.entity.PushContent;
import com.hxlm.health.web.service.PushContentService;
import com.sun.net.httpserver.Authenticator;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service - 角色
 * 
 * 
 * 
 */
@Service("pushContentServiceImpl")
public class PushContentServiceImpl extends BaseServiceImpl<PushContent, Long> implements PushContentService {

	@Resource(name = "pushContentDaoImpl")
	private PushContentDao pushContentDao;

	@Resource(name = "pushContentDaoImpl")
	public void setBaseDao(PushContentDao pushContentDao) {
		super.setBaseDao(pushContentDao);
	}


	@Override
	@Transactional(readOnly = true)
	public Map listPush(Long time) {
		Map map =  new HashMap();

		Date dateTime = null;
		if (time != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				dateTime = simpleDateFormat.parse(simpleDateFormat.format(time));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		map.put("msg","操作成功！");
		map.put("status",0);
		map.put("news",pushContentDao.findListDate(dateTime));
		return map;
	}
}