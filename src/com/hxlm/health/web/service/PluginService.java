/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service;

import java.util.List;

import com.hxlm.health.web.plugin.PaymentPlugin;
import com.hxlm.health.web.plugin.RefundsPlugin;
import com.hxlm.health.web.plugin.StoragePlugin;

/**
 * Service - 插件
 * 
 * 
 * 
 */
public interface PluginService {

	/**
	 * 获取支付插件
	 * 
	 * @return 支付插件
	 */
	List<PaymentPlugin> getPaymentPlugins();

	/**
	 * 获取存储插件
	 * 
	 * @return 存储插件
	 */
	List<StoragePlugin> getStoragePlugins();

	/**
	 * 获取支付插件
	 * 
	 * @param isEnabled
	 *            是否启用
	 * @return 支付插件
	 */
	List<PaymentPlugin> getPaymentPlugins(boolean isEnabled);

	/**
	 * 获取存储插件
	 * 
	 * @param isEnabled
	 *            是否启用
	 * @return 存储插件
	 */
	List<StoragePlugin> getStoragePlugins(boolean isEnabled);

	/**
	 * 获取支付插件
	 * 
	 * @param id
	 *            ID
	 * @return 支付插件
	 */
	PaymentPlugin getPaymentPlugin(String id);

	/**
	 * 获取存储插件
	 * 
	 * @param id
	 *            ID
	 * @return 存储插件
	 */
	StoragePlugin getStoragePlugin(String id);

	/**
	 * 获取支付插件
	 *
	 * @return 支付插件
	 */
	List<RefundsPlugin> getRefundsPlugins();

	/**
	 * 获取支付插件
	 *
	 * @return 支付插件
	 */
	List<RefundsPlugin> getRefundsPlugins(boolean isEnabled);

	/**
	 * 获取退款插件
	 *
	 * @param id
	 *            ID
	 * @return 退款插件
	 */
	RefundsPlugin getRefundsPlugin(String id);

}