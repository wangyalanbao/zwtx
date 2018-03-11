package com.hxlm.health.web.yuntongxun.group;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * 
* @Description: TODO(群组对象) 
* @author： baohj
* @date： 2014年9月13日 下午1:48:30
 */
public class Group implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String groupId;//群组id
	private String name;//群组名称
	private String count;//群组成员数
	private String type;//群组类型 0：临时组(上限100人)  1：普通组(上限300人)  2：VIP组 (上限500人)
	private String permission;//申请加入模式 0：默认直接加入1：需要身份验证 2:私有群组
	private List<MemberStatusVo> list=new ArrayList<MemberStatusVo>();
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public List<MemberStatusVo> getList() {
		return list;
	}
	public void setList(List<MemberStatusVo> list) {
		this.list = list;
	}

}
