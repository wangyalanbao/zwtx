package com.hxlm.health.web.service.impl;

import com.cms.admin.base.action.WelcomeAct;
import com.common.data.entity.DefaultConfig;
import com.common.data.manager.DefaultConfigMng;
import com.hxlm.health.web.ConfigConstants;
import com.hxlm.health.web.DateEditor;
import com.hxlm.health.web.Status;
import com.hxlm.health.web.service.ConfigService;
import com.yanhuang.services.SMSServiceInput;
import com.yanhuang.services.SMSWebServiceClient;
import com.yanhuang.services.SMSWebServicePortType;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.List;


@Service("configServiceImpl")
public class ConfigServiceImpl implements ConfigService {

	private Logger logger = Logger.getLogger(this.getClass());

	WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
	private SMSWebServiceClient smsServiceYanhuang = (SMSWebServiceClient) wac.getBean("smsServiceYanhuang");

	private int maxMobileCounts;
	private int freeCount;
	private int standardExerciseDuration;
	private int validExerciseRecordDuration;
	private int changeDuration;
	private int exerciseNotifyDays;
	private String amrFilePath;
	private String wavFilePath;
	private String exerciseFilePath;
	private String musicFilePath;
	private int priceAmount;
	private int priceCounts;
	private int musiceNewDays;
	private int bonusCount;
	private int pageSize;
	private int passwordLength;
	private String convertFilePath;
	private String baseUrlAddress;
	private String smsSuffix;

	private boolean limitServiceCount;

	private boolean isLoad = false;

	private DefaultConfigMng defaultConfigMng;

	@Override
	public int getMaxMobileCounts() {
		String pwdLen = this.defaultConfigMng.getDefaultConfigValue(WelcomeAct.SOFT_ID, ConfigConstants.MAX_REGCODE_MOBILE_COUNT);

		if(!StringUtils.isEmpty(pwdLen)) {
			int duration = Integer.parseInt(pwdLen);
			if(duration != this.maxMobileCounts) {
				this.loadDefaultConfig();
			}
		}
		return this.maxMobileCounts;
	}

	@Override
	public int getFreeCount() {
		String pwdLen = this.defaultConfigMng.getDefaultConfigValue(WelcomeAct.SOFT_ID, ConfigConstants.FREE_COUNT);

		if(!StringUtils.isEmpty(pwdLen)) {
			int duration = Integer.parseInt(pwdLen);
			if(duration != this.freeCount) {
				this.loadDefaultConfig();
			}
		}
		return this.freeCount;
	}

	@Override
	public int getStandardExerciseDuration() {
		String pwdLen = this.defaultConfigMng.getDefaultConfigValue(WelcomeAct.SOFT_ID, ConfigConstants.STANDARD_EXERCISE_DURATION);

		if(!StringUtils.isEmpty(pwdLen)) {
			int duration = Integer.parseInt(pwdLen);
			if(duration != this.standardExerciseDuration) {
				this.loadDefaultConfig();
			}
		}
		return this.standardExerciseDuration;
	}

	@Override
	public int getValidExerciseRecordDuration() {
		String pwdLen = this.defaultConfigMng.getDefaultConfigValue(WelcomeAct.SOFT_ID, ConfigConstants.EXERCISE_RECORD_INTERVAL);

		if(!StringUtils.isEmpty(pwdLen)) {
			int duration = Integer.parseInt(pwdLen);
			if(duration != this.validExerciseRecordDuration) {
				this.loadDefaultConfig();
			}
		}
		return this.validExerciseRecordDuration;
	}

	@Override
	public int getChangeDuration() {
		String pwdLen = this.defaultConfigMng.getDefaultConfigValue(WelcomeAct.SOFT_ID, ConfigConstants.CHANGE_FREECOUNT_DURATION);

		if(!StringUtils.isEmpty(pwdLen)) {
			int duration = Integer.parseInt(pwdLen);
			if(duration != this.changeDuration) {
				this.loadDefaultConfig();
			}
		}
		return this.changeDuration;
	}

	@Override
	public int getExerciseNotifyDays() {
		if(!this.isLoad) {
			this.loadDefaultConfig();
		}
		return this.exerciseNotifyDays;
	}

	@Override
	public boolean loadDefaultConfig() {
		this.isLoad = false;
		List<DefaultConfig> defaultConfigs = this.defaultConfigMng.getConfigBySoftId(WelcomeAct.SOFT_ID);
		if(defaultConfigs != null && defaultConfigs.size() > 0) {
			for(DefaultConfig config : defaultConfigs) {
				if(config.getName().equalsIgnoreCase(ConfigConstants.FREE_COUNT)) {
					this.freeCount = Integer.parseInt(config.getValue());
				} else if(config.getName().equalsIgnoreCase(ConfigConstants.MAX_REGCODE_MOBILE_COUNT)) {
					this.maxMobileCounts = Integer.parseInt(config.getValue());
				} else if(config.getName().equalsIgnoreCase(ConfigConstants.STANDARD_EXERCISE_DURATION)) {
					this.standardExerciseDuration = Integer.parseInt(config.getValue());
				} else if(config.getName().equalsIgnoreCase(ConfigConstants.EXERCISE_RECORD_INTERVAL)) {
					this.validExerciseRecordDuration = Integer.parseInt(config.getValue());
				} else if(config.getName().equalsIgnoreCase(ConfigConstants.CHANGE_FREECOUNT_DURATION)) {
					this.changeDuration = Integer.parseInt(config.getValue());
				} else if(config.getName().equalsIgnoreCase(ConfigConstants.EXERCISE_NOTIFY_DAYS)) {
					this.exerciseNotifyDays = Integer.parseInt(config.getValue());
				} else if(config.getName().equalsIgnoreCase(ConfigConstants.MUSIC_FILE_PATH)) {
					this.musicFilePath = config.getValue();
				} else if(config.getName().equalsIgnoreCase(ConfigConstants.AMR_FILE_PATH)) {
					this.amrFilePath = config.getValue();
				} else if(config.getName().equalsIgnoreCase(ConfigConstants.WAV_FILE_PATH)) {
					this.wavFilePath = config.getValue();
				} else if(config.getName().equalsIgnoreCase(ConfigConstants.EXERCISE_FILE_PATH)) {
					this.exerciseFilePath = config.getValue();
				} else if(config.getName().equalsIgnoreCase(ConfigConstants.PRICE_AMOUNT)) {
					this.priceAmount = Integer.parseInt(config.getValue());
				} else if(config.getName().equalsIgnoreCase(ConfigConstants.PRICE_COUNTS)) {
					this.priceCounts = Integer.parseInt(config.getValue());
				} else if(ConfigConstants.MUSIC_NEW_DAYS.equalsIgnoreCase(config.getName())) {
					this.musiceNewDays = Integer.parseInt(config.getValue());
				} else if(config.getName().equalsIgnoreCase(ConfigConstants.BONUS_COUNT)) {
					this.bonusCount = Integer.parseInt(config.getValue());
				} else if(config.getName().equalsIgnoreCase(ConfigConstants.PAGE_SIZE)) {
					this.pageSize = Integer.parseInt(config.getValue());
				} else if(config.getName().equalsIgnoreCase(ConfigConstants.PASSWORD_LENGTH)) {
					this.passwordLength = Integer.parseInt(config.getValue());
				} else if(config.getName().equalsIgnoreCase(ConfigConstants.CONVERT_FILE_PATH)) {
					this.convertFilePath = config.getValue();
				} else if(config.getName().equalsIgnoreCase(ConfigConstants.BASE_URL_ADDRESS)) {
					this.baseUrlAddress = config.getValue();
				} else if(config.getName().equalsIgnoreCase(ConfigConstants.SMS_SUFFIX)) {
					this.smsSuffix = config.getValue();
				} else if(config.getName().equalsIgnoreCase(ConfigConstants.LIMIT_SERVICE_COUNT)) {
					this.limitServiceCount = Boolean.parseBoolean(config.getValue());
				}
			}
			this.isLoad = true;
			defaultConfigs.clear();
			return true;
		}
		return false;
	}

	@Override
	public String getAmrFilePath() {
		if(!this.isLoad) {
			this.loadDefaultConfig();
		}

		return this.amrFilePath;
	}

	@Override
	public String getWavFilePath() {
		if(!this.isLoad) {
			this.loadDefaultConfig();
		}
		return this.wavFilePath;
	}

	@Override
	public String getExerciseFilePath() {
		if(!this.isLoad) {
			this.loadDefaultConfig();
		}
		return this.exerciseFilePath;
	}

	@Override
	public int getPriceAmount() {
		if(!this.isLoad) {
			this.loadDefaultConfig();
		}
		return this.priceAmount;
	}

	@Override
	public int getPriceCounts() {
		if(!this.isLoad) {
			this.loadDefaultConfig();
		}
		return this.priceCounts;
	}

	@Override
	public String getMusicFilePath() {
		if(!this.isLoad) {
			this.loadDefaultConfig();
		}
		return this.musicFilePath;
	}

	@Override
	public int getMusicNewDays() {
		String value = this.defaultConfigMng.getDefaultConfigValue(WelcomeAct.SOFT_ID, ConfigConstants.MUSIC_NEW_DAYS);

		if(!StringUtils.isEmpty(value)) {
			int iMusic = Integer.parseInt(value);
			if(iMusic != this.musiceNewDays) {
				this.loadDefaultConfig();
			}
		}
		return this.musiceNewDays;
	}

	@Override
	public int getBonusCount() {
		String value = this.defaultConfigMng.getDefaultConfigValue(WelcomeAct.SOFT_ID, ConfigConstants.BONUS_COUNT);

		if(!StringUtils.isEmpty(value)) {
			int iBonus = Integer.parseInt(value);
			if(iBonus != this.bonusCount) {
				this.loadDefaultConfig();
			}
		}
		return this.bonusCount;
	}

	@Override
	public int getPageSize() {
		if(!this.isLoad) {
			this.loadDefaultConfig();
		}
		return this.pageSize;
	}

	@Override
	public int getPasswordLength() {
		String pwdLen = this.defaultConfigMng.getDefaultConfigValue(WelcomeAct.SOFT_ID, ConfigConstants.PASSWORD_LENGTH);

		if(!StringUtils.isEmpty(pwdLen)) {
			int iPwd = Integer.parseInt(pwdLen);
			if(iPwd != this.passwordLength) {
				this.loadDefaultConfig();
			}
		}
		return this.passwordLength;
	}

	@Override
	public String getConvertFilePath() {
		if(!this.isLoad) {
			this.loadDefaultConfig();
		}
		return this.convertFilePath;
	}

	@Override
	public String getBaseUrlAddress() {
		if(!this.isLoad) {
			this.loadDefaultConfig();
		}
		return this.baseUrlAddress;
	}

	@Override
	public String getSmsSuffix() {
		String sms = this.defaultConfigMng.getDefaultConfigValue(WelcomeAct.SOFT_ID, ConfigConstants.SMS_SUFFIX);
		if(!StringUtils.isEmpty(sms) && !sms.equalsIgnoreCase(this.smsSuffix)) {
			this.loadDefaultConfig();
		}
		return this.smsSuffix;
	}

	@Override
	public boolean getLimitServiceCount() {
		String limit = this.defaultConfigMng.getDefaultConfigValue(WelcomeAct.SOFT_ID, ConfigConstants.LIMIT_SERVICE_COUNT);
		if(!StringUtils.isEmpty(limit) && !limit.equalsIgnoreCase(Boolean.toString(this.limitServiceCount))) {
			this.limitServiceCount = Boolean.parseBoolean(limit);
		}
		return this.limitServiceCount;
	}

	/**
	 * 使用炎黄的短信网关发送短信
	 * @param mobile
	 * @param content
	 */
	public Integer sendSmsYH(String mobile, String content) {
		logger.error(" ---------------------------- sendsms mobile --------------------------------------- "+mobile);
		logger.error(" ---------------------------- sendsms content --------------------------------------- "+content);
		SMSServiceInput input = new SMSServiceInput();
		input.setServiceID("炎黄手机闻音短信业务");
		input.setSendtarget(mobile);
		String smsSuffix = "[炎黄健康短信验证]";
		if (!StringUtils.isEmpty(smsSuffix)) {
			content = "您的验证码是" + content + "，15分钟内有效" + smsSuffix;
		}

		input.setSmcontent(content);
		input.setRcompleteTime(DateEditor.format(new Date(), "yyyy-MM-dd"));
		input.setPAD4("code");
		logger.error(" ---------------------------- sendsms input --------------------------------------- "+input.toString());
		SMSWebServicePortType service = this.smsServiceYanhuang.getSMSWebServiceHttpPort();
		String result = service.soaSMSServiceForCUNLE(input);
		logger.error(" ---------------------------- sendsms service --------------------------------------- "+result);
		return Status.SUCCESS;
	}

	/**
	 * 使用炎黄的短信网关发送短信
	 * @param mobile
	 * @param content
	 */
	public Integer sendSmsGW(String mobile, String content) {
		logger.error(" ---------------------------- sendsms mobile --------------------------------------- "+mobile);
		logger.error(" ---------------------------- sendsms content --------------------------------------- "+content);
		SMSServiceInput input = new SMSServiceInput();
		input.setServiceID("炎黄手私人顾问选择");
		input.setSendtarget(mobile);
		String smsSuffix = "[炎黄健康]";
		content = content + smsSuffix;

		input.setSmcontent(content);
		input.setRcompleteTime(DateEditor.format(new Date(), "yyyy-MM-dd"));
		input.setPAD4("code");
		logger.error(" ---------------------------- sendsms input --------------------------------------- "+input.toString());
		SMSWebServicePortType service = this.smsServiceYanhuang.getSMSWebServiceHttpPort();
		String result = service.soaSMSServiceForCUNLE(input);
		logger.error(" ---------------------------- sendsms service --------------------------------------- "+result);
		return Status.SUCCESS;
	}


}
