package com.hxlm.health.web.controller.shop;

import com.hxlm.health.web.Result;
import com.hxlm.health.web.service.PushContentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * Created by guofeng on 2017/3/6.
 */
@Controller("pushContentController")
@RequestMapping(value = "/shop/push_content")
public class PushContentController extends BaseController{

    @Resource(name = "pushContentServiceImpl")
    private PushContentService pushContentService;

    //根据前端传入的日期进行查询推送内容
    @ResponseBody
    @RequestMapping(value = "/push",method = RequestMethod.GET)
    public Map getPush(Long time){
        return pushContentService.listPush(time);
    }
}
