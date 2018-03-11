package com.hxlm.health.web.controller.admin;

import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.service.CustomerFeedbackService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by guofeng on 2015/12/25.
 */
@Controller("adminCustomerFeedbackController")
@RequestMapping("/admin/customer_feedback")
public class CustomerFeedbackController extends BaseController {

    @Resource(name = "customerFeedbackServiceImpl")
    private CustomerFeedbackService customerFeedbackService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Pageable pageable,ModelMap model){
        model.addAttribute("page",customerFeedbackService.findPage(pageable));
        return "/admin/customer_feedback/list";
    }

}
