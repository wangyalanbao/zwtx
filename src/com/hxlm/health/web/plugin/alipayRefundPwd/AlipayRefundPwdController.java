/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.plugin.alipayRefundPwd;

import com.hxlm.health.web.Message;
import com.hxlm.health.web.controller.admin.BaseController;
import com.hxlm.health.web.entity.PluginConfig;
import com.hxlm.health.web.plugin.RefundsPlugin;
import com.hxlm.health.web.plugin.RefundsPlugin;
import com.hxlm.health.web.service.PluginConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Controller - 支付宝(即时批量退款有密)
 * 
 * 
 * 
 */
@Controller("adminAlipayRefundPwdController")
@RequestMapping("/admin/refunds_plugin/alipay_refund_pwd")
public class AlipayRefundPwdController extends BaseController {

	@Resource(name = "alipayRefundPwdPlugin")
	private AlipayRefundPwdPlugin alipayRefundPwdPlugin;
	@Resource(name = "pluginConfigServiceImpl")
	private PluginConfigService pluginConfigService;

	/**
	 * 安装
	 */
	@RequestMapping(value = "/install", method = RequestMethod.POST)
	public @ResponseBody
	Message install() {
		if (!alipayRefundPwdPlugin.getIsInstalled()) {
			PluginConfig pluginConfig = new PluginConfig();
			pluginConfig.setPluginId(alipayRefundPwdPlugin.getId());
			pluginConfig.setIsEnabled(false);
			pluginConfigService.save(pluginConfig);
		}
		return SUCCESS_MESSAGE;
	}

	/**
	 * 卸载
	 */
	@RequestMapping(value = "/uninstall", method = RequestMethod.POST)
	public @ResponseBody
	Message uninstall() {
		if (alipayRefundPwdPlugin.getIsInstalled()) {
			PluginConfig pluginConfig = alipayRefundPwdPlugin.getPluginConfig();
			pluginConfigService.delete(pluginConfig);
		}
		return SUCCESS_MESSAGE;
	}

	/**
	 * 设置
	 */
	@RequestMapping(value = "/setting", method = RequestMethod.GET)
	public String setting(ModelMap model) {
		PluginConfig pluginConfig = alipayRefundPwdPlugin.getPluginConfig();
		model.addAttribute("feeTypes", RefundsPlugin.FeeType.values());
		model.addAttribute("pluginConfig", pluginConfig);
		return "/com/hxlm/health/web/plugin/alipayRefundPwd/setting";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(String refundsName, String partner, String key, RefundsPlugin.FeeType feeType, BigDecimal fee, String logo, String description, @RequestParam(defaultValue = "false") Boolean isEnabled, Integer order, RedirectAttributes redirectAttributes) {
		PluginConfig pluginConfig = alipayRefundPwdPlugin.getPluginConfig();
		pluginConfig.setAttribute(RefundsPlugin.REFUNDS_NAME_ATTRIBUTE_NAME, refundsName);
		pluginConfig.setAttribute("partner", partner);
		pluginConfig.setAttribute("key", key);
		pluginConfig.setAttribute(RefundsPlugin.FEE_TYPE_ATTRIBUTE_NAME, feeType.toString());
		pluginConfig.setAttribute(RefundsPlugin.FEE_ATTRIBUTE_NAME, fee.toString());
		pluginConfig.setAttribute(RefundsPlugin.LOGO_ATTRIBUTE_NAME, logo);
		pluginConfig.setAttribute(RefundsPlugin.DESCRIPTION_ATTRIBUTE_NAME, description);
		pluginConfig.setIsEnabled(isEnabled);
		pluginConfig.setOrder(order);
		pluginConfigService.update(pluginConfig);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:/admin/refunds_plugin/list.jhtml";
	}

}