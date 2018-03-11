package com.hxlm.health.web.controller.admin;

import com.hxlm.health.web.Message;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.Company;
import com.hxlm.health.web.entity.PlaneBrand;
import com.hxlm.health.web.service.CompanyService;
import com.hxlm.health.web.service.PlaneBrandService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * Created by guofeng on 2015/12/11.
 * controller--航空公司
 */
@Controller("adminCompanyController")
@RequestMapping("/admin/company")
public class CompanyController extends BaseController {

    @Resource(name = "companyServiceImpl")
    private CompanyService companyService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Pageable pageable,ModelMap model){
        model.addAttribute("page",companyService.findPage(pageable));
        return "/admin/company/list";
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public @ResponseBody
    Message delete(Long[] ids) {
        if (ids != null) {
            companyService.delete(ids);
        }
        return SUCCESS_MESSAGE;
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(){
        return "/admin/company/add";
    }
    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(Company company, RedirectAttributes redirectAttributes){

        if (!isValid(company)) {
            return ERROR_VIEW;
        }
        companyService.save(company);
        addFlashMessage(redirectAttributes,SUCCESS_MESSAGE);
        return "redirect:list.jhtml";

    }

    /**
     * 编辑
     */
    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String edit(Long id, ModelMap model){

        model.addAttribute("company",companyService.find(id));
        return "/admin/company/edit";
    }

    /**
     * 更新
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public String update(Company company, RedirectAttributes redirectAttributes){
        if(!isValid(company)){
            return ERROR_VIEW;
        }
        companyService.update(company);
        addFlashMessage(redirectAttributes,SUCCESS_MESSAGE);
        return "redirect:list.jhtml";
    }
}
