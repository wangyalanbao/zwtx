package com.hxlm.health.web;

import org.apache.commons.lang.time.DateUtils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期类型转换
 * 
 * 
 * 
 */
public class DateEditor extends PropertyEditorSupport {

	/** 默认日期格式 */
	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String C_DATE_PATTON_DEFAULT = "yyyy-MM-dd";

	public static final String C_DATA_PATTON_MM_DD = "MM-dd";



	/** 是否将空转换为null */
	private boolean emptyAsNull;

	/** 日期格式 */
	private String dateFormat = DEFAULT_DATE_FORMAT;

	/**
	 * @param emptyAsNull
	 *            是否将空转换为null
	 */
	public DateEditor(boolean emptyAsNull) {
		this.emptyAsNull = emptyAsNull;
	}

	/**
	 * @param emptyAsNull
	 *            是否将空转换为null
	 * @param dateFormat
	 *            日期格式
	 */
	public DateEditor(boolean emptyAsNull, String dateFormat) {
		this.emptyAsNull = emptyAsNull;
		this.dateFormat = dateFormat;
	}

	/**
	 * 获取日期
	 * 
	 * @return 日期
	 */
	@Override
	public String getAsText() {
		Date value = (Date) getValue();
		return value != null ? new SimpleDateFormat(dateFormat).format(value) : "";
	}

	/**
	 * 设置日期
	 * 
	 * @param text
	 *            字符串
	 */
	@Override
	public void setAsText(String text) {
		if (text == null) {
			setValue(null);
		} else {
			String value = text.trim();
			if (emptyAsNull && "".equals(value)) {
				setValue(null);
			} else {
				try {
					setValue(DateUtils.parseDate(value, CommonAttributes.DATE_PATTERNS));
				} catch (ParseException e) {
					setValue(null);
				}
			}
		}
	}


	/**
	 * 将Date类型的日期转换为系统参数定义的格式的字符串。
	 *
	 * @param aTs_Datetime
	 * @param as_Pattern
	 * @return
	 */
	public static String format(Date aTs_Datetime, String as_Pattern) {
		if (aTs_Datetime == null || as_Pattern == null)
			return null;

		SimpleDateFormat dateFromat = new SimpleDateFormat();
		dateFromat.applyPattern(as_Pattern);

		return dateFromat.format(aTs_Datetime);
	}

	/**
	 * 将Timestamp类型的日期转换为系统参数定义的格式的字符串。
	 *
	 * @param aTs_Datetime
	 *            需要转换的日期。
	 * @return 转换后符合给定格式的日期字符串
	 */
	public static String format(Date aTs_Datetime) {
		return format(aTs_Datetime, C_DATE_PATTON_DEFAULT);
	}

	/**
	 * 将Timestamp类型的日期转换为系统参数定义的格式的字符串。
	 *
	 * @param aTs_Datetime
	 *            需要转换的日期。
	 * @return 转换后符合给定格式的日期字符串 C_DATA_PATTON_MM_DD
	 */
	public static String formatMMDD(Date aTs_Datetime) {
		return format(aTs_Datetime, C_DATA_PATTON_MM_DD);
	}

	/**
	 * 计算两个日期之间相差的天数
	 * @param smdate 较小的时间
	 * @param bdate  较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static Integer daysBetween(Date smdate,Date bdate)
	{
		SimpleDateFormat sdf=new SimpleDateFormat(C_DATE_PATTON_DEFAULT);
		try {
			smdate=sdf.parse(sdf.format(smdate));
			bdate=sdf.parse(sdf.format(bdate));
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days=(time2-time1)/(1000*3600*24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	public static Date formatDate(Date aTs_Datetime, String as_Pattern){
		SimpleDateFormat sdf=new SimpleDateFormat(as_Pattern);
		try {
			 aTs_Datetime=sdf.parse(sdf.format(aTs_Datetime));
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			return aTs_Datetime;
		}
	}
}