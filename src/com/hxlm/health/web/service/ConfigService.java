package com.hxlm.health.web.service;

import com.hxlm.health.web.Status;
import com.hxlm.health.web.entity.Payment;

/**
 * 配置服务：负责将软件配置信息加载到内存中，减少频繁查询数据库的次数
 * @author Sun
 *
 */
public interface ConfigService {

	/**
	 * 加载缺省配置信息
	 * @return
	 */
	public boolean loadDefaultConfig();

	/**
	 * 查询系统缺省配置的一个软件注册码最多允许绑定手机号码数量
	 * @return
	 */
	public int getMaxMobileCounts();

	/**
	 * 查询系统缺省配置的免费闻音辨识服务次数
	 * @return
	 */
	public int getFreeCount();

	/**
	 * 查询系统缺省配置的标准锻炼时长
	 * @return
	 */
	public int getStandardExerciseDuration();

	/**
	 * 查询系统缺省配置的:相邻两行记录隔多长时间作为某次的锻炼时间
	 * @return
	 */
	public int getValidExerciseRecordDuration();

	/**
	 * 查询系统缺省配置的:按照有效锻炼次数累加的锻炼时长来兑换一次免费的闻音辨识服务
	 * @return
	 */
	public int getChangeDuration();

	/**
	 * 查询系统缺省配置的:当多长时间没有锻炼时，系统给出提醒信息。默认7天
	 * @return
	 */
	public int getExerciseNotifyDays();


	/**
	 * 查询系统缺省配置的：amr文件上传路径
	 * @return
	 */
	public String getAmrFilePath();

	/**
	 * 查询系统缺省配置的：wav文件保存路径
	 * @return
	 */
	public String getWavFilePath();

	/**
	 * 查询缺省配置：锻炼记录保存位置
	 * @return
	 */
	public String getExerciseFilePath();

	/**
	 * 查询系统缺省配置的：乐药存放位置
	 * @return
	 */
	public String getMusicFilePath();

	/**
	 * 查询定价-价格
	 * @return
	 */
	public int getPriceAmount();

	/**
	 * 查询定价 - 购买次数
	 * @return
	 */
	public int getPriceCounts();

	/**
	 * 查询最新乐药上传天数
	 * @return
	 */
	public int getMusicNewDays();

	/**
	 * 查询赠送次数
	 * @return
	 */
	public int getBonusCount();

	/**
	 * 查询缺省页面显示记录数
	 * @return
	 */
	public int getPageSize();

	/**
	 * 查询缺省配置：密码长度
	 * @return
	 */
	public int getPasswordLength();

	/**
	 * 查询缺省配置：amr转码文件路径
	 * @return
	 */
	public String getConvertFilePath();

	/**
	 * 查询缺省配置：本机对外的URL访问基础地址
	 * @return
	 */
	public String getBaseUrlAddress();

	/**
	 * 查询缺省短信后缀
	 * @return
	 */
	public String getSmsSuffix();

	/**
	 * 查询是否限制服务次数
	 * @return
	 */
	public boolean getLimitServiceCount();

	/**
	 *发送短信
	 * @param mobile
	 * 手机号
	 * @param content
	 * 发送内容
	 * @return
	 */
	Integer sendSmsYH(String mobile, String content);

	/**
	 *发送短信
	 * @param mobile
	 * 手机号
	 * @param content
	 * 发送内容
	 * @return
	 */
	Integer sendSmsGW(String mobile, String content);

}
