package com.hxlm.health.web.controller.admin;

import com.hxlm.health.web.Message;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.SoftwareManage;
import com.hxlm.health.web.service.SoftwareManageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;


/**
 * Created by guofeng on 2015/12/28.
 * Controller--软件类型
 */
@Controller("adminSoftwareManageController")
@RequestMapping("/admin/software_manage")
public class SoftwareManageController extends BaseController {

    @Resource(name = "softwareManageServiceImpl")
    private SoftwareManageService softwareManageService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Pageable pageable,ModelMap model){
        model.addAttribute("page", softwareManageService.findPage(pageable));
        return "/admin/software_manage/list";
    }
    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Message delete(Long [] ids){
        softwareManageService.delete(ids);
        return SUCCESS_MESSAGE;
    }
    /**
     * 增加
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(ModelMap model){
        return "/admin/software_manage/add";
    }
    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(SoftwareManage softwareManage,RedirectAttributes redirectAttributes){
        if(!isValid(softwareManage)){
            return ERROR_VIEW;
        }

        softwareManageService.save(softwareManage);
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:list.jhtml";
    }
    /**
     * 修改
     */
    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String edit(Long id,ModelMap model){
        model.addAttribute("software",softwareManageService.find(id));
        return "/admin/software_manage/edit";
    }
    /**
     * 更新
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(SoftwareManage softwareManage,RedirectAttributes redirectAttributes){
        if(!isValid(softwareManage)){
            return ERROR_VIEW;
        }
        softwareManageService.update(softwareManage);
        addFlashMessage(redirectAttributes,SUCCESS_MESSAGE);
        return "redirect:list.jhtml";
    }
}
