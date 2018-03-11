package com.hxlm.health.web.controller.admin;

import com.hxlm.health.web.Message;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.OrderPassenger;
import com.hxlm.health.web.service.CustomerService;
import com.hxlm.health.web.service.OrderPassengerService;
import com.hxlm.health.web.service.OrderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * Created by guofeng on 2016/1/12.
 */

@Controller("adminOrderPassengerController")
@RequestMapping("/admin/order_passenger")
public class OrderPassengerController extends BaseController {

    @Resource(name = "orderPassengerServiceImpl")
    private OrderPassengerService orderPassengerService;
    @Resource(name = "orderServiceImpl")
    private OrderService orderService;
    @Resource(name = "customerServiceImpl")
    private CustomerService customerService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Pageable pageable,ModelMap model){

        model.addAttribute("page",orderPassengerService.findPage(pageable));
        return "/admin/orderpassenger/list";
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(ModelMap model){
        model.addAttribute("sexs", OrderPassenger.Sex.values());
        model.addAttribute("identityTypes", OrderPassenger.IdentityTypes.values());
        return "/admin/orderpassenger/add";
    }
    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(OrderPassenger orderPassenger, RedirectAttributes redirectAttributes){

        if (!isValid(orderPassenger)) {
            return ERROR_VIEW;
        }
        orderPassengerService.save(orderPassenger);
        addFlashMessage(redirectAttributes,SUCCESS_MESSAGE);
        return "redirect:list.jhtml";

    }

    /**
     * 编辑
     */
    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String edit(Long id,ModelMap model){
        model.addAttribute("orderPassenger",orderPassengerService.find(id));
        model.addAttribute("sexs", OrderPassenger.Sex.values());
        model.addAttribute("identityTypes", OrderPassenger.IdentityTypes.values());
        return "/admin/orderpassenger/edit";
    }


    /**
     * 更新
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(OrderPassenger orderPassenger,RedirectAttributes redirectAttributes){

        if(!isValid(orderPassenger)){
            return ERROR_VIEW;
        }
        orderPassengerService.update(orderPassenger);
        addFlashMessage(redirectAttributes,SUCCESS_MESSAGE);
        return "redirect:list.jhtml";
    }


    /**
     * 删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public @ResponseBody
    Message delete(Long [] ids){
        if(ids !=null){
            orderPassengerService.delete(ids);
        }
        return SUCCESS_MESSAGE;
    }
    /**
     * 检查用户名是否存在
     */
    @RequestMapping(value = "/check_idCardNo", method = RequestMethod.GET)
    public @ResponseBody
    boolean checkUsername(String idCardNo) {
        if (StringUtils.isEmpty(idCardNo)) {
            return false;
        }
        if (orderPassengerService.idCardNoExists(idCardNo)) {
            return false;
        } else {
            return true;
        }
    }
}
