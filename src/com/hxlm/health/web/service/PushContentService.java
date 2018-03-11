/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service;

import com.hxlm.health.web.Result;
import com.hxlm.health.web.entity.PushContent;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * Service - 推送内容
 * 
 * 
 * 
 */
public interface PushContentService extends BaseService<PushContent, Long> {


    //跟据前端传入的时间查询推送内容
    public Map listPush(Long time);
}