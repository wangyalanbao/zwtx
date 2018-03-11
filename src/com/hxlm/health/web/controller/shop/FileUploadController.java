package com.hxlm.health.web.controller.shop;

import com.hxlm.health.web.FileInfo;
import com.hxlm.health.web.Result;
import com.hxlm.health.web.Status;
import com.hxlm.health.web.service.FileService;
import com.hxlm.health.web.service.MemberService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * Created by delll on 2015/6/30./fileUpload/upload
 */
@Controller("pFileUploadController")
@RequestMapping("/fileUploads")
public class FileUploadController extends BaseController {

    @Resource(name = "fileServiceImpl")
    private FileService fileService;
    @Resource(name = "memberServiceImpl")
    private MemberService memberService;

    /**
     * 添加会员头像接口
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    Result upload(FileInfo.FileType fileType,MultipartFile file){
        Result result=new Result();
//		上传文件是否符合既定的文件格式
        if(fileService.isValid(fileType,file)){
//			符合类型要求直接使用upload(upload(上传的类型， 上传的文件)上传本地方法
            String url=fileService.upload(fileType, file);
            if(StringUtils.isEmpty(url)){
                result.setData("上传文件错误，请从新上传");
                result.setStatus(Status.FAILED_UPLOAD_FILE);
            }else {
                result.setData(url);
                result.setStatus(Status.SUCCESS);
            }
        }else {
            result.setData("上传格式不正确，请从新上传");
            result.setStatus(Status.FAILED_UPLOAD_FILE);
        }
        return result;
    }



}
