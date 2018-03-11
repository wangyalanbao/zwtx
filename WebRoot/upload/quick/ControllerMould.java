package com.hxlm.health.web.controller.admin;

import com.hxlm.health.web.Message;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.TestMould;
// 从这里引入包
import com.hxlm.health.web.service.TestMouldService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Controller - 快捷模板
 *
 * Created by testPerson on createTime
 */
@Controller("adminTestMouldController")
@RequestMapping("/admin/testMould")
public class TestMouldController extends BaseController {

    @Resource(name ="testMouldServiceImpl")
    private TestMouldService testMouldService;
    // 从这里注入

    // 列表
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Pageable pageable, HttpServletRequest request, ModelMap model) {
        // 筛选条件放入model
        model.addAttribute("page", testMouldService.findPage(pageable));// 查询结果放入page
        return "/admin/testMould/list";
    }

    // 添加
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap model) {
        // 外键数据查询
        return "/admin/testMould/add";
    }

    // 编辑
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Long id, ModelMap model) {
        // 外键数据查询
        model.addAttribute("testMould", testMouldService.find(id));
        return "/admin/testMould/edit";
    }


    // 保存
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(TestMould testMould, HttpServletRequest request, RedirectAttributes redirectAttributes,ModelMap mode) {
        //获取外键对象
        if (!isValid(testMould)) {
            return ERROR_VIEW;
        }
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        testMouldService.save(testMould);
        return "redirect:list.jhtml";
    }

    // 更新
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(TestMould testMould, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        //获取外键对象
        if (!isValid(testMould)) {
            return ERROR_VIEW;
        }
        testMouldService.update(testMould);
        return "redirect:list.jhtml";
    }


    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    Message delete(Long[] ids) {
        testMouldService.delete(ids);
        return SUCCESS_MESSAGE;
    }

}
