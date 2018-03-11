/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service;

import com.hxlm.health.web.entity.Quote;

/**
 * Service - 报价
 * 
 * 
 * 
 */
public interface QuoteService extends BaseService<Quote, Long> {

    Quote price(Quote quote);
}