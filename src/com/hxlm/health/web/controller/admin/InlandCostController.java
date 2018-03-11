package com.hxlm.health.web.controller.admin;

import com.hxlm.health.web.Message;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.Airport;
import com.hxlm.health.web.entity.InlandCost;
import com.hxlm.health.web.entity.Role;
import com.hxlm.health.web.service.AirportService;
import com.hxlm.health.web.service.InlandCostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by guofeng on 2016/1/5.
 * Controller--国内机场代理费
 */
@Controller("adminInlandCostController")
@RequestMapping("/admin/inland_cost")
public class InlandCostController extends BaseController {

    @Resource(name = "inlandCostServiceImpl")
    private InlandCostService inlandCostService;
    @Resource(name = "airportServiceImpl")
    private AirportService airportService;
    public static final String DECOLLATOR = ",";

    /**
     * 添加
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap model) {
        List<Airport> airports = airportService.findByInlandCostNull();
        List<Airport> airportsInland = airportService.findByInlandCostNull();
        List<Airport> airportsForeign = airportService.findByForeignCostNull();

        List<String> areasInland = new ArrayList<String>();
        for (Airport airport : airportsInland) {
            areasInland.add(airport.getCity());
        }
        List<String> areasForeigns = new ArrayList<String>();
        for (Airport airport : airportsForeign) {
            areasForeigns.add(airport.getArea());
        }
        model.addAttribute("areasForeigns", areasForeigns);
        model.addAttribute("areasInland", areasInland);
        model.addAttribute("airports", airports);
        return "/admin/inland_cost/add";
    }


    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(InlandCost inlandCost, String[] areas, RedirectAttributes redirectAttributes) {
        if (!isValid(inlandCost)) {
            return ERROR_VIEW;
        }
        String st = "";
        for (String s : areas) {
            st = st + s + DECOLLATOR;

        }
        inlandCost.setArea(st);
        inlandCostService.save(inlandCost);
        //保存完后自动修改机场数据里的外键，和地面代理费用 1拿到所有的列表 2循环操作当查到机场里城市的值与st相等时变更
//        List<Airport> airports = airportService.findByInlandCostNull();
        List<Airport> airportsInland = airportService.findByInlandCostNull();
        List<Airport> airportsForeign = airportService.findByForeignCostNull();
        String[] str = st.split(DECOLLATOR);
        //当所在区域为国内时 更改对应机场管理费和调机费
        if (inlandCost.getAirfield() == InlandCost.Airfield.interior) {

            for (int i = 0; i < str.length; i++) {
                for (int j = 0; j < airportsInland.size(); j++) {
                    if (airportsInland.get(j).getCity().equals(str[i])) {
                        airportsInland.get(j).setInlandCost(inlandCost);
                        airportsInland.get(j).setMaintenanceCost(inlandCost.getAgencyCost());
                        airportsInland.get(j).setTransferCost(inlandCost.getTransferCost());
                        airportService.update(airportsInland.get(j));
                    }

                }
            }
        } else if (inlandCost.getAirfield() == InlandCost.Airfield.external) {//当所在区域为国外时 更改对应机场管理费

            for (int i = 0; i < str.length; i++) {
                for (int j = 0; j < airportsForeign.size(); j++) {
                    if (airportsForeign.get(j).getArea().equals(str[i])) {
                        //当国外机场地面费管理里 国家名和 机场数据里国家名相同时。给该国家所有机场循环赋值
                        List<Airport> airportList = airportService.findByArea(str[i]);
                        for (Airport airport : airportList) {
                            airport.setInlandCost(inlandCost);
                            airport.setMaintenanceCost(inlandCost.getAgencyCost());
                            airportService.update(airport);
                        }

                    }

                }
            }
        }

        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:list.jhtml";
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Long id, ModelMap model) {
        List<Airport> airports = airportService.findByInlandCostNull();
//        List<Airport> airportsInland = airportService.findByInlandCostNull();
        List<Airport> airportsForeign = airportService.findByForeignCostNull();


        InlandCost inlandCost = inlandCostService.find(id);
        List<String> areas = new ArrayList<String>();
        for (Airport airport : airports) {
            areas.add(airport.getCity());
        }
        List<String> areasForeign = new ArrayList<String>();
        for (Airport airport : airportsForeign) {
            areasForeign.add(airport.getArea());
        }
        String[] str = inlandCost.getArea().split(DECOLLATOR);
        List<String> strList = new ArrayList<String>();
        for (String s : str) {
            strList.add(s);
        }
        List<Airport> byAreas = airportService.findByAreas(strList);
        List<Airport> byCitys = airportService.findByCitys(strList);
        if (byAreas != null && !byAreas.isEmpty()) {
            for (Airport airport : byAreas) {
                if(!areasForeign.contains(airport.getArea())){
                    areasForeign.add(airport.getArea());
                }
            }
        } else if (byCitys != null && !byCitys.isEmpty()) {
            for (Airport airport : byCitys) {
                if(!areas.contains(airport.getCity())){
                    areas.add(airport.getCity());
                }
            }
        }

        model.addAttribute("strs", str);
        model.addAttribute("areas", areas);
        model.addAttribute("areasForeign", areasForeign);
        model.addAttribute("airports", airports);
        model.addAttribute("inlandCost", inlandCostService.find(id));
        return "/admin/inland_cost/edit";
    }

    /**
     * 更新
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(InlandCost inlandCost, RedirectAttributes redirectAttributes) {

        if (!isValid(inlandCost)) {
            return ERROR_VIEW;
        }
        inlandCostService.update(inlandCost);
        List<Airport> airports = airportService.findByInland();
//        List<Airport> airportsInland = airportService.findByInlandCostNull();
        List<Airport> airportsForeign = airportService.findByForeignCostNull();
        String[] str = inlandCost.getArea().split(DECOLLATOR);
        List<String> strList = new ArrayList<String>();
        for (String s : str) {
            strList.add(s);
        }

        List<Airport> byAreas = airportService.findByAreas(strList);
        List<Airport> byCitys = airportService.findByCitys(strList);
        if (byAreas != null && !byAreas.isEmpty()) {
            airportsForeign.addAll(byAreas);
        } else if (byCitys != null && !byCitys.isEmpty()) {
            airports.addAll(byCitys);
        }
        if (inlandCost.getAirfield() == InlandCost.Airfield.interior) {

            for (int i = 0; i < str.length; i++) {
                for (int j = 0; j < airports.size(); j++) {
                    if (airports.get(j).getCity().equals(str[i])) {
                        airports.get(j).setInlandCost(inlandCost);
                        airports.get(j).setMaintenanceCost(inlandCost.getAgencyCost());
                        airports.get(j).setTransferCost(inlandCost.getTransferCost());
                        airportService.update(airports.get(j));
                    } else {
                        airports.get(j).setInlandCost(null);
                        airports.get(j).setMaintenanceCost(null);
                        airports.get(j).setTransferCost(null);
                        airportService.update(airports.get(j));
                    }

                }
            }
        } else if (inlandCost.getAirfield() == InlandCost.Airfield.external) {//当所在区域为国外时 更改对应机场管理费

            //如果获取国家名为空则跳过
            if (!inlandCost.getArea().isEmpty()) {
                for (int i = 0; i < str.length; i++) {
                    for (int j = 0; j < airports.size(); j++) {
                        if (airports.get(j).getArea().equals(str[i])) {
                            //当国外机场地面费管理里 国家名和 机场数据里国家名相同时。给该国家所有机场循环赋值
                            List<Airport> airportList = airportService.findByArea(str[i]);
                            for (Airport airport : airportList) {
                                airport.setInlandCost(inlandCost);
                                airport.setMaintenanceCost(inlandCost.getTransferCost());
                                airportService.update(airport);
                            }
                        } else {
                            List<Airport> airportList = airportService.findByArea(str[i]);
                            for (Airport airport : airportList) {
                                airport.setInlandCost(null);
                                airport.setMaintenanceCost(null);
                                airportService.update(airport);
                            }
                        }

                    }
                }
            }
        }
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:list.jhtml";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Pageable pageable, ModelMap model) {
        model.addAttribute("page", inlandCostService.findPage(pageable));
        return "/admin/inland_cost/list";
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    Message delete(Long[] ids) {
        if (ids != null) {

            inlandCostService.delete(ids);
        }
        return SUCCESS_MESSAGE;
    }

}
