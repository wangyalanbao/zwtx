package com.hxlm.health.web.yuntongxun.group;


import com.hxlm.health.web.yuntongxun.common.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.ming.sample.util.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* @Description: TODO(群组管理类) 
* @author： baohj
* @date： 2014年9月13日 下午1:50:55
 */
@Controller
public class GroupManagerController {
	protected final Logger logger = Logger.getLogger(GroupManagerController.class);
	/*
	* @Title: createGroup 
	* @Description: TODO(创建群组) 
	* @param subAccount 子账号
	* @param subToken 子账号密码
	* @param name 群组名称
	* @param type 群组类型 0：临时组(上限100人)  1：普通组(上限300人)  2：VIP组 (上限500人)
	* @param permission 申请加入模式 0：默认直接加入1：需要身份验证 2:私有群组
	* @param declared 群组公告
	* @param response
	* @param request
	* @return void    返回类型
	* @throws
	 */
	@RequestMapping("/CreateGroup")
	public String createGroup(String subAccount, String subToken, String name,
			String type, String permission, String declared,
			HttpServletResponse response, HttpServletRequest request) {
		
		try {
			declared= java.net.URLDecoder.decode(declared,"UTF-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		String Authorization = "";
		try {
			Authorization = Base64.encode((subAccount + ":" + CommonUtil
					.formatDate()).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String sig = MD5.md5Digest(subAccount + subToken + CommonUtil.formatDate());
		String url = "https://" + PropertiesHolder.getValue(Constants.RESTIP) + ":" +
		PropertiesHolder.getValue(Constants.RESTPOST)
				+ "/2013-12-26/SubAccounts/" + subAccount
				+ "/Group/CreateGroup?sig=" + sig;
		/* 获取包体 */
		Map<String,String> data=new HashMap<String,String>();
		data.put("name", name);
		data.put("type", type);
		data.put("permission", permission);
		data.put("declared", declared);
		String body = CommonUtil.createXml("Request", data);
		logger.info(String.format("子账号:%s,子主账号密码:%s",subAccount,subToken));
		try {
		HttpPostDemo.send(url, Authorization, body);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "jishixiaoxi";
	}
	
	/*
	* @Title: onequeryGroup 
	* @Description: TODO(获取加入的群) 
	* @param subAccount 子账号
	* @param subToken 子账号密码
	* @param response
	* @param request
	* @return void    返回类型
	* @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/onequeryGroup")
	public void onequeryGroup(String subAccount, String subToken,
			HttpServletResponse response, HttpServletRequest request){
		String Authorization = "";
		try {
			Authorization = Base64.encode((subAccount + ":" + CommonUtil
					.formatDate()).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String sig = MD5.md5Digest(subAccount + subToken + CommonUtil.formatDate());
		String url = "https://" + PropertiesHolder.getValue(Constants.RESTIP) + ":" + PropertiesHolder.getValue(Constants.RESTPOST)
				+ "/2013-12-26/SubAccounts/" + subAccount
				+ "/Member/QueryGroup?sig=" + sig;
		logger.info(String.format("子账号:%s,子主账号密码:%s",subAccount,subToken));
		String result="";
		StringBuffer sb=new StringBuffer();
		PrintWriter out=null;
		try {
			out = response.getWriter();
		    result=HttpPostDemo.send(url, Authorization, null);
		    if(result!=null){
		    	Element root=CommonUtil.getRoot(result);
		    	List<Element> list= root.element("groups").elements("group");
		    	for(int i=0;i<list.size();i++){
		    		sb.append(list.get(i).elementText("groupId"));
		    			if(i!=list.size()-1){
		    				sb.append(",");
		    			}
		    	}
		    }
		    out.write(sb.toString());
		    out.flush();
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				out.close();
			}
		}
	}
	/*
	* @Title: GetPublicGroups 
	* @Description: TODO(获取公共群) 
	* @param subAccount 子账号
	* @param subToken 子账号密码
	* @param response
	* @param request
	* @return void    返回类型
	* @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/GetPublicGroups")
	public void GetPublicGroups(String subAccount, String subToken,
			HttpServletResponse response, HttpServletRequest request){
		String Authorization = "";
		try {
			Authorization = Base64.encode((subAccount + ":" + CommonUtil
					.formatDate()).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String sig = MD5.md5Digest(subAccount + subToken + CommonUtil.formatDate());
		String url = "https://" + PropertiesHolder.getValue(Constants.RESTIP) + ":" + PropertiesHolder.getValue(Constants.RESTPOST)
				+ "/2013-12-26/SubAccounts/" + subAccount
				+ "/Group/GetPublicGroups?sig=" + sig;
		logger.info(String.format("子账号:%s,子主账号密码:%s",subAccount,subToken));
		String result="";
		StringBuffer sb=new StringBuffer();
		PrintWriter out=null;
		try {
			out = response.getWriter();
			String body=CommonUtil.createXml("Request",null);
		    result=HttpPostDemo.send(url, Authorization, body);
		    if(result!=null){
		    	Element root=CommonUtil.getRoot(result);
		    	List<Element> list= root.element("groups").elements("group");
		    	for(int i=0;i<list.size();i++){
		    		sb.append(list.get(i).elementText("groupId"));
		    			if(i!=list.size()-1){
		    				sb.append(",");
		    			}
		    	}
		    }
		    out.write(sb.toString());
		    out.flush();
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				out.close();
			}
		}
	}
	
	/*
	* @Title: JoinGroup 
	* @Description: TODO(邀请加入群) 
	* @param subAccount 子账号
	* @param subToken 子账号密码
	* @param groupId 群组id
	* @param voip 接入群组的voip账号
	* @param confirm 是否需要被邀请人确认 0:需要 1：不需要(自动加入群组)
	* @param declared 邀请理由
	* @param response
	* @param request
	* @return void    返回类型
	* @throws
	 */
	@RequestMapping("/InviteJoinGroup")
	public String JoinGroup(String subAccount, String subToken,String groupId,
			String voip,String confirm,String declared,
			HttpServletResponse response, HttpServletRequest request){
		try {
			declared= java.net.URLDecoder.decode(declared,"UTF-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		
		String Authorization = "";
		try {
			Authorization = Base64.encode((subAccount + ":" + CommonUtil
					.formatDate()).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String sig = MD5.md5Digest(subAccount + subToken + CommonUtil.formatDate());
		String url = "https://" + PropertiesHolder.getValue(Constants.RESTIP) + ":" + PropertiesHolder.getValue(Constants.RESTPOST)
				+ "/2013-12-26/SubAccounts/" + subAccount
				+ "/Group/InviteJoinGroup?sig=" + sig;
		logger.info(String.format("子账号:%s,子主账号密码:%s",subAccount,subToken));
		String result="";
		
		try {
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("Request");
			root.addElement("groupId").setText(groupId);
			Element members=root.addElement("members");
			String[] voips=voip.split(",");
			for(int l=0;l<voips.length;l++){
				members.addElement("member").setText(voips[l].trim());
			}
			root.addElement("confirm").setText(confirm);
			root.addElement("declared").setText(declared);
			
		    result=HttpPostDemo.send(url, Authorization, document.asXML());
		    if(result!=null){
		    	logger.info("邀请成功!");
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "jishixiaoxi";
	}
	/*
	* @Title: QueryMember 
	* @Description: TODO(查询群组成员) 
	* @param subAccount 子账号
	* @param subToken 子账号密码
	* @param groupId 群组id
	* @param response 
	* @param request
	* @return void    返回类型
	* @throws
	 */
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/QueryMember")
	public void QueryMember(String subAccount, String subToken,String groupId,
			HttpServletResponse response, HttpServletRequest request){
		String Authorization = "";
		try {
			Authorization = Base64.encode((subAccount + ":" + CommonUtil
					.formatDate()).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String sig = MD5.md5Digest(subAccount + subToken + CommonUtil.formatDate());
		String url = "https://" + PropertiesHolder.getValue(Constants.RESTIP) + ":" + PropertiesHolder.getValue(Constants.RESTPOST)
				+ "/2013-12-26/SubAccounts/" + subAccount
				+ "/Member/QueryMember?sig=" + sig;
		logger.info(String.format("子账号:%s,子主账号密码:%s",subAccount,subToken));
		String result="";
		List<MemberStatusVo> members=new ArrayList<MemberStatusVo>();
		PrintWriter out=null;
		try {
			out = response.getWriter();
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("Request");
			root.addElement("groupId").setText(groupId);
		    result=HttpPostDemo.send(url, Authorization, document.asXML());
		    if(result!=null){
		    	Element element=CommonUtil.getRoot(result);
		    	List<Element> list= element.element("members").elements("member");
		    	for(int i=0;i<list.size();i++){
		    		MemberStatusVo vo=new MemberStatusVo();
		    		String voipAccount=list.get(i).elementText("voipAccount");
		    		String isBan=list.get(i).elementText("isBan");
		    		vo.setVoipAccount(voipAccount);
		    		vo.setIsBan(isBan);
		    		members.add(vo);
		    	}
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		Group group=new Group();
		group.setGroupId(groupId);
		group.setList(members);
		JSONObject jsonObject = JSONObject.fromObject(group);
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	/*
	* @Title: QueryGroupDetail 
	* @Description: TODO(查询群组属性) 
	* @param subAccount 子账号
	* @param subToken 子账号密码
	* @param groupId 群组id
	* @param response
	* @param request
	* @return void    返回类型
	* @throws
	 */
		@RequestMapping("/QueryGroupDetail")
		public void QueryGroupDetail(String subAccount, String subToken,String groupId,
				HttpServletResponse response, HttpServletRequest request){
			String Authorization = "";
			try {
				Authorization = Base64.encode((subAccount + ":" + CommonUtil
						.formatDate()).getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			String sig = MD5.md5Digest(subAccount + subToken + CommonUtil.formatDate());
			String url = "https://" + PropertiesHolder.getValue(Constants.RESTIP) + ":" + PropertiesHolder.getValue(Constants.RESTPOST)
					+ "/2013-12-26/SubAccounts/" + subAccount
					+ "/Group/QueryGroupDetail?sig=" + sig;
			logger.info(String.format("子账号:%s,子主账号密码:%s",subAccount,subToken));
			String result="";
			List<MemberStatusVo> members=new ArrayList<MemberStatusVo>();
			PrintWriter out=null;
			String declared="";
			try {
				response.setContentType("text/xml;charset=UTF-8");  
				out = response.getWriter();
				Document document = DocumentHelper.createDocument();
				Element root = document.addElement("Request");
				root.addElement("groupId").setText(groupId);
			    result=HttpPostDemo.send(url, Authorization, document.asXML());
			    if(result!=null){
			    	Element element=CommonUtil.getRoot(result);
			    	declared= element.elementText("declared");
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}
			out.write(declared);
			out.flush();
			out.close();}
	
		/*
		* @Title: deleteGroup 
		* @Description: TODO(删除群组) 
		* @param subAccount 子账号
		* @param subToken 子账号密码
		* @param groupId 群组id
		* @param response
		* @param request
		* @return
		* @return String    返回类型
		* @throws
		 */
	@RequestMapping("/deleteGroup")
	public String deleteGroup(String subAccount, String subToken,String groupId,
			HttpServletResponse response, HttpServletRequest request){
		String Authorization = "";
		try {
			Authorization = Base64.encode((subAccount + ":" + CommonUtil
					.formatDate()).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String sig = MD5.md5Digest(subAccount + subToken + CommonUtil.formatDate());
		String url = "https://" + PropertiesHolder.getValue(Constants.RESTIP) + ":" + PropertiesHolder.getValue(Constants.RESTPOST)
				+ "/2013-12-26/SubAccounts/" + subAccount
				+ "/Group/DeleteGroup?sig=" + sig;
		logger.info(String.format("子账号:%s,子主账号密码:%s",subAccount,subToken));
		String result="";
		try {
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("Request");
			root.addElement("groupId").setText(groupId);
		    result=HttpPostDemo.send(url, Authorization, document.asXML());
		    if(result!=null){
		    	
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "jishixiaoxi";
	}
	
	/*
	* @Title: deleteGroupMember 
	* @Description: TODO(删除群组成员) 
	* @param subAccount 子账号
	* @param subToken 子账号密码
	* @param groupId 群组id
	* @param member 将要删除的成员voip账号
	* @param response
	* @param request
	* @return void    返回类型
	* @throws
	 */
	@RequestMapping("/deleteGroupMember")
	public String deleteGroupMember(String subAccount, String subToken,String groupId,
			String member,
			HttpServletResponse response, HttpServletRequest request){
		String Authorization = "";
		try {
			Authorization = Base64.encode((subAccount + ":" + CommonUtil
					.formatDate()).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String sig = MD5.md5Digest(subAccount + subToken + CommonUtil.formatDate());
		String url = "https://" + PropertiesHolder.getValue(Constants.RESTIP) + ":" + PropertiesHolder.getValue(Constants.RESTPOST)
				+ "/2013-12-26/SubAccounts/" + subAccount
				+ "/Group/DeleteGroupMember?sig=" + sig;
		logger.info(String.format("子账号:%s,子主账号密码:%s",subAccount,subToken));
		String result="";
		try {
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("Request");
			root.addElement("groupId").setText(groupId);
			root.addElement("members").addElement("member").setText(member);
		    result=HttpPostDemo.send(url, Authorization, document.asXML());
		    if(result!=null){
		    	
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "jishixiaoxi";
	}
	
	/*
	* @Title: logoutGroup 
	* @Description: TODO(退出群组) 
	* @param subAccount 子账号
	* @param subToken 子账号密码
	* @param groupId 群组id
	* @param response
	* @param request
	* @return void    返回类型
	* @throws
	 */
	@RequestMapping("/logoutGroup")
	public String logoutGroup(String subAccount, String subToken,String groupId,
			HttpServletResponse response, HttpServletRequest request){
		String Authorization = "";
		try {
			Authorization = Base64.encode((subAccount + ":" + CommonUtil
					.formatDate()).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String sig = MD5.md5Digest(subAccount + subToken + CommonUtil.formatDate());
		String url = "https://" + PropertiesHolder.getValue(Constants.RESTIP) + ":" + PropertiesHolder.getValue(Constants.RESTPOST)
				+ "/2013-12-26/SubAccounts/" + subAccount
				+ "/Group/LogoutGroup?sig=" + sig;
		logger.info(String.format("子账号:%s,子主账号密码:%s",subAccount,subToken));
		String result="";
		try {
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("Request");
			root.addElement("groupId").setText(groupId);
		    result=HttpPostDemo.send(url, Authorization, document.asXML());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "jishixiaoxi";
	}
	
	/*
	* @Title: joinGroup 
	* @Description: TODO(成员邀请加入群组) 
	* @param subAccount 子账号
	* @param subToken 子账号密码
	* @param groupId 群组id
	* @param response
	* @param request
	* @return void    返回类型
	* @throws
	 */
	@RequestMapping("/joinGroup")
	public String joinGroup(String subAccount, String subToken,String groupId,
			HttpServletResponse response, HttpServletRequest request){
		String Authorization = "";
		try {
			Authorization = Base64.encode((subAccount + ":" + CommonUtil
					.formatDate()).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String sig = MD5.md5Digest(subAccount + subToken + CommonUtil.formatDate());
		String url = "https://" + PropertiesHolder.getValue(Constants.RESTIP) + ":" + PropertiesHolder.getValue(Constants.RESTPOST)
				+ "/2013-12-26/SubAccounts/" + subAccount
				+ "/Group/JoinGroup?sig=" + sig;
		logger.info(String.format("子账号:%s,子主账号密码:%s",subAccount,subToken));
		String result="";
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setContentType("text/xml;charset=UTF-8"); 
			response.setCharacterEncoding("UTF-8");
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("Request");
			root.addElement("groupId").setText(groupId);
			result=  HttpPostDemo.send(url, Authorization, document.asXML());
			if(result!=null){
				out.write("已加入群组!");
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				out.close();
			}
		}
		return "jishixiaoxi";
	}
	
	
	@RequestMapping("/jsontogroup")
	public void jsontogroup(String subAccount, String subToken,String groupId,
			HttpServletResponse response, HttpServletRequest request){
		String Authorization = "";
		try {
			Authorization = Base64.encode((subAccount + ":" + CommonUtil
					.formatDate()).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String sig = MD5.md5Digest(subAccount + subToken + CommonUtil.formatDate());
		String url = "https://" + PropertiesHolder.getValue(Constants.RESTIP) + ":" + PropertiesHolder.getValue(Constants.RESTPOST)
				+ "/2013-12-26/SubAccounts/" + subAccount
				+ "/Group/JoinGroup?sig=" + sig;
		logger.info(String.format("子账号:%s,子主账号密码:%s",subAccount,subToken));
		try {
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("Request");
			root.addElement("groupId").setText(groupId);
			root.addElement("declared").setText("");
		    HttpPostDemo.send(url, Authorization, document.asXML());
		 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
