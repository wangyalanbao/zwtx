package com.hxlm.health.web.controller.admin;

import com.hxlm.health.web.FileInfo;
import com.hxlm.health.web.Message;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.SoftwareManage;
import com.hxlm.health.web.entity.VersionsUpdate;
import com.hxlm.health.web.service.FileService;
import com.hxlm.health.web.service.SoftwareManageService;
import com.hxlm.health.web.service.VersionsUpdateService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;

/**
 * Created by guofeng on 2015/12/28.
 * Controller--版本号
 */
@Controller("adminVersionsUpdateController")
@RequestMapping("/admin/versions_update")
public class VersionsUpdateController extends BaseController {


    @Resource(name = "versionsUpdateServiceImpl")
    private VersionsUpdateService versionsUpdateService;
    @Resource(name = "softwareManageServiceImpl")
    private SoftwareManageService softwareManageService;
    @Resource(name = "fileServiceImpl")
    private FileService fileService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Pageable pageable,ModelMap model,Long sId){
        SoftwareManage softwareManage = softwareManageService.find(sId);
        model.addAttribute("sId",sId);
        model.addAttribute("software",softwareManage);
        model.addAttribute("page", versionsUpdateService.findPage(softwareManage,pageable));
        return "/admin/versions_update/list";
    }
    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Message delete(Long [] ids){
        versionsUpdateService.delete(ids);
        return SUCCESS_MESSAGE;
    }
    /**
     * 增加
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(ModelMap model,Long sId){
        model.addAttribute("sId",sId);
        model.addAttribute("software", softwareManageService.find(sId));
        return "/admin/versions_update/add";
    }
    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(VersionsUpdate versionsUpdate,Long sId,RedirectAttributes redirectAttributes, HttpServletRequest request){
        versionsUpdate.setSoftwareManage(softwareManageService.find(sId));
        if(!isValid(versionsUpdate)){
            return ERROR_VIEW;
        }

        String path =  upload(request);
        versionsUpdate.setDownurl(path);
        versionsUpdateService.save(versionsUpdate);
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:list.jhtml";
    }
    /**
     * 修改
     */
    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String edit(Long id,ModelMap model,Long sId,HttpServletRequest request){
        model.addAttribute("sId",sId);
        model.addAttribute("software", softwareManageService.find(sId));
        model.addAttribute("versions", versionsUpdateService.find(id));
        return "/admin/versions_update/edit";
    }
    /**
     * 更新
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(VersionsUpdate versionsUpdate,Long sId,RedirectAttributes redirectAttributes,HttpServletRequest request){
        versionsUpdate.setSoftwareManage(softwareManageService.find(sId));

        if(!isValid(versionsUpdate)){
            return ERROR_VIEW;
        }
        String path =  upload(request);
        if(StringUtils.isNotEmpty(path) && !path.substring(path.length()-1).equals(".")){
            versionsUpdate.setDownurl(path);
        }else{
            versionsUpdate.setDownurl(versionsUpdateService.find(versionsUpdate.getId()).getDownurl());
        }
        versionsUpdateService.update(versionsUpdate);
        addFlashMessage(redirectAttributes,SUCCESS_MESSAGE);
        return "redirect:list.jhtml";
    }

    private String upload( HttpServletRequest request){
        String path = null;
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if(multipartResolver.isMultipart(request)){
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while(iter.hasNext()){
                //取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                return fileService.upload(FileInfo.FileType.file,file);
            }
        }
        return path;
    }
}
