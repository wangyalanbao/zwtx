/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.controller.admin;

import com.hxlm.health.web.FileInfo;
import com.hxlm.health.web.FileInfo.FileType;
import com.hxlm.health.web.FileInfo.OrderType;
import com.hxlm.health.web.Message;
import com.hxlm.health.web.service.FileService;
import com.hxlm.health.web.util.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller - 文件处理
 * 
 * /admin/file/upload
 * 
 */
@Controller("adminFileController")
@RequestMapping("/admin/file")
public class FileController extends BaseController {

	@Resource(name = "fileServiceImpl")
	private FileService fileService;

	/**
	 * 上传
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
	public void upload(FileType fileType, MultipartFile file, HttpServletResponse response) {
		Map<String, Object> data = new HashMap<String, Object>();
		if (!fileService.isValid(fileType, file)) {//格式不正确
			data.put("message", Message.warn("admin.upload.invalid"));
		} else {
			String url = fileService.upload(fileType, file, false);//上传的类型，上传的文件，是否云上传参数false上传本地
			if (url == null) {//文件不正确
				data.put("message", Message.warn("admin.upload.error"));//上传文件时出现错误
			} else {
				data.put("message", SUCCESS_MESSAGE);
				data.put("url", url);
				data.put("error", 0);
			}
		}
		try {
			response.setContentType("text/html; charset=UTF-8");
			JsonUtils.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 浏览
	 */
	@RequestMapping(value = "/browser", method = RequestMethod.GET)
	public @ResponseBody
	List<FileInfo> browser(String path, FileType fileType, OrderType orderType) {
		return fileService.browser(path, fileType, orderType);
	}

}