/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.hxlm.health.web.Message;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.Area;
import com.hxlm.health.web.entity.ShippingMethod;
import com.hxlm.health.web.service.AreaService;
import com.hxlm.health.web.service.DeliveryCorpService;
import com.hxlm.health.web.service.ShippingMethodService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 配送方式
 * 
 * 
 * 
 */
@Controller("adminShippingMethodController")
@RequestMapping("/admin/shipping_method")
public class ShippingMethodController extends BaseController {

	@Resource(name = "shippingMethodServiceImpl")
	private ShippingMethodService shippingMethodService;
	@Resource(name = "deliveryCorpServiceImpl")
	private DeliveryCorpService deliveryCorpService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("deliveryCorps", deliveryCorpService.findAll());
		model.addAttribute("sendArea",areaService.findByName("北京市"));
		return "/admin/shipping_method/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(ShippingMethod shippingMethod, Long defaultDeliveryCorpId,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		shippingMethod.setDefaultDeliveryCorp(deliveryCorpService.find(defaultDeliveryCorpId));
		shippingMethod.setSendArea("北京市");
		// 收货地区
		String inputArea = request.getParameter("inputArea");
		if(StringUtils.isNotEmpty(inputArea)){
			Area area = areaService.find(Long.valueOf(inputArea));
			if(area != null){
				shippingMethod.setReceiveArea(area);
			}
		}
		if (!isValid(shippingMethod)) {
			return ERROR_VIEW;
		}
//		shippingMethod.setPaymentMethods(null);
//		shippingMethod.setOrders(null);
		shippingMethodService.save(shippingMethod);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("deliveryCorps", deliveryCorpService.findAll());
		model.addAttribute("shippingMethod", shippingMethodService.find(id));
		model.addAttribute("sendArea",areaService.findByName("北京市"));
		return "/admin/shipping_method/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(ShippingMethod shippingMethod, Long defaultDeliveryCorpId,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		shippingMethod.setDefaultDeliveryCorp(deliveryCorpService.find(defaultDeliveryCorpId));
		// 收货地区

		String inputArea = request.getParameter("inputArea");
		if(StringUtils.isNotEmpty(inputArea)){
			Area area = areaService.find(Long.valueOf(inputArea));
			if(area != null){
				shippingMethod.setReceiveArea(area);
			}
		}
		if (!isValid(shippingMethod)) {
			return ERROR_VIEW;
		}
		shippingMethodService.update(shippingMethod, "paymentMethods", "orders");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", shippingMethodService.findPage(pageable));
		model.addAttribute("sendArea",areaService.findByName("北京市"));
		return "/admin/shipping_method/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		if (ids.length >= shippingMethodService.count()) {
			return Message.error("admin.common.deleteAllNotAllowed");
		}
		shippingMethodService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}