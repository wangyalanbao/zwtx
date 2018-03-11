package com.hxlm.health.web;

/**
 * 配置信息常量值
 * 
 * @author Sun
 *
 */
public class ConfigConstants {

	/**
	 * 软件注册码文件保存路径
	 */
	public static final String REGCODE_FILE_PATH = "regcodeFilePath";
	
	/**
	 * 免费闻音辨识服务次数
	 */
	public static final String FREE_COUNT = "freeCount";
	
	/**
	 * 一个软件注册码支持的手机号码数
	 */
	public static final String MAX_REGCODE_MOBILE_COUNT = "maxBindsMobileNumberPerRegcode";
	
	/**
	 * 标准锻炼时长
	 */
	public static final String STANDARD_EXERCISE_DURATION = "standardExercisesDuration";
	
	/**
	 * 相邻两行记录隔多长时间作为某次的锻炼时间
	 */
	public static final String EXERCISE_RECORD_INTERVAL = "exerciseRecordInterval";
	
	/**
	 * 按照有效锻炼次数累加的锻炼时长来兑换一次免费的闻音辨识服务
	 */
	public static final String CHANGE_FREECOUNT_DURATION = "changeFreeCountDuration";
	
	/**
	 * 当多长时间没有锻炼时，系统给出提醒信息。默认7天
	 */
	public static final String EXERCISE_NOTIFY_DAYS = "exerciseNotifyDays";
	
	/**
	 * amr 音频文件上传路径
	 */
	public static final String AMR_FILE_PATH = "amrFilePath";
	
	/**
	 * wav 音频文件共享路径
	 */
	public static final String WAV_FILE_PATH = "wavFilePath";
	
	/**
	 * amr 音频文件转换成wav的路径
	 */
	public static final String CONVERT_FILE_PATH = "convertFilePath";
	
	/**
	 * 乐药 保存路径
	 */
	public static final String MUSIC_FILE_PATH = "musicFilePath";
	
	/**
	 * 锻炼记录保存路径
	 */
	public static final String EXERCISE_FILE_PATH = "exerciseFilePath";
	
	/**
	 * 定价-金额 用于支付页面查询单价
	 */
	public static final String PRICE_AMOUNT = "priceAmount";
	
	/**
	 * 定价-购买次数
	 */
	public static final String PRICE_COUNTS = "priceCounts";
	
	/**
	 * 缺省配置：默认的乐药上传天数在之内的为新
	 */
	public static final String MUSIC_NEW_DAYS = "musicNewDays";
	
	/**
	 * 缺省配置：赠送服务次数
	 */
	public static final String BONUS_COUNT = "bonusCount";
	
	/**
	 * 缺省页面查询数量
	 */
	public static final String PAGE_SIZE = "pageSize";
	
	/**
	 * 缺省配置：密码长度
	 */
	public static final String PASSWORD_LENGTH = "passwordLength";
	
	/**
	 * 缺省配置：本机对外的URL访问基础地址
	 */
	public static final String BASE_URL_ADDRESS = "baseUrlAddress";
	
	/**
	 * 缺省配置：短信后缀
	 */
	public static final String SMS_SUFFIX = "smsSuffix";
	
	/**
	 * 缺省配置：是否限制服务次数
	 */
	public static final String LIMIT_SERVICE_COUNT = "limitServiceCount";
}
