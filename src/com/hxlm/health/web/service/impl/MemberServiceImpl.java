/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.*;
import com.hxlm.health.web.dao.MemberDao;
import com.hxlm.health.web.entity.*;
import com.hxlm.health.web.service.MemberService;
import com.hxlm.health.web.util.SettingUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.persistence.LockModeType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Service - 会员
 * 
 * 
 * 
 */
@Service("memberServiceImpl")
public class MemberServiceImpl extends BaseServiceImpl<Member, Long> implements MemberService {

	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;
	@Resource(name = "memberDaoImpl")
	public void setBaseDao(MemberDao memberDao) {
		super.setBaseDao(memberDao);
	}

	@Transactional(readOnly = true)
	public boolean usernameExists(String username) {
		return memberDao.usernameExists(username);
	}

	@Transactional(readOnly = true)
	public boolean usernameDisabled(String username) {
		Assert.hasText(username);
		Setting setting = SettingUtils.get();
		if (setting.getDisabledUsernames() != null) {
			for (String disabledUsername : setting.getDisabledUsernames()) {
				if (StringUtils.containsIgnoreCase(username, disabledUsername)) {
					return true;
				}
			}
		}
		return false;
	}

	@Transactional(readOnly = true)
	public boolean emailExists(String email) {
		return memberDao.emailExists(email);
	}

	@Transactional(readOnly = true)
	public boolean emailUnique(String previousEmail, String currentEmail) {
		if (StringUtils.equalsIgnoreCase(previousEmail, currentEmail)) {
			return true;
		} else {
			if (memberDao.emailExists(currentEmail)) {
				return false;
			} else {
				return true;
			}
		}
	}

	public void save(Member member, Admin operator) {
		Assert.notNull(member);
		memberDao.persist(member);
		if (member.getBalance().compareTo(new BigDecimal(0)) > 0) {

		}
	}

	public Member saveMember(Member member, Admin operator){
		Assert.notNull(member);
		member = memberDao.saveAndGet(member);
		if (member.getBalance().compareTo(new BigDecimal(0)) > 0) {

		}
		return member;
	}

	public Member saveMembers(Member member){
		Assert.notNull(member);
		member = memberDao.saveAndGet(member);

		return member;
	}

	public void update(Member member, Integer modifyPoint, BigDecimal modifyBalance, String depositMemo, Admin operator) {
		Assert.notNull(member);

		memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);

		if (modifyPoint != null && modifyPoint != 0 && member.getPoint() + modifyPoint >= 0) {
			member.setPoint(member.getPoint() + modifyPoint);
		}

		if (modifyBalance != null && modifyBalance.compareTo(new BigDecimal(0)) != 0 && member.getBalance().add(modifyBalance).compareTo(new BigDecimal(0)) >= 0) {
			member.setBalance(member.getBalance().add(modifyBalance));

		}
		memberDao.merge(member);
	}

	@Transactional(readOnly = true)
	public Member findByUsername(String username) {
		return memberDao.findByUsername(username);
	}

	@Transactional(readOnly = true)
	public List<Member> findListByEmail(String email) {
		return memberDao.findListByEmail(email);
	}

	@Transactional(readOnly = true)
	public List<Object[]> findPurchaseList(Date beginDate, Date endDate, Integer count) {
		return memberDao.findPurchaseList(beginDate, endDate, count);
	}

	@Transactional(readOnly = true)
	public boolean isAuthenticated() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Principal principal = (Principal) request.getSession().getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return true;
			}
		}
		return false;
	}

	@Transactional(readOnly = true)
	public Member getCurrent() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Principal principal = (Principal) request.getSession().getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return memberDao.find(principal.getId());
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	public String getCurrentUsername() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Principal principal = (Principal) request.getSession().getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return principal.getUsername();
			}
		}
		return null;
	}




	/***
	 *
	 * 验证码
	 */
	@Transactional(readOnly = true)
	public void randomCode(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		int randomNumber = 5;
		// 图片宽度
		int width = 100;
		// 图片高度
		int height = 35;
		// 创建随机码
		char[] ranlist = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
				.toCharArray();
		StringBuilder sb = new StringBuilder();
		Random randomer = new Random();
		for (int i = 0; i < randomNumber; i++) {
			sb.append(ranlist[randomer.nextInt(ranlist.length)]);
		}
		// 保存到session中
		HttpSession session = req.getSession();
		session.setAttribute("RANDOM_IN_SESSION", sb.toString());

		// 画画
		// 创建一个图片对象
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_BGR);
		// 得到图片对象对应的绘画缓存,绘画都在Graphics对象上
		Graphics g = image.getGraphics();

		// 绘制背景
		g.setColor(Color.WHITE);
		// 填充矩形
		g.fillRect(0, 0, width, height);
		// 绘制边框
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width - 1, height - 1);
		// 绘制文字
		g.setColor(Color.GRAY);
		g.setFont(new Font("宋体", Font.ITALIC, 24));
		g.drawString(sb.toString(), 13, height - 9);

		//绘制干扰点
		for (int i = 0; i < 300; i++) {
			g.fillRect(randomer.nextInt(width - 1),
					randomer.nextInt(height - 1), 1, 1);
		}
		// 关闭绘画资源
		g.dispose();

		//清除图片缓存：IE7  firefox验证码没有反应问题
		//设定网页的到期时间，一旦过期则必须到服务器上重新调用
		resp.setDateHeader("Expires",-1);
		//Cache-Control指定请求和响应遵循的缓存机制   no-cache指示请求或响应消息不能缓存
		resp.setHeader("Cache-Control","no-cache");
		//是用于设定禁止浏览器从本地机的缓存中调阅页面内容，设定后一旦离开网页就无法从Cache中
//		再调出
		resp.setHeader("Pragma","no-cache");

		// 输出
		ImageIO.write(image, "JPG", resp.getOutputStream());
	}


	/**
	 * 查询所有
	 */
	public Result getList(){
		Result  result=new Result();
		result.setData(super.findAll());
		result.setStatus(Status.SUCCESS);
		return result;
	}


	@Transactional
	public Long saveMember(Member member){
		Assert.notNull(member);
		super.save(member);
		return memberDao.getIdentifier(member);

	}

//	id名称模糊查询
	@Transactional(readOnly = true)
	public List<Member> search(String keyword, Integer count) {
		return memberDao.searth(keyword, count);
	}

	@Transactional
	public void updateUA(Long id, String ua) {
		memberDao.updateUA(id, ua);
	}

	@Transactional
	public void updateMeber(Long id,String memberImage, Member.Gender gender,String name,String mobile,String phone,String nation,String address,Boolean isMarried,Member.IdentityType identityType,String idNumber,Boolean isMedicare,String birthday){
		memberDao.updateMember(id,memberImage,gender,name,mobile,phone,nation,address,isMarried,identityType,idNumber,isMedicare,birthday);
	}
	@Transactional
	public void updateToken(Long id,String deviceToken){
		memberDao.updateToken(id,deviceToken);
	}

	@Transactional
	public void updateWXUserMsg(Member member) {
		memberDao.updateWXUserMsg(member);
	}


}

