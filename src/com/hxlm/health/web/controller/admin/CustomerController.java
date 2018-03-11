package com.hxlm.health.web.controller.admin;

import com.hxlm.health.web.Message;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.Airport;
import com.hxlm.health.web.entity.Customer;
import com.hxlm.health.web.entity.CustomerMessage;
import com.hxlm.health.web.service.CustomerMessageService;
import com.hxlm.health.web.service.CustomerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guofeng on 2015/12/23.
 */
@Controller("adminCustomerController")
@RequestMapping("/admin/customer")
public class CustomerController extends BaseController {

    private static final Integer SEARCH_COUNT = 10;

    @Resource(name = "customerServiceImpl")
    private CustomerService customerService;
    @Resource(name = "customerMessageServiceImpl")
    private CustomerMessageService customerMessageService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Boolean charted, Pageable pageable, ModelMap model) {
        model.addAttribute("charted", charted);
        model.addAttribute("page", customerService.findPage(charted, pageable));
        return "/admin/customer/list";
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    Message delete(Long[] ids) {
        if (ids != null) {
            customerService.delete(ids);
        }
        return SUCCESS_MESSAGE;
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Long id, ModelMap model) {
        model.addAttribute("customer", customerService.find(id));
        model.addAttribute("sexs", Customer.Sex.values());
        model.addAttribute("identityTypes", Customer.IdentityType.values());
        return "/admin/customer/edit";
    }


    /**
     * 更新
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Customer customer, RedirectAttributes redirectAttributes) {

        if (!isValid(customer)) {
            return ERROR_VIEW;
        }
        customerService.update(customer);
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:list.jhtml";
    }


    /**
     * 发送
     */
    @ResponseBody
    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String save(CustomerMessage customerMessage, Long customeId) {

        customerMessage.setCustomerId(customerService.find(customeId));
        if (!isValid(customerMessage)) {
            return ERROR_VIEW;
        }
        customerMessageService.save(customerMessage);
        return "/admin/customer/list";
    }


    /**
     * 删除
     */
    @RequestMapping(value = "/search_customer",method = RequestMethod.GET)
    public @ResponseBody
    List<Customer> search_customer(String q) {
        List<Customer> customerList = customerService.findByTelephone(q, SEARCH_COUNT);
        return customerList;
    }
}
