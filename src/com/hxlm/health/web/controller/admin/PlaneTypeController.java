package com.hxlm.health.web.controller.admin;

import com.hxlm.health.web.Message;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.PlaneBrand;
import com.hxlm.health.web.entity.PlaneType;
import com.hxlm.health.web.service.PlaneBrandService;
import com.hxlm.health.web.service.PlaneTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * Created by guofeng on 2015/12/11.
 * Controller--飞机型号
 */
@Controller("adminPlaneTypeController")
@RequestMapping("/admin/plane_type")
public class PlaneTypeController extends BaseController {

    @Resource(name = "planeTypeServiceImpl")
    private PlaneTypeService planeTypeService;
    @Resource(name = "planeBrandServiceImpl")
    private PlaneBrandService planeBrandService;


    /**
     * 列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Long planeBrandId, Pageable pageable,ModelMap model){
        PlaneBrand planeBrand = planeBrandService.find(planeBrandId);
        model.addAttribute("planeBrandId",planeBrandId);
        model.addAttribute("planeBrand",planeBrand);
        model.addAttribute("page",planeTypeService.findPage(planeBrand,pageable));
        return "/admin/plane_type/list";
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    Message delete(Long[] ids) {
        planeTypeService.delete(ids);
        return SUCCESS_MESSAGE;
    }


    /**
     * 添加
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Long planeBrandId,ModelMap model) {
        model.addAttribute("planeBrand", planeBrandService.find(planeBrandId));
        model.addAttribute("planeBrandId", planeBrandId);
        return "/admin/plane_type/add";
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(PlaneType planeType,Long planeBrandId,RedirectAttributes redirectAttributes){

        planeType.setBrandId(planeBrandService.find(planeBrandId));
        if(!isValid(planeType)){
            return  ERROR_VIEW;
        }
        planeTypeService.save(planeType);
        addFlashMessage(redirectAttributes,SUCCESS_MESSAGE);
        return "redirect:list.jhtml";
    }

    /**
     * 编辑
     *
     */
    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String edit(Long id,Long planeBrandId,ModelMap model){
        model.addAttribute("planeBrandId",planeBrandId);
        model.addAttribute("id",id);
        model.addAttribute("planeBrand",planeBrandService.find(planeBrandId));
        model.addAttribute("planeType", planeTypeService.find(id));
        return  "/admin/plane_type/edit";
    }
    /**
     * 更新
     *
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(PlaneType planeType,Long planeBrandId,RedirectAttributes redirectAttributes){
        planeType.setBrandId(planeBrandService.find(planeBrandId));
        if(!isValid(planeType)){
            return ERROR_VIEW;
        }

        planeTypeService.update(planeType);
        addFlashMessage(redirectAttributes,SUCCESS_MESSAGE);
        return  "redirect:list.jhtml";
    }

}
