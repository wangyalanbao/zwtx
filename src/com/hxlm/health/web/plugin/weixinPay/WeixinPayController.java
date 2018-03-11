package com.hxlm.health.web.plugin.weixinPay;

import com.hxlm.health.web.Message;
import com.hxlm.health.web.controller.admin.BaseController;
import com.hxlm.health.web.entity.PluginConfig;
import com.hxlm.health.web.plugin.PaymentPlugin;
import com.hxlm.health.web.plugin.alipayDirect.AlipayDirectPlugin;
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
 * Created by dengyang on 15/7/24.
 * app内支付使用微信支付
 * 和畅依pad版客户端使用
 */

@Controller("adminWeixinPayController")
@RequestMapping("/admin/payment_plugin/weixin_pay")
public class WeixinPayController extends BaseController {

    @Resource(name = "weixinPayPlugin")
    private WeixinPayPlugin weixinPayPlugin;
    @Resource(name = "pluginConfigServiceImpl")
    private PluginConfigService pluginConfigService;

    /**
     * 安装
     */
    @RequestMapping(value = "/install", method = RequestMethod.POST)
    public @ResponseBody
    Message install() {
        if (!weixinPayPlugin.getIsInstalled()) {
            PluginConfig pluginConfig = new PluginConfig();
            pluginConfig.setPluginId(weixinPayPlugin.getId());
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
        if (weixinPayPlugin.getIsInstalled()) {
            PluginConfig pluginConfig = weixinPayPlugin.getPluginConfig();
            pluginConfigService.delete(pluginConfig);
        }
        return SUCCESS_MESSAGE;
    }

    /**
     * 设置
     */
    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public String setting(ModelMap model) {
        PluginConfig pluginConfig = weixinPayPlugin.getPluginConfig();
        model.addAttribute("feeTypes", PaymentPlugin.FeeType.values());
        model.addAttribute("pluginConfig", pluginConfig);
        return "/com/hxlm/health/web/plugin/weixinPay/setting";
    }

    /**
     * 更新
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(String paymentName, String appid, String appsecret, String apikey, String mchid, String encodingaeskey, PaymentPlugin.FeeType feeType, BigDecimal fee, String logo, String description, @RequestParam(defaultValue = "false") Boolean isEnabled, Integer order, RedirectAttributes redirectAttributes) {
        PluginConfig pluginConfig = weixinPayPlugin.getPluginConfig();
        pluginConfig.setAttribute(PaymentPlugin.PAYMENT_NAME_ATTRIBUTE_NAME, paymentName);
        pluginConfig.setAttribute("appid", appid);
        pluginConfig.setAttribute("appsecret", appsecret);
        pluginConfig.setAttribute("apikey", apikey);
        pluginConfig.setAttribute("mchid", mchid);
        pluginConfig.setAttribute(PaymentPlugin.FEE_TYPE_ATTRIBUTE_NAME, feeType.toString());
        pluginConfig.setAttribute(PaymentPlugin.FEE_ATTRIBUTE_NAME, fee.toString());
        pluginConfig.setAttribute(PaymentPlugin.LOGO_ATTRIBUTE_NAME, logo);
        pluginConfig.setAttribute(PaymentPlugin.DESCRIPTION_ATTRIBUTE_NAME, description);
        pluginConfig.setIsEnabled(isEnabled);
        pluginConfig.setOrder(order);
        pluginConfigService.update(pluginConfig);
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:/admin/payment_plugin/list.jhtml";
    }

}
