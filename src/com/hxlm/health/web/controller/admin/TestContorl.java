package com.hxlm.health.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hongchen on 2015/9/24.
 *
 */
@Controller
@RequestMapping("/result")
public class TestContorl extends BaseController {


    @RequestMapping("/test")
    public void test(){
        List list=new ArrayList();

//        List<CertPlain> list1=resultService.getCertplain(1, list);
//        Map result=resultService.getCertInfoZX(1,list1);



    }
}
