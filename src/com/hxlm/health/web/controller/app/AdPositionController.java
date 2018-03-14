package com.hxlm.health.web.controller.app;

import com.hxlm.health.web.ErrorMsg;
import com.hxlm.health.web.Result;
import com.hxlm.health.web.Status;
import com.hxlm.health.web.entity.AdPosition;
import com.hxlm.health.web.service.AdPositionService;
import com.hxlm.health.web.service.AdService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/3/13.
 */
@Controller("adPositionController")
@RequestMapping("/ad")
public class AdPositionController extends BaseController{

    @Resource(name = "adServiceImpl")
    private AdService adService;
    @Resource(name = "adPositionServiceImpl")
    private AdPositionService adPositionService;


    /**
     * 提供广告位接口（包括轮播图）
     * @param id
     * 广告位名称
     * @return
     */
    @RequestMapping(value = "/shuffling_figure", method = RequestMethod.GET)
    @ResponseBody
    public ErrorMsg shufflingFigure(Long id){

        ErrorMsg errorMsg = new ErrorMsg();
        Result result = new Result();

        AdPosition adPosition = adPositionService.find(id, "首页轮播图");

        if (adPosition == null){
            errorMsg.setMessage("必填参数不能为空");
            errorMsg.setCode(Status.INVALID_PARAMS);
            return errorMsg;
        }

        result.setData(adPosition.getAds());
        result.setMessage("sucsess");
        result.setCode(Status.SUCCESS);

        return result;
    }
}
