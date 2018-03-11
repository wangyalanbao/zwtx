package com.hxlm.health.web.controller.admin;

import com.hxlm.health.web.Message;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.Airline;
import com.hxlm.health.web.entity.Airport;
import com.hxlm.health.web.entity.PlaneBrand;
import com.hxlm.health.web.entity.PlaneType;
import com.hxlm.health.web.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by guofeng on 2015/12/14.
 * controller--行线
 */
@Controller("adminAirlineController")
@RequestMapping("/admin/airline")
public class AirlineController extends BaseController {

    @Resource(name = "airlineServiceImpl")
    private AirlineService airlineService;
    //飞机品牌
    @Resource(name = "planeBrandServiceImpl")
    private PlaneBrandService planeBrandService;
    //飞机型号
    @Resource(name = "planeTypeServiceImpl")
    private PlaneTypeService planeTypeService;
    //机场
    @Resource(name = "airportServiceImpl")
    private AirportService airportService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list( Long departureId,Long destinationId, Long brandId, Long typeId,Integer month, Pageable pageable,ModelMap model){
        Airport departure = airportService.find(departureId);
        Airport destination = airportService.find(destinationId);
        PlaneBrand planeBrand = planeBrandService.find(brandId);
        PlaneType planeType = planeTypeService.find(typeId);
        model.addAttribute("planeBrand",planeBrandService.findAll());
        model.addAttribute("planeType",planeTypeService.findAll());
        model.addAttribute("airport",airportService.findAll());
        model.addAttribute("departureId",departureId);
        model.addAttribute("destinationId",destinationId);
        model.addAttribute("brandId",brandId);
        model.addAttribute("typeId",typeId);
        model.addAttribute("month",month);
        model.addAttribute("page", airlineService.findPage(departure,destination, planeBrand, planeType,month, pageable));
        return "/admin/airline/list";
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public @ResponseBody
    Message delete(Long[] ids) {
        if (ids != null) {
            airlineService.delete(ids);
        }
        return SUCCESS_MESSAGE;
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(ModelMap model){
        model.addAttribute("planeBrand",planeBrandService.findAll());
        model.addAttribute("planeType",planeTypeService.findAll());
        model.addAttribute("airport",airportService.findAll());
        return "/admin/airline/add";
    }
    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(Long typeIds,Long planeBrandIds,Airline airline,Long departureIdAdd, Long destinationIdAdd ,RedirectAttributes redirectAttributes){
        if(departureIdAdd == null || departureIdAdd == 0){
            addFlashMessage(redirectAttributes,Message.error("不好意思，起飞机场不可为空"));
            return "redirect:add.jhtml";
        }
        if(destinationIdAdd == null ||destinationIdAdd == 0){
            addFlashMessage(redirectAttributes,Message.error("不好意思，降落机场不可为空"));
            return "redirect:add.jhtml";
        }
        airline.setBrandId(planeBrandService.find(planeBrandIds));
        airline.setTypeId(planeTypeService.find(typeIds));
        airline.setDepartureId(airportService.find(departureIdAdd));
        airline.setDestinationId(airportService.find(destinationIdAdd));
        airline.setIsVirtual(true);
        if (!isValid(airline)) {
            return ERROR_VIEW;
        }
        airlineService.save(airline);
        addFlashMessage(redirectAttributes,SUCCESS_MESSAGE);
        return "redirect:list.jhtml";

    }

    /**
     * 编辑
     */
    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String edit(Long id,ModelMap model){
        model.addAttribute("airline",airlineService.find(id));
        model.addAttribute("planeBrand",planeBrandService.findAll());
        model.addAttribute("planeType",planeTypeService.findAll());
        model.addAttribute("airport",airportService.findAll());
        return "/admin/airline/edit";
    }

    /**
     * 更新
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public String update(Long typeIds,Long planeBrandIds,Airline airline,Long departureIdAdd, Long destinationIdAdd , RedirectAttributes redirectAttributes){
        if(departureIdAdd == null || departureIdAdd == 0){
            addFlashMessage(redirectAttributes,Message.error("不好意思，起飞机场不可为空"));
            return "redirect:edit.jhtml?id="+airline.getId();
        }
        if(destinationIdAdd == null ||destinationIdAdd == 0){
            addFlashMessage(redirectAttributes,Message.error("不好意思，降落机场不可为空"));
            return "redirect:edit.jhtml?id="+airline.getId();
        }
        airline.setBrandId(planeBrandService.find(planeBrandIds));
        airline.setTypeId(planeTypeService.find(typeIds));
        airline.setDepartureId(airportService.find(departureIdAdd));
        airline.setDestinationId(airportService.find(destinationIdAdd));
        airline.setIsVirtual(true);
        if(!isValid(airline)){
            return ERROR_VIEW;
        }
        airlineService.update(airline);
        addFlashMessage(redirectAttributes,SUCCESS_MESSAGE);
        return "redirect:list.jhtml";
    }

    /**
     * 获取飞机型号
     */
    @RequestMapping(value = "/getPlaneType", method = RequestMethod.GET)
    public @ResponseBody
    Set<PlaneType> getSpeciality(Long id) {
        PlaneBrand planeBrand = planeBrandService.find(id);
        return planeBrand.getPlaneTypes();
    }

}
