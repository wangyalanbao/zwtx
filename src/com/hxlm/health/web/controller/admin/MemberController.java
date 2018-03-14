/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.controller.admin;

import com.hxlm.health.web.*;
import com.hxlm.health.web.Message;
import com.hxlm.health.web.entity.*;
import com.hxlm.health.web.entity.Member.Gender;
import com.hxlm.health.web.service.*;
import com.hxlm.health.web.util.SettingUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Controller - 会员
 * 
 * 
 * 
 */
@Controller("adminMemberController")
@RequestMapping("/admin/member")
public class MemberController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "memberAttributeServiceImpl")
	private MemberAttributeService memberAttributeService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;

	/**
	 * 检查用户名是否被禁用或已存在
	 */
	@RequestMapping(value = "/check_username", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		if (memberService.usernameDisabled(username) || memberService.usernameExists(username)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 检查E-mail是否唯一
	 */
	@RequestMapping(value = "/check_email", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkEmail(String previousEmail, String email) {
		if (StringUtils.isEmpty(email)) {
			return false;
		}
		if (memberService.emailUnique(previousEmail, email)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model) {
		Member member = memberService.find(id);
		model.addAttribute("genders", Gender.values());
		model.addAttribute("memberAttributes", memberAttributeService.findList());
		model.addAttribute("member", member);
		/*List<MemberPoints> points = memberPointsService.findList(member, false);
		// 总积分
		Long allPoints = 0L;
		// 可使用积分
		Long surplusPoints = 0L;
		// 已使用积分
		Long usedPoints = 0l;
		// 过期积分
		Long expirePoints = 0l;
		if (!points.isEmpty() && points.size() > 0) {
			for (MemberPoints mp : points) {
				allPoints += mp.getPoint();
				usedPoints += mp.getUsedPoint();
				if(!mp.getIsExpire()){
					surplusPoints += mp.getSurplusPoint();
				} else {
					expirePoints +=  mp.getSurplusPoint();
				}
			}
		}
		model.addAttribute("points", points);
		model.addAttribute("allPoints", allPoints);
		model.addAttribute("surplusPoints", surplusPoints);
		model.addAttribute("usedPoints", usedPoints);
		model.addAttribute("expirePoints", expirePoints);


		List<MemberVirtualPoints> virtualPointses = memberVirtualPointsService.findList(member, false);
		Long allVirtualPoints = 0L;
		// 可使用积分
		Long surplusVirtualPoints = 0L;
		// 已使用积分
		Long usedVirtualPoints = 0l;
		// 过期积分
		Long expireVirtualPoints = 0l;
		if (!virtualPointses.isEmpty() && virtualPointses.size() > 0) {
			for (MemberVirtualPoints mp : virtualPointses) {
				allVirtualPoints += mp.getPoint();
				usedVirtualPoints += mp.getUsedPoint();
				if(!mp.getIsExpire()){
					surplusVirtualPoints += mp.getSurplusPoint();
				} else {
					expireVirtualPoints +=  mp.getSurplusPoint();
				}
			}
		}
*/
		/*model.addAttribute("virtualPointses", virtualPointses);
		model.addAttribute("allVirtualPoints", allVirtualPoints);
		model.addAttribute("surplusVirtualPoints", surplusVirtualPoints);
		model.addAttribute("usedVirtualPoints", usedVirtualPoints);
		model.addAttribute("expireVirtualPoints", expireVirtualPoints);
*/
		return "/admin/member/view";
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("genders", Gender.values());
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("memberAttributes", memberAttributeService.findList());
		//model.addAttribute("distributorsTree", distributorsService.findTree());
		return "/admin/member/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Member member, Long memberRankId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		member.setMemberRank(memberRankService.find(memberRankId));
		if (!isValid(member, BaseEntity.Save.class)) {
			return ERROR_VIEW;
		}
		Setting setting = SettingUtils.get();
		if (member.getUsername().length() < setting.getUsernameMinLength() || member.getUsername().length() > setting.getUsernameMaxLength()) {
			return ERROR_VIEW;
		}
		if (member.getPassword().length() < setting.getPasswordMinLength() || member.getPassword().length() > setting.getPasswordMaxLength()) {
			return ERROR_VIEW;
		}
		if (memberService.usernameDisabled(member.getUsername()) || memberService.usernameExists(member.getUsername())) {
			return ERROR_VIEW;
		}
		if (!setting.getIsDuplicateEmail() && memberService.emailExists(member.getEmail())) {
			return ERROR_VIEW;
		}
		member.removeAttributeValue();
		for (MemberAttribute memberAttribute : memberAttributeService.findList()) {
			String parameter = request.getParameter("memberAttribute_" + memberAttribute.getId());
			if (memberAttribute.getType() == MemberAttribute.Type.name || memberAttribute.getType() == MemberAttribute.Type.address || memberAttribute.getType() == MemberAttribute.Type.zipCode || memberAttribute.getType() == MemberAttribute.Type.phone || memberAttribute.getType() == MemberAttribute.Type.mobile || memberAttribute.getType() == MemberAttribute.Type.text || memberAttribute.getType() == MemberAttribute.Type.select) {
				if (memberAttribute.getIsRequired() && StringUtils.isEmpty(parameter)) {
					return ERROR_VIEW;
				}
				member.setAttributeValue(memberAttribute, parameter);
			} else if (memberAttribute.getType() == MemberAttribute.Type.gender) {
				Gender gender = StringUtils.isNotEmpty(parameter) ? Gender.valueOf(parameter) : null;
				if (memberAttribute.getIsRequired() && gender == null) {
					return ERROR_VIEW;
				}
				member.setGender(gender);
			} else if (memberAttribute.getType() == MemberAttribute.Type.birth) {
				try {
					Date birth = StringUtils.isNotEmpty(parameter) ? DateUtils.parseDate(parameter, CommonAttributes.DATE_PATTERNS) : null;
					if (memberAttribute.getIsRequired() && birth == null) {
						return ERROR_VIEW;
					}
					member.setBirth(birth);
				} catch (ParseException e) {
					return ERROR_VIEW;
				}
			} else if (memberAttribute.getType() == MemberAttribute.Type.area) {
				Area area = StringUtils.isNotEmpty(parameter) ? areaService.find(Long.valueOf(parameter)) : null;
				if (area != null) {
					member.setArea(area);
				} else if (memberAttribute.getIsRequired()) {
					return ERROR_VIEW;
				}
			} else if (memberAttribute.getType() == MemberAttribute.Type.checkbox) {
				String[] parameterValues = request.getParameterValues("memberAttribute_" + memberAttribute.getId());
				List<String> options = parameterValues != null ? Arrays.asList(parameterValues) : null;
				if (memberAttribute.getIsRequired() && (options == null || options.isEmpty())) {
					return ERROR_VIEW;
				}
				member.setAttributeValue(memberAttribute, options);
			}
		}
		member.setUsername(member.getUsername().toLowerCase());
		member.setPassword(DigestUtils.md5Hex(member.getPassword()));
		member.setAmount(new BigDecimal(0));
		member.setIsLocked(false);
		member.setLoginFailureCount(0);
		member.setLockedDate(null);
		member.setRegisterIp(request.getRemoteAddr());
		member.setLoginIp(null);
		member.setLoginDate(null);
		member.setSafeKey(null);
		member.setCart(null);
		member.setOrders(null);
		//member.setDeposits(null);
		member.setPayments(null);
		//member.setCouponCodes(null);
		member.setReceivers(null);
		member.setReviews(null);
		//member.setConsultations(null);
		member.setFavoriteProducts(null);
		member.setProductNotifies(null);
		member.setInMessages(null);
		member.setOutMessages(null);
		/*if(member.getDistributorsId() != null){
			member.setAuditStatus(Member.AuditStatus.accepted);
		}*/
		memberService.save(member, adminService.getCurrent());
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	/*@RequestMapping(value = "/edit", method = RequestMethod.GET)*/
	/*public String edit(Long id, ModelMap model) {
		Member member = memberService.find(id);
		model.addAttribute("genders", Gender.values());
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("memberAttributes", memberAttributeService.findList());
	*//*	model.addAttribute("distributorsTree", distributorsService.findTree());
		model.addAttribute("member", member);
		model.addAttribute("distributors", distributorsService.find(member.getDistributorsId()));
		List<MemberPoints> points = memberPointsService.findList(member, null);*//*
		// 总积分
		Long allPoints = 0L;
		// 可使用积分
		Long surplusPoints = 0L;
		// 已使用积分
		Long usedPoints = 0l;
		// 过期积分
		Long expirePoints = 0l;
		*//*if (!points.isEmpty() && points.size() > 0) {
			for (MemberPoints mp : points) {
				allPoints += mp.getPoint();
				usedPoints += mp.getUsedPoint();
				if(!mp.getIsExpire()){
					surplusPoints += mp.getSurplusPoint();
				} else {
					expirePoints +=  mp.getSurplusPoint();
				}
			}
		}*//*
		model.addAttribute("points", points);
		model.addAttribute("allPoints", allPoints);
		model.addAttribute("surplusPoints", surplusPoints);
		model.addAttribute("usedPoints", usedPoints);
		model.addAttribute("expirePoints", expirePoints);

		//List<MemberVirtualPoints> virtualPointses = memberVirtualPointsService.findList(member, false);
		Long allVirtualPoints = 0L;
		// 可使用积分
		Long surplusVirtualPoints = 0L;
		// 已使用积分
		Long usedVirtualPoints = 0l;
		// 过期积分
		Long expireVirtualPoints = 0l;
		if (!virtualPointses.isEmpty() && virtualPointses.size() > 0) {
			*//*for (MemberVirtualPoints mp : virtualPointses) {
				allVirtualPoints += mp.getPoint();
				usedVirtualPoints += mp.getUsedPoint();
				if(!mp.getIsExpire()){
					surplusVirtualPoints += mp.getSurplusPoint();
				} else {
					expireVirtualPoints +=  mp.getSurplusPoint();
				}
			}*//*
		}

		model.addAttribute("virtualPointses", virtualPointses);
		model.addAttribute("allVirtualPoints", allVirtualPoints);
		model.addAttribute("surplusVirtualPoints", surplusVirtualPoints);
		model.addAttribute("usedVirtualPoints", usedVirtualPoints);
		model.addAttribute("expireVirtualPoints", expireVirtualPoints);

		return "/admin/member/edit";
	}*/

	/**
	 * 更新
	 */
	/*@RequestMapping(value = "/update", method = RequestMethod.POST)*/
	/*public String update(Member member, Long memberRankId, Integer modifyPoint, Integer modifyVirtualPoint, BigDecimal modifyBalance, String depositMemo, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		member.setMemberRank(memberRankService.find(memberRankId));
		*//*Distributors distributors = distributorsService.find(member.getDistributorsId());
		if(distributors != null){
			member.setDistributorsName(distributors.getName());
		} else {
			member.setDistributorsId(null);
			member.setDistributorsName(null);
			member.setAuditStatus(null);
		}
*//*
		if (!isValid(member)) {
			return ERROR_VIEW;
		}
		Setting setting = SettingUtils.get();
		if (member.getPassword() != null && (member.getPassword().length() < setting.getPasswordMinLength() || member.getPassword().length() > setting.getPasswordMaxLength())) {
			return ERROR_VIEW;
		}
		Member pMember = memberService.find(member.getId());
		if (pMember == null) {
			return ERROR_VIEW;
		}
		if (!setting.getIsDuplicateEmail() && !memberService.emailUnique(pMember.getEmail(), member.getEmail())) {
			return ERROR_VIEW;
		}
		member.removeAttributeValue();
		for (MemberAttribute memberAttribute : memberAttributeService.findList()) {
			String parameter = request.getParameter("memberAttribute_" + memberAttribute.getId());
			if (memberAttribute.getType() == MemberAttribute.Type.name || memberAttribute.getType() == MemberAttribute.Type.address || memberAttribute.getType() == MemberAttribute.Type.zipCode || memberAttribute.getType() == MemberAttribute.Type.phone || memberAttribute.getType() == MemberAttribute.Type.mobile || memberAttribute.getType() == MemberAttribute.Type.text || memberAttribute.getType() == MemberAttribute.Type.select) {
				if (memberAttribute.getIsRequired() && StringUtils.isEmpty(parameter)) {
					return ERROR_VIEW;
				}
				member.setAttributeValue(memberAttribute, parameter);
			} else if (memberAttribute.getType() == MemberAttribute.Type.gender) {
				Gender gender = StringUtils.isNotEmpty(parameter) ? Gender.valueOf(parameter) : null;
				if (memberAttribute.getIsRequired() && gender == null) {
					return ERROR_VIEW;
				}
				member.setGender(gender);
			} else if (memberAttribute.getType() == MemberAttribute.Type.birth) {
				try {
					Date birth = StringUtils.isNotEmpty(parameter) ? DateUtils.parseDate(parameter, CommonAttributes.DATE_PATTERNS) : null;
					if (memberAttribute.getIsRequired() && birth == null) {
						return ERROR_VIEW;
					}
					member.setBirth(birth);
				} catch (ParseException e) {
					return ERROR_VIEW;
				}
			} else if (memberAttribute.getType() == MemberAttribute.Type.area) {
				Area area = StringUtils.isNotEmpty(parameter) ? areaService.find(Long.valueOf(parameter)) : null;
				if (area != null) {
					member.setArea(area);
				} else if (memberAttribute.getIsRequired()) {
					return ERROR_VIEW;
				}
			} else if (memberAttribute.getType() == MemberAttribute.Type.checkbox) {
				String[] parameterValues = request.getParameterValues("memberAttribute_" + memberAttribute.getId());
				List<String> options = parameterValues != null ? Arrays.asList(parameterValues) : null;
				if (memberAttribute.getIsRequired() && (options == null || options.isEmpty())) {
					return ERROR_VIEW;
				}
				member.setAttributeValue(memberAttribute, options);
			}
		}
		if (StringUtils.isEmpty(member.getPassword())) {
			member.setPassword(pMember.getPassword());
		} else {
			member.setPassword(DigestUtils.md5Hex(member.getPassword()));
		}
		if (pMember.getIsLocked() && !member.getIsLocked()) {
			member.setLoginFailureCount(0);
			member.setLockedDate(null);
		} else {
			member.setIsLocked(pMember.getIsLocked());
			member.setLoginFailureCount(pMember.getLoginFailureCount());
			member.setLockedDate(pMember.getLockedDate());
		}
		if(member.getDistributorsId() != null){
			member.setAuditStatus(Member.AuditStatus.accepted);
		}
		BeanUtils.copyProperties(member, pMember, new String[]{"channel", "username", "point", "amount", "balance", "registerIp", "loginIp", "loginDate", "safeKey", "cart", "orders", "deposits", "payments", "couponCodes", "receivers", "reviews", "consultations", "favoriteProducts", "productNotifies", "inMessages", "outMessages", "memberCardses", "memberCategory", "refereesAmount", "authentication"});
		if (modifyVirtualPoint != null && modifyVirtualPoint != 0 && pMember.getVirtualPoint() + modifyVirtualPoint >= 0) {
			pMember.setVirtualPoint(pMember.getVirtualPoint() + modifyVirtualPoint);
		}
		memberService.update(pMember, modifyPoint, modifyBalance, depositMemo, adminService.getCurrent());

		if (modifyPoint != null) {
			MemberPoints memberPoints = new MemberPoints();
			memberPoints.setSn(null);
			memberPoints.setIsExpire(false);
			memberPoints.setIsSend(true);
			memberPoints.setMember(member);
			memberPoints.setPoint(Long.valueOf(modifyPoint.toString()));
			Date d = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			calendar.add(Calendar.YEAR, 99);
			memberPoints.setExpire(calendar.getTime());
			memberPointsService.save(memberPoints);
		}

		if (modifyVirtualPoint != null) {
			MemberVirtualPoints memberVirtualPoints = new MemberVirtualPoints();
			memberVirtualPoints.setMember(member);
			memberVirtualPoints.setGetType(MemberVirtualPoints.GetType.system);
			memberVirtualPoints.setPoint(Long.valueOf(modifyVirtualPoint.toString()));
			memberVirtualPoints.setMemberIdFrom(null);
			Date d = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			calendar.add(Calendar.DAY_OF_MONTH, 90);
			memberVirtualPoints.setExpire(calendar.getTime());
			memberVirtualPoints.setIsExpire(false);
			memberVirtualPoints.setPosterBase(null);
			memberVirtualPointsService.save(memberVirtualPoints);
		}

		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}*/

	/**
	 * 列表
	 */
	/*@RequestMapping(value = "/list", method = RequestMethod.GET)*/
	/*public String list(String name,String channelSn,String openid,String lxtxUserId,Pageable pageable, ModelMap model) {
		model.addAttribute("name",name);
		model.addAttribute("channelSn",channelSn);
		model.addAttribute("openid",openid);
		model.addAttribute("lxtxUserId",lxtxUserId);
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("memberAttributes", memberAttributeService.findAll());
		model.addAttribute("channels",channelService.findAll());

		List<Filter> filterList = new ArrayList<Filter>();
		if(StringUtils.isNotEmpty(name)){
			Filter filter = new Filter();
			filter.setProperty("memberRank");
			filter.setValue(memberRankService.findByName(name));
			filter.setOperator(Filter.Operator.eq);
			filterList.add(filter);
		}

		if(StringUtils.isNotEmpty(channelSn)){
			Filter filter = new Filter();
			filter.setProperty("channel");
			filter.setValue(channelService.findBySn(channelSn));
			filter.setOperator(Filter.Operator.eq);
			filterList.add(filter);
		}
		pageable.setFilters(filterList);
		model.addAttribute("page", memberService.findPage(openid,lxtxUserId,pageable));
		return "/admin/member/list";
	}*/

	/**
	 * 删除
	 */
	/*@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody*/
	/*Message delete(Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				Member member = memberService.find(id);
				if (member != null && member.getBalance().compareTo(new BigDecimal(0)) > 0) {
					return Message.error("admin.member.deleteExistDepositNotAllowed", member.getUsername());
				}
			}
			memberService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}
*/
	/**
	 * 删除积分
	 */
	/*@RequestMapping(value = "/del_point", method = RequestMethod.GET)
	public String delPoint(Long pid, Long mid, RedirectAttributes redirectAttributes) {
		memberPointsService.updateUsedPoint(pid);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:edit.jhtml?id=" + mid;
	}*/

	/**
	 * 删除积分
	 */
	//@RequestMapping(value = "/del_point_virtual", method = RequestMethod.GET)
	/*public String delPointVirtual(Long pid, Long mid, RedirectAttributes redirectAttributes) {
		memberVirtualPointsService.updateUsedPoint(pid);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:edit.jhtml?id=" + mid;
	}*/

	/*// 导入
	@RequestMapping(value = "/member_import", method = RequestMethod.POST)
	public void member_import(HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		String path =  fileService.upload(request);
		if(StringUtils.isNotEmpty(path)){
			try {
				InputStream is = null;
				if (path.startsWith("http://pic-10049890.file.myqcloud.com")) {
					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					is = conn.getInputStream();
				} else {
					String itemPath = request.getSession().getServletContext().getRealPath("");
					String filePath = path.substring(path.indexOf("upload"));
					path = itemPath +"/" + filePath;
					File file=new File(path);
					if(file.isFile() && file.exists()) {
						is = new FileInputStream(path);
					}
				}

				if(is != null){ //判断文件是否存在
					HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
					// 循环工作表Sheet

					HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
					// 循环行Row
					for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
						HSSFRow hssfRow = hssfSheet.getRow(rowNum);
						if (hssfRow == null) {
							continue;
						}

						if (getValue(hssfRow.getCell(0)) == null || getValue(hssfRow.getCell(0)) == "") {
//                            addFlashMessage(redirectAttributes, com.hxlm.health.web.Message.success("导入成功"));
//                            return "redirect:list.jhtml";
							break;
						}

						HSSFCell username = hssfRow.getCell(0);
						HSSFCell name = hssfRow.getCell(1);
						HSSFCell gender = hssfRow.getCell(2);
						HSSFCell password = hssfRow.getCell(3);
						HSSFCell memberRankName = hssfRow.getCell(4);
						HSSFCell idNumber = hssfRow.getCell(5);
						if(!isChinaPhoneLegal(getValue(username))){
							hssfRow.createCell(6);
							hssfRow.createCell(6).setCellValue("手机号不正确");
							continue;
						}
						MemberRank memberRank = memberRankService.findByName(getValue(memberRankName));

						Member member =  memberService.findByUsername(getValue(username));
						if(member != null){
							if(memberRank != null){
								member.setMemberRank(memberRank);
							}
							if(StringUtils.isNotEmpty(getValue(name))){
								member.setName(getValue(name));
							}
							if(StringUtils.equals(getValue(gender), "男")){
								member.setGender(Gender.male);
							} else if(StringUtils.equals(getValue(gender), "女")){
								member.setGender(Gender.female);
							}
							if(StringUtils.isNotEmpty(getValue(password))){
								member.setPassword(DigestUtils.md5Hex(getValue(password)));
							}
							if(StringUtils.isNotEmpty(getValue(idNumber))){
								member.setIdNumber(getValue(idNumber));
							}
							memberService.update(member);
						} else {
							Setting setting = SettingUtils.get();
							member = new Member();
							member.setUsername(getValue(username));
							if(StringUtils.isEmpty(getValue(password))){
								member.setPassword(DigestUtils.md5Hex(getValue(username).substring(5)));
							} else {
								member.setPassword(DigestUtils.md5Hex(getValue(password)));
							}
							member.setName(getValue(name));
							if(StringUtils.equals(getValue(gender), "男")){
								member.setGender(Gender.male);
							} else if(StringUtils.equals(getValue(gender), "女")){
								member.setGender(Gender.female);
							}
							member.setPoint(setting.getRegisterPoint());
							member.setAmount(new BigDecimal(0));
							member.setBalance(new BigDecimal(0));
							member.setIsEnabled(true);
							member.setIsLocked(false);
							member.setLoginFailureCount(0);
							member.setLockedDate(null);
							member.setRegisterIp(request.getRemoteAddr());
							member.setLoginIp(request.getRemoteAddr());
							member.setLoginDate(new Date());
							member.setSafeKey(null);
							member.setFavoriteProducts(null);
							member.setPhone(getValue(username));
							member.setMobile(getValue(username));
							member.setDiamondAmount(new BigDecimal(0));
							member.setDiamondNums(0);
							member.setCustomMadeAmount(new BigDecimal(0));
							member.setReturnsAmount(new BigDecimal(0));
							member.setIdNumber(getValue(idNumber));

							if(memberRank != null){
								member.setMemberRank(memberRank);
							} else {
								member.setMemberRank(memberRankService.findDefault());
							}
							memberService.save(member);
						}

						hssfRow.createCell(6);
						hssfRow.createCell(6).setCellValue("成功");

					}
					response.setContentType("application/force-download");
					response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode("result.xls", "UTF-8"));

					ServletOutputStream out = response.getOutputStream();
					hssfWorkbook.write(out);
					out.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
//                addFlashMessage(redirectAttributes, com.hxlm.health.web.Message.error("File read error"));
//                return "redirect:list.jhtml";
			}
		}
//        return "redirect:list.jhtml";
	}
*/

	/**
	 * 得到Excel表中的值
	 *
	 * @param hssfCell
	 *            Excel中的每一个格子
	 * @return Excel中每一个格子中的值
	 */
	/*@SuppressWarnings("static-access")*/
	/*private String getValue(HSSFCell hssfCell) {
		if(hssfCell == null){
			return "";
		}
		hssfCell.setCellType(hssfCell.CELL_TYPE_STRING);
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			// 返回布尔类型的值
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			// 返回数值类型的值
			return String.valueOf(hssfCell.getNumericCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BLANK || hssfCell.getCellType() == HSSFCell.CELL_TYPE_ERROR) { //空值
			return null;
		} else {
			// 返回字符串类型的值
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}*/


	/**
	 * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
	 */
	/*public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
		String regExp = "^((1[0-9]))\\d{9}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}*/


}