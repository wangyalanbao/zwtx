package com.hxlm.health.web.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.number.CurrencyFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;

import javax.swing.text.NumberFormatter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by guofeng on 2017/9/5.
 */
public class TestAnnotation {

    //测试货币格式化
    /*@Test
    public void testWithDefaultFormattingConversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        //默认不自动注册任何Formatter
        CurrencyFormatter currencyFormatter = new CurrencyFormatter();
        currencyFormatter.setFractionDigits(2);//保留小数点后几位
        currencyFormatter.setRoundingMode(RoundingMode.CEILING);//舍入模式（ceilling表示四舍五入）
        //注册Formatter SPI实现
        conversionService.addFormatter(currencyFormatter);

        //绑定Locale信息到ThreadLocal
        //FormattingConversionService内部自动获取作为Locale信息，如果不设值默认是 Locale.getDefault()
        LocaleContextHolder.setLocale(Locale.US);
        System.out.println(conversionService.convert(new BigDecimal("1234.128"), String.class));
        LocaleContextHolder.setLocale(null);

        LocaleContextHolder.setLocale(Locale.CHINA);
        System.out.println(conversionService.convert(new BigDecimal("1234.128"), String.class));
        System.out.println(conversionService.convert("￥1,234.13", BigDecimal.class));
        LocaleContextHolder.setLocale(null);
    }*/

    @Test
    public void testNumberFormatting(){
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");

        System.out.println(list.toString());
    }
}
