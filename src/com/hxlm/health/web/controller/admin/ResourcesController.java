package com.hxlm.health.web.controller.admin;

import com.hxlm.health.web.FileInfo;
import com.hxlm.health.web.Message;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.Resources;
import com.hxlm.health.web.entity.ResourcesWarehouse;
import com.hxlm.health.web.service.FileService;
import com.hxlm.health.web.service.ProductCategoryService;
import com.hxlm.health.web.service.ProductService;
import com.hxlm.health.web.service.ResourcesService;
import com.hxlm.health.web.util.EvaluateSttings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Iterator;

/**
 *  Created by guofeng on 2016/2/17.
 *  Controller -- 资源上传
 */

@Controller("adminResourcesController")
@RequestMapping("/admin/resources")
public class ResourcesController extends BaseController {

    @Resource(name ="resourcesServiceImpl")
    private ResourcesService resourcesService;
    @Resource(name = "fileServiceImpl")
    private FileService fileService;

    // 列表
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list( Pageable pageable, ModelMap model) {
        model.addAttribute("page", resourcesService.findPage(pageable));
        return "/admin/resources/list";
    }

    // 添加
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap model) {
        model.addAttribute("types", EvaluateSttings.getInstance().getMap());
        return "/admin/resources/add";
    }

    // 编辑
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Long id, ModelMap model) {
        model.addAttribute("resources", resourcesService.find(id));
        model.addAttribute("types", EvaluateSttings.getInstance().getMap());
        return "/admin/resources/edit";
    }

    // 更新
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(Resources resources, RedirectAttributes redirectAttributes) {
        for (Iterator<ResourcesWarehouse> iterator = resources.getResourcesWarehouses().iterator(); iterator.hasNext();) {
            ResourcesWarehouse resourcesWarehouse = iterator.next();
            if (resourcesWarehouse == null || resourcesWarehouse.isEmpty()) {
                iterator.remove();
                continue;
            }
            if (resourcesWarehouse.getFile() != null && !resourcesWarehouse.getFile().isEmpty()) {
                String type = resourcesWarehouse.getType();
                if (type.equals("file")) {
                    if (!fileService.isValid(FileInfo.FileType.file, resourcesWarehouse.getFile())) {
                        addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
                        return "redirect:add.jhtml?id="+resources.getId();
                    }
                    resourcesWarehouse.setType(resourcesWarehouse.getType());
                    resourcesWarehouse.setSource(fileService.upload(FileInfo.FileType.file, resourcesWarehouse.getFile()));
                } else if (type.equals("video") || type.equals("audio") || type.equals("other")) {
                    if (!fileService.isValid(FileInfo.FileType.media, resourcesWarehouse.getFile())) {
                        addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
                        return "redirect:add.jhtml?id="+resources.getId();
                    }
                    resourcesWarehouse.setType(resourcesWarehouse.getType());
                    resourcesWarehouse.setSource(fileService.upload(FileInfo.FileType.media, resourcesWarehouse.getFile()));
                }
            }
        }
        if (!isValid(resources)) {
            return ERROR_VIEW;
        }
        resourcesService.update(resources);
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:list.jhtml";
    }

    // 保存
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Resources resources, RedirectAttributes redirectAttributes) {
        for (Iterator<ResourcesWarehouse> iterator = resources.getResourcesWarehouses().iterator(); iterator.hasNext();) {
            ResourcesWarehouse resourcesWarehouse = iterator.next();
            if (resourcesWarehouse == null || resourcesWarehouse.isEmpty()) {
                iterator.remove();
                continue;
            }
            if (resourcesWarehouse.getFile() != null && !resourcesWarehouse.getFile().isEmpty()) {
                String type = resourcesWarehouse.getType();
                if (type.equals("file")) {
                    if (!fileService.isValid(FileInfo.FileType.file, resourcesWarehouse.getFile())) {
                        addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
                        return "redirect:add.jhtml";
                    }
                    resourcesWarehouse.setSource(fileService.upload(FileInfo.FileType.file, resourcesWarehouse.getFile()));
                } else if (type.equals("video") || type.equals("audio") || type.equals("other")) {
                    if (!fileService.isValid(FileInfo.FileType.media, resourcesWarehouse.getFile())) {
                        addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
                        return "redirect:add.jhtml";
                    }
                    resourcesWarehouse.setSource(fileService.upload(FileInfo.FileType.media, resourcesWarehouse.getFile()));
                }
            }
        }

        if (!isValid(resources)) {
            return ERROR_VIEW;
        }

        resourcesService.save(resources);
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:list.jhtml";
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    Message delete(Long[] ids) {
        resourcesService.delete(ids);
        return SUCCESS_MESSAGE;
    }

}
