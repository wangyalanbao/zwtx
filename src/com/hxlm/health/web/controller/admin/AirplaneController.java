package com.hxlm.health.web.controller.admin;

import com.hxlm.health.web.FileInfo;
import com.hxlm.health.web.Message;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.controller.shop.SystemController;
import com.hxlm.health.web.entity.*;
import com.hxlm.health.web.service.*;
import com.hxlm.health.web.util.ImageCut;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by guofeng on 2015/12/14.
 * controller--飞机
 */
@Controller("adminAirplaneController")
@RequestMapping("/admin/airplane")
public class AirplaneController extends BaseController {

    //飞机
    @Resource(name = "airplaneServiceImpl")
    private AirplaneService airplaneService;
    //飞机品牌
    @Resource(name = "planeBrandServiceImpl")
    private PlaneBrandService planeBrandService;
    //飞机型号
    @Resource(name = "planeTypeServiceImpl")
    private PlaneTypeService planeTypeService;
    //航空公司
    @Resource(name = "companyServiceImpl")
    private CompanyService companyService;
    //机场
    @Resource(name = "airportServiceImpl")
    private AirportService airportService;
    @Resource(name = "airplaneImageServiceImpl")
    private AirplaneImageService airplaneImageService;
    @Resource(name = "fileServiceImpl")
    private FileService fileService;



    /**
     * 列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Long airportI, Long brandId, Long typeId,Long companyId, Pageable pageable,ModelMap model){

        PlaneType planeType = planeTypeService.find(typeId);
        PlaneBrand planeBrand = planeBrandService.find(brandId);
        Company company = companyService.find(companyId);
        Airport airport = airportService.find(airportI);
        model.addAttribute("planeBrands",planeBrandService.findAll());
        model.addAttribute("planeTypes",planeTypeService.findAll());
        model.addAttribute("airports",airportService.findAll());
        model.addAttribute("companys",companyService.findAll());
        model.addAttribute("airportId",airportI);
        model.addAttribute("brandId",brandId);
        model.addAttribute("typeId",typeId);
        model.addAttribute("companyId",companyId);
        model.addAttribute("page", airplaneService.findPage(planeType,planeBrand,company,airport,pageable));
        return "/admin/airplane/list";
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public @ResponseBody
    Message delete(Long[] ids) {
        if (ids != null) {
            airplaneService.delete(ids);
        }
        return SUCCESS_MESSAGE;
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(ModelMap model){
        model.addAttribute("planeBrands",planeBrandService.findAll());
        model.addAttribute("companys",companyService.findAll());
        model.addAttribute("planeTypes",planeTypeService.findAll());
        model.addAttribute("airports",airportService.findAll());
        model.addAttribute("virtuals",airportService.findIsVirtual());
        return "/admin/airplane/add";
    }
    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(Long typeI,Long airportI,Long brandI,Long companyId,Long [] airportIds,Airplane airplane, RedirectAttributes redirectAttributes,Long departureIdAdd){

        for (Iterator<AirplaneImage> iterator = airplane.getAirplaneImages().iterator(); iterator.hasNext();) {
            AirplaneImage airplaneImage = iterator.next();
            if (airplaneImage == null) {
                iterator.remove();
                continue;
            }
            //if (airplaneImage.getFile() != null && !airplaneImage.getFile().isEmpty()) {
            //    if (!fileService.isValid(FileInfo.FileType.image, airplaneImage.getFile())) {
            //        addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
            //        return "redirect:add.jhtml";
            //    }
            //}
        }

        airplane.setAirports(new HashSet<Airport>(airportService.findList(airportIds)));
        airplane.setBrandId(planeBrandService.find(brandI));
        airplane.setBrand(planeBrandService.find(brandI).getName());
        airplane.setCompany(companyService.find(companyId));
        airplane.setTypeId(planeTypeService.find(typeI));
        airplane.setType(planeTypeService.find(typeI).getTypeName());
        airplane.setAirportId(airportService.find(departureIdAdd));
        if (airplane.getRegNos() == null) {
            if(airplaneService.regNoExists(airplane.getNumber())){
                addFlashMessage(redirectAttributes,Message.error("对不起,注册号已存在！"));
                return "redirect:add.jhtml";
            }
            airplane.setRegNo(airplane.getNumber());
        } else {
            if(airplaneService.regNoExists(airplane.getRegNos()+airplane.getNumber())){
                addFlashMessage(redirectAttributes,Message.error("对不起,注册号已存在！"));
                return "redirect:add.jhtml";
            }
            airplane.setRegNo(airplane.getRegNos()+airplane.getNumber());
        }
        if (!isValid(airplane)) {
            return ERROR_VIEW;
        }
        //for (AirplaneImage airplaneImage : airplane.getAirplaneImages()) {
        //    airplaneImageService.build(airplaneImage);
        //}
        Collections.sort(airplane.getAirplaneImages());
        airplaneService.save(airplane);
        addFlashMessage(redirectAttributes,SUCCESS_MESSAGE);
        return "redirect:list.jhtml";

    }

    /**
     * 编辑
     */
    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String edit(Long id, ModelMap model){
        model.addAttribute("planeBrands",planeBrandService.findAll());
        model.addAttribute("companys",companyService.findAll());
        model.addAttribute("planeTypes",planeTypeService.findAll());
        model.addAttribute("airports",airportService.findAll());
        model.addAttribute("virtuals",airportService.findIsVirtual());
        model.addAttribute("airplane", airplaneService.find(id));
        return "/admin/airplane/edit";
    }

    /**
     * 更新
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public String update(Long airportI,Long typeI,Long planeBrandI,Long companyId,Long [] airportIds,Airplane airplane, RedirectAttributes redirectAttributes,Long departureIdAdd){
        for (Iterator<AirplaneImage> iterator = airplane.getAirplaneImages().iterator(); iterator.hasNext();) {
            AirplaneImage airplaneImage = iterator.next();
            if (airplaneImage == null) {
                iterator.remove();
                continue;
            }
            //if (airplaneImage.getFile() != null && !airplaneImage.getFile().isEmpty()) {
            //    if (!fileService.isValid(FileInfo.FileType.image, airplaneImage.getFile())) {
            //        addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
            //        return "redirect:edit.jhtml?id=" + airplane.getId();
            //    }
            //}
        }

        airplane.setAirports(new HashSet<Airport>(airportService.findList(airportIds)));
        airplane.setBrandId(planeBrandService.find(planeBrandI));
        airplane.setBrand(planeBrandService.find(planeBrandI).getName());
        airplane.setCompany(companyService.find(companyId));
        airplane.setTypeId(planeTypeService.find(typeI));
        airplane.setType(planeTypeService.find(typeI).getTypeName());
        airplane.setAirportId(airportService.find(departureIdAdd));
        if (airplane.getRegNos() == null) {
            airplane.setRegNo(airplane.getNumber());
        } else {
            airplane.setRegNo(airplane.getRegNos()+airplane.getNumber());
        }


                if(!isValid(airplane)){
            return ERROR_VIEW;
        }
        //for (AirplaneImage airplaneImage : airplane.getAirplaneImages()) {
        //    airplaneImageService.build(airplaneImage);
        //}
        Collections.sort(airplane.getAirplaneImages());
        airplaneService.update(airplane);
        addFlashMessage(redirectAttributes,SUCCESS_MESSAGE);
        return "redirect:list.jhtml";
    }

    /**
     * 查看
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String view(Long typeIds,Long planeBrandIds,Long companyId,Long id, ModelMap model) {
        model.addAttribute("airplane", airplaneService.find(id));
        model.addAttribute("planeBrand", planeBrandService.find(planeBrandIds));
        model.addAttribute("company", companyService.find(companyId));
        model.addAttribute("planeType",planeTypeService.find(typeIds));
        model.addAttribute("virtuals",airportService.findIsVirtual());
        return "/admin/airplane/view";
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/cutImage",method = RequestMethod.GET)
    public @ResponseBody
    Message cutImage(Integer x1, Integer x2, Integer y1, Integer y2, Integer imageIndex,String path) {
        // path = http://localhost:8080/upload/image/2016/0115/c2b12da6-86eb-4011-9f2b-7da795f8a8c9.png
        // fff ========= /Users/dengyang/workSpace/hxlm-system/out/artifacts/hxlm/WEB-INF/classes/
        String fff = Message.class.getResource("/").getFile().toString();

        String[] strs = path.split("upload");

        String sourcePath = fff.split("WEB-INF")[0]+"upload"+strs[1];
        System.out.println(" sourcePath ------------------------------------------ "+sourcePath);

        String targetPath = fff.split("WEB-INF")[0]+"upload"+strs[1].split("\\.")[0]+"_cut_"+imageIndex+".jpg";
        System.out.println(" targetPath ------------------------------------------ "+targetPath);

        ImageCut.cutImage("JPEG", sourcePath, targetPath, x1, y1, x2, y2);

        String cutpath = strs[0]+"upload"+targetPath.split("upload")[1];
        System.out.println(" cutpath ------------------------------------------ "+cutpath);
        Message message = new Message();
        message.setType(Message.Type.success);
        message.setContent(cutpath);
        return message;
    }
}
