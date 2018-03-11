package com.hxlm.health.web.controller.admin;

import com.hxlm.health.web.Message;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.BaseEntity;
import com.hxlm.health.web.entity.PlaneBrand;
import com.hxlm.health.web.service.PlaneBrandService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by guofeng on 2015/12/11.
 * controller--飞机品牌
 */
@Controller("adminPlaneBrandController")
@RequestMapping("/admin/plane_brand")
public class PlaneBrandController extends BaseController{

    @Resource(name = "planeBrandServiceImpl")
    private PlaneBrandService planeBrandService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Pageable pageable,ModelMap model){
        model.addAttribute("page",planeBrandService.findPage(pageable));
        return "/admin/plane_brand/list";
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public @ResponseBody
    Message delete(Long[] ids) {
        if (ids != null) {
            for (Long id : ids) {
                PlaneBrand planeBrand = planeBrandService.find(id);
                if (planeBrand.getPlaneTypes()!=null && planeBrand.getPlaneTypes().size()<0) {
                    return Message.error("对不起，该品牌下有所属飞机");
                }
            }
        planeBrandService.delete(ids);
        }
        return SUCCESS_MESSAGE;
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(){
        return "/admin/plane_brand/add";
    }
    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(PlaneBrand planeBrand, RedirectAttributes redirectAttributes){

        if (!isValid(planeBrand)) {
            return ERROR_VIEW;
        }
        planeBrandService.save(planeBrand);
        addFlashMessage(redirectAttributes,SUCCESS_MESSAGE);
        return "redirect:list.jhtml";

    }

    /**
     * 编辑
     */
    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String edit(Long id, ModelMap model){

        model.addAttribute("planeBrand",planeBrandService.find(id));
        return "/admin/plane_brand/edit";
    }

    /**
     * 更新
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public String update(PlaneBrand planeBrand, RedirectAttributes redirectAttributes){
        if(!isValid(planeBrand)){
            return ERROR_VIEW;
        }
        planeBrandService.update(planeBrand);
        addFlashMessage(redirectAttributes,SUCCESS_MESSAGE);
        return "redirect:list.jhtml";
    }

}
