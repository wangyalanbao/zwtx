package com.hxlm.health.web.controller.shop;

import com.hxlm.health.web.Result;
import com.hxlm.health.web.Status;
import com.hxlm.health.web.service.VersionsUpdateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by guofeng on 2015/12/28.
 * 软件版本更新
 */
@Controller("interfaceVersionsUpdateController")
@RequestMapping("/versions_update")
public class VersionsUpdateController extends BaseController {

    @Resource(name = "versionsUpdateServiceImpl")
    private VersionsUpdateService versionsUpdateService;


    /**
     * Header头取值，用户版本更新接口
     * @param request (软件编码，渠道编码，版本号)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateVersion" , method = RequestMethod.GET)
    public Result updateVersion(HttpServletRequest request){
        Result result = new Result();
        String version = request.getHeader("version");
        if(version == null && version.isEmpty()){
            result.setData("软件编码不存在~！");
            result.setStatus(Status.INVALID_PARAMS);
            return result;
        }
        String[] strList = version.split("-");
        if(strList == null && strList.length != 3){
            result.setData("软件编码格式有误~！");
            result.setStatus(Status.INVALID_PARAMS);
            return result;
        }
        return versionsUpdateService.vsesion(strList[0], strList[1], strList[2]);
    }
}
