package com.hxlm.health.web.controller.admin;

import com.hxlm.health.web.Message;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.Airport;
import com.hxlm.health.web.entity.InlandCost;
import com.hxlm.health.web.service.AirportService;
import com.hxlm.health.web.service.InlandCostService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by guofeng on 2015/12/14.
 * controller--机场
 */
@Controller("adminAirportController")
@RequestMapping("/admin/airport")
public class AirportController extends BaseController{

    private static final Integer SEARCH_COUNT = 5;

    @Resource(name = "airportServiceImpl")
    private AirportService airportService;
    @Resource(name = "inlandCostServiceImpl")
    private InlandCostService inlandCostService;


    /**
     * 列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Boolean isVirtual, Pageable pageable,ModelMap model){
        model.addAttribute("isVirtual", isVirtual);
        model.addAttribute("page", airportService.findPage(isVirtual, pageable));
        return "/admin/airport/list";
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public @ResponseBody
    Message delete(Long[] ids) {
        if (ids != null) {
            airportService.delete(ids);
        }
        return SUCCESS_MESSAGE;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/search_airport",method = RequestMethod.GET)
    public @ResponseBody
    List<Airport> search_airport(String q) {
        List<Airport> airportList = new ArrayList<Airport>();
        if(StringUtils.isEmpty(q)){
            airportList = airportService.findList(SEARCH_COUNT,null,null);
        }else {
            airportList = airportService.search(q, SEARCH_COUNT);
        }
        return airportList;
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(ModelMap model){
        model.addAttribute("baseVirtuals", airportService.findAll());
        model.addAttribute("locations", Airport.Location.values());
        model.addAttribute("inlandCost", inlandCostService.findAll());
//        model.addAttribute("foreignCost", foreignCostService.findAll());
        return "/admin/airport/add";
    }
    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(Airport airport,Long baseAirportId,Long inlandCostId,Long foreignCostId, RedirectAttributes redirectAttributes){
        airport.setInlandCost(inlandCostService.find(inlandCostId));
//        airport.setForeignCost(foreignCostService.find(foreignCostId));
//        airport.setMaintenanceCost(inlandCost.getAgencyCost());
//        airport.setTransferCost(inlandCost.getTransferCost());
        if (!isValid(airport)) {
            return ERROR_VIEW;
        }
        airportService.save(airport);
        addFlashMessage(redirectAttributes,SUCCESS_MESSAGE);
        return "redirect:list.jhtml";

    }

    /**
     * 编辑
     */
    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String edit(Long id, ModelMap model){
        model.addAttribute("baseVirtuals", airportService.findAll());
        model.addAttribute("airport",airportService.find(id));
        model.addAttribute("locations", Airport.Location.values());
        model.addAttribute("inlandCost", inlandCostService.findAll());
//        model.addAttribute("foreignCost", foreignCostService.findAll());
        return "/admin/airport/edit";
    }

    /**
     * 更新
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public String update(Airport airport,Long baseAirportId,Long inlandCostId,Long foreignCostId, RedirectAttributes redirectAttributes){
        airport.setInlandCost(inlandCostService.find(inlandCostId));
//        airport.setForeignCost(foreignCostService.find(foreignCostId));
        if(!isValid(airport)){
            return ERROR_VIEW;
        }
        airportService.update(airport);
        addFlashMessage(redirectAttributes,SUCCESS_MESSAGE);
        return "redirect:list.jhtml";
    }

    /**
     * 查询国内地面服务费
     */
    @RequestMapping(value = "/cost",method = RequestMethod.GET)
    public @ResponseBody
    Map<String, BigDecimal> cost(Long inlandCostId){
        InlandCost inlandCost = inlandCostService.find(inlandCostId);
        Map<String, BigDecimal> data = new HashMap<String, BigDecimal>();
        data.put("agencyCost",inlandCost.getAgencyCost());
        data.put("transferCost",inlandCost.getTransferCost());
        return data;
    }



}
