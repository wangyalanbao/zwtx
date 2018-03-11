package com.hxlm.health.web.yuntongxun.group;

import java.io.Serializable;

public class MemberStatusVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*群组成员*/
	private String voipAccount;
	/*是否被禁言 0 ：可发言 1：被禁言（管理员可见）*/
	private String isBan;
	
	public String getVoipAccount() {
		return voipAccount;
	}
	public void setVoipAccount(String voipAccount) {
		this.voipAccount = voipAccount;
	}
	public String getIsBan() {
		return isBan;
	}
	public void setIsBan(String isBan) {
		this.isBan = isBan;
	}

}
