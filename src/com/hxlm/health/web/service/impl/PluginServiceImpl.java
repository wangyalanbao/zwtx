/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hxlm.health.web.plugin.PaymentPlugin;
import com.hxlm.health.web.plugin.RefundsPlugin;
import com.hxlm.health.web.plugin.StoragePlugin;
import com.hxlm.health.web.service.PluginService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.stereotype.Service;

/**
 * Service - 插件
 * 
 * 
 * 
 */
@Service("pluginServiceImpl")
public class PluginServiceImpl implements PluginService {

	@Resource
	private List<PaymentPlugin> paymentPlugins = new ArrayList<PaymentPlugin>();
	@Resource
	private List<StoragePlugin> storagePlugins = new ArrayList<StoragePlugin>();
	@Resource
	private List<RefundsPlugin> refundsPlugins = new ArrayList<RefundsPlugin>();
	@Resource
	private Map<String, PaymentPlugin> paymentPluginMap = new HashMap<String, PaymentPlugin>();
	@Resource
	private Map<String, StoragePlugin> storagePluginMap = new HashMap<String, StoragePlugin>();
	@Resource
	private Map<String, RefundsPlugin> refundsPluginMap = new HashMap<String, RefundsPlugin>();

	public List<PaymentPlugin> getPaymentPlugins() {
		Collections.sort(paymentPlugins);
		return paymentPlugins;
	}

	public List<StoragePlugin> getStoragePlugins() {
		Collections.sort(storagePlugins);
		return storagePlugins;
	}

	public List<PaymentPlugin> getPaymentPlugins(final boolean isEnabled) {
		List<PaymentPlugin> result = new ArrayList<PaymentPlugin>();
		CollectionUtils.select(paymentPlugins, new Predicate() {
			public boolean evaluate(Object object) {
				PaymentPlugin paymentPlugin = (PaymentPlugin) object;
				return paymentPlugin.getIsEnabled() == isEnabled;
			}
		}, result);
		Collections.sort(result);
		return result;
	}

	public List<StoragePlugin> getStoragePlugins(final boolean isEnabled) {
		List<StoragePlugin> result = new ArrayList<StoragePlugin>();
		CollectionUtils.select(storagePlugins, new Predicate() {
			public boolean evaluate(Object object) {
				StoragePlugin storagePlugin = (StoragePlugin) object;
				return storagePlugin.getIsEnabled() == isEnabled;
			}
		}, result);
		Collections.sort(result);
		return result;
	}

	public PaymentPlugin getPaymentPlugin(String id) {
		return paymentPluginMap.get(id);
	}

	public StoragePlugin getStoragePlugin(String id) {
		return storagePluginMap.get(id);
	}

	/**
	 * 获取支付插件
	 *
	 * @return 支付插件
	 */
	public List<RefundsPlugin> getRefundsPlugins(){
		Collections.sort(refundsPlugins);
		return refundsPlugins;
	}

	/**
	 * 获取支付插件
	 *
	 * @return 支付插件
	 */
	public List<RefundsPlugin> getRefundsPlugins(final boolean isEnabled){
		List<RefundsPlugin> result = new ArrayList<RefundsPlugin>();
		CollectionUtils.select(refundsPlugins, new Predicate() {
			public boolean evaluate(Object object) {
				RefundsPlugin refundsPlugin = (RefundsPlugin) object;
				return refundsPlugin.getIsEnabled() == isEnabled;
			}
		}, result);
		Collections.sort(result);
		return result;
	}

	/**
	 * 获取退款插件
	 *
	 * @param id
	 *            ID
	 * @return 退款插件
	 */
	public RefundsPlugin getRefundsPlugin(String id){
		return refundsPluginMap.get(id);
	}

}