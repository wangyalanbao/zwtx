package com.hxlm.health.web.controller.app.member;

import com.hxlm.health.web.ErrorMsg;
import com.hxlm.health.web.FileInfo;
import com.hxlm.health.web.Result;
import com.hxlm.health.web.Status;
import com.hxlm.health.web.controller.app.BaseController;
import com.hxlm.health.web.service.FileService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/3/12.
 */
@Controller("fileUploadController")
@RequestMapping("/member/zwtx")
public class FileUploadController extends BaseController{

    @Resource(name = "fileServiceImpl")
    private FileService fileService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ErrorMsg upload(FileInfo.FileType fileType, MultipartFile file, HttpServletRequest request) {
        Result result = new Result();
        ErrorMsg errorMsg = new ErrorMsg();

        if(fileService.isValid(fileType,file)){
//			符合类型要求直接使用upload(upload(上传的类型， 上传的文件)上传本地方法
            String url=fileService.upload(fileType, file);
            if(StringUtils.isEmpty(url)) {
                errorMsg.setMessage("上传文件错误，请重新上传");
                errorMsg.setCode(Status.FAILED_UPLOAD_FILE);
            }

            result.setData(url);
            result.setCode(Status.SUCCESS);
            result.setMessage("上传文件成功");

            return result;
        } else {
            errorMsg.setMessage("上传格式不正确，请重新上传");
            errorMsg.setCode(Status.FAILED_UPLOAD_FILE);
            return errorMsg;
        }
    }
}
