/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.controller.admin;

import com.hxlm.health.web.service.PluginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Controller - 支付插件
 * 
 * 
 * 
 */
@Controller("adminRefundsPluginController")
@RequestMapping("/admin/refunds_plugin")
public class RefundsPluginController extends BaseController {

	@Resource(name = "pluginServiceImpl")
	private PluginService pluginService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		model.addAttribute("refundsPlugins", pluginService.getRefundsPlugins());
		return "/admin/refunds_plugin/list";
	}

}