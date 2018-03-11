package com.hxlm.health.web.controller.admin;

import com.hxlm.health.web.Filter;
import com.hxlm.health.web.Message;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.*;
import com.hxlm.health.web.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dengyang on 15/12/18.
 */

@Controller("adminFlightPlanController")
@RequestMapping("/admin/flightplan")
public class FlightPlanController extends BaseController {

    @Resource(name = "flightPlanServiceImpl")
    private FlightPlanService flightPlanService;
    //飞机品牌
    @Resource(name = "planeBrandServiceImpl")
    private PlaneBrandService planeBrandService;
    //飞机型号
    @Resource(name = "planeTypeServiceImpl")
    private PlaneTypeService planeTypeService;
    //机场
    @Resource(name = "airportServiceImpl")
    private AirportService airportService;
    //航空公司
    @Resource(name = "companyServiceImpl")
    private CompanyService companyService;
    //飞机
    @Resource(name = "airplaneServiceImpl")
    private AirplaneService airplaneService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Long flightPlanId, Long airplaneId, Long brandId, Long companyId, Long typeI, Pageable pageable, Date date, ModelMap model) {

        //设置时间
        List<Date> dates = new ArrayList<Date>();
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = DateUtils.toCalendar(date);
        for (int i = 0; i < 7; i++) {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        List<FlightPlan> flightPlanList = flightPlanService.findByAirplane(airplaneService.find(airplaneId));
        Company company = companyService.find(companyId);
        PlaneBrand planeBrand = planeBrandService.find(brandId);
        PlaneType planeType = planeTypeService.find(typeI);
        model.addAttribute("companys", companyService.findAll());
        model.addAttribute("planeTypes", planeTypeService.findAll());
        model.addAttribute("planeBrands", planeBrandService.findAll());
        model.addAttribute("airport", airportService.findAll());
        model.addAttribute("brandId", brandId);
        model.addAttribute("typeI", typeI);
        model.addAttribute("flightPlanList", flightPlanList);
        model.addAttribute("companyId", companyId);
        model.addAttribute("dates", dates);
        model.addAttribute("nowdate", date);
//        model.addAttribute("page", flightPlanService.findPage(planeType,planeBrand,company, pageable));
        model.addAttribute("page", airplaneService.findPage(planeType, planeBrand, company, null, pageable));
        return "/admin/flight_plan/list";
    }

    /**
     * 特价航段列表
     */
    @RequestMapping(value = "/specialList", method = RequestMethod.GET)
    public String specialList(String regNo, String airplaneType, FlightPlan.FlightPlanStatus status, Long companyId, Long departureId, Long destinationId, Pageable pageable, ModelMap model) {

        Company company = companyService.find(companyId);
        Airport departure = airportService.find(departureId);
        Airport destination = airportService.find(destinationId);
        model.addAttribute("regNo", regNo);
        model.addAttribute("departureId", departureId);
        model.addAttribute("departureName", departure != null ? departure.getName() : "");
        model.addAttribute("destinationId", destinationId);
        model.addAttribute("destinationName", destination != null ? destination.getName() : "");
        model.addAttribute("companys", companyService.findAll());
        model.addAttribute("planeTypes", planeTypeService.findAll());
        model.addAttribute("companyId", companyId);
        model.addAttribute("status", status);
        List<Filter> filterList = new ArrayList<Filter>();
        if (departure != null) {
            Filter filter = new Filter();
            filter.setProperty("departureId");
            filter.setValue(departure);
            filter.setOperator(Filter.Operator.eq);
            filterList.add(filter);
        }
        if (destination != null) {
            Filter filter = new Filter();
            filter.setProperty("destinationId");
            filter.setValue(destination);
            filter.setOperator(Filter.Operator.eq);
            filterList.add(filter);
        }
        if (company != null) {
            Filter filter = new Filter();
            filter.setProperty("company");
            filter.setValue(company);
            filter.setOperator(Filter.Operator.eq);
            filterList.add(filter);
        }
        if (StringUtils.isNotEmpty(regNo)) {
            Filter filter = new Filter();
            filter.setProperty("regNo");
            filter.setValue(regNo);
            filter.setOperator(Filter.Operator.like);
            filterList.add(filter);
        }
        if (StringUtils.isNotEmpty(airplaneType)) {
            Filter filter = new Filter();
            filter.setProperty("airplaneType");
            filter.setValue(airplaneType);
            filter.setOperator(Filter.Operator.eq);
            filterList.add(filter);
        }
        if (status != null) {
            Filter filter = new Filter();
            filter.setProperty("status");
            filter.setValue(status);
            filter.setOperator(Filter.Operator.eq);
            filterList.add(filter);
        }
        Filter filter = new Filter();
        filter.setProperty("type");
        filter.setValue(FlightPlan.Type.tuning);
        filter.setOperator(Filter.Operator.eq);
        filterList.add(filter);
        pageable.setFilters(filterList);
        model.addAttribute("page", flightPlanService.findPage(pageable));
        return "/admin/flight_plan/specialList";
    }

    // 获取某段时间内的所有日期
    public static List<Date> findDates(Date dBegin, Date dEnd) {
        List lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    /**
     * 添加航班计划
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public
    @ResponseBody
    Message add(Long flightPlanId, Long departureI, Long destinationI, Long takeoffTime, Long landingTime, Date actualTakeoffTime, Date actualLandingTime, FlightPlan.Type type,BigDecimal flyingTime) {
        Airport departure = airportService.find(departureI);
        Airport destination = airportService.find(destinationI);
        Airplane airplane = airplaneService.find(flightPlanId);
        SimpleDateFormat format;
        Date timetakeoff = null;
        Date timelanding = null;
        //当飞机计划为调机时，保存开始时间到结束时间总天数条调机
        if (type != null && type == FlightPlan.Type.tuning) {
            format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                timetakeoff = format.parse(format.format(takeoffTime));
                timelanding = format.parse(format.format(landingTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            List<Date> lDate = findDates(timetakeoff, timelanding);
            for (Date date : lDate) {
                FlightPlan flightPlan = new FlightPlan();
                flightPlan.setTakeoffTime(date);
                flightPlan.setLandingTime(timelanding);
                flightPlan.setActualTakeoffTime(actualTakeoffTime);
                flightPlan.setActualLandingTime(actualLandingTime);
                flightPlan.setDestination(destination.getName());
                flightPlan.setDeparture(departure.getName());
                flightPlan.setAirplane(airplane);
                flightPlan.setDepartureId(departure);
                flightPlan.setDestinationId(destination);
                flightPlan.setRegNo(airplane.getRegNo());
                flightPlan.setAirplaneType(airplane.getType());
                flightPlan.setStatus(FlightPlan.FlightPlanStatus.userhidden);
                flightPlan.setFlyingTime(flyingTime);
                flightPlan.setCompany(airplane.getCompany());
                flightPlan.setTuningDate(format.format(takeoffTime) + format.format(landingTime));
                flightPlan.setIsReal(false);
                flightPlan.setType(type);
                flightPlanService.price(flightPlan);
                flightPlanService.save(flightPlan);
            }
            return SUCCESS_MESSAGE;
        } else {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        try {
            timelanding = format.parse(format.format(landingTime));
            timetakeoff = format.parse(format.format(takeoffTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        FlightPlan flightPlan = new FlightPlan();
        Calendar calendar = DateUtils.toCalendar(timelanding);
        if (type == FlightPlan.Type.carry ) {
            flightPlan.setDestination(destination.getName());
            flightPlan.setDeparture(departure.getName());
            flightPlan.setLandingTime(timelanding);
        } else {
            calendar.add(Calendar.HOUR_OF_DAY, 24);
            calendar.add(Calendar.SECOND, -1);
            flightPlan.setLandingTime(calendar.getTime());
        }
        flightPlan.setTakeoffTime(timetakeoff);
        flightPlan.setAirplane(airplane);
        flightPlan.setActualTakeoffTime(actualTakeoffTime);
        flightPlan.setActualLandingTime(actualLandingTime);
        flightPlan.setDepartureId(departure);
        flightPlan.setDestinationId(destination);
        flightPlan.setRegNo(airplane.getRegNo());
        flightPlan.setAirplaneType(airplane.getType());
        flightPlan.setStatus(FlightPlan.FlightPlanStatus.userhidden);
        flightPlan.setFlyingTime(flyingTime);
        flightPlan.setCompany(airplane.getCompany());
        flightPlan.setTuningDate(format.format(takeoffTime) + format.format(landingTime));
        flightPlan.setIsReal(false);
        flightPlan.setType(type);
        flightPlanService.price(flightPlan);
        flightPlanService.save(flightPlan);
        return SUCCESS_MESSAGE;
    }

    /**
     * 编辑
     */

    @RequestMapping(value = "/editU", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> editU(Long flightPlanId) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> data = new HashMap<String, Object>();
        FlightPlan flightPlan = flightPlanService.find(flightPlanId);
        data.put("flightPlan", flightPlanService.find(flightPlanId));
        data.put("flightPlanId", flightPlanId);
        data.put("departureI", flightPlan.getDepartureId() != null ? flightPlan.getDepartureId().getId() : null);
        data.put("departureName", flightPlan.getDepartureId() != null ? flightPlan.getDepartureId().getName() : null);
        data.put("destinationI", flightPlan.getDestinationId() != null ? flightPlan.getDestinationId().getId() : null);
        data.put("destinationName", flightPlan.getDestinationId() != null ? flightPlan.getDestinationId().getName() : null);
        data.put("takeoffTime", flightPlan.getTakeoffTime());
        data.put("landingTime", flightPlan.getLandingTime());
        data.put("actualTakeoffTime", flightPlan.getActualTakeoffTime());
        data.put("actualLandingTime", flightPlan.getActualLandingTime());
        data.put("flyingTime", flightPlan.getFlyingTime());
        data.put("typePlan", flightPlan.getType());
        return data;
    }

    /**
     * 编辑航班计划
     */
    @RequestMapping(value = "/editPlan", method = RequestMethod.GET)
    public
    @ResponseBody
    Message editPlan(Long flightPlanId, Long departureI, Long destinationI, Long takeoffTime, Long landingTime, Date actualTakeoffTime, Date actualLandingTime, FlightPlan.Type type,BigDecimal flyingTime, ModelMap model) {
        model.addAttribute("flightPlan", flightPlanService.find(flightPlanId));
        FlightPlan flightPlan1 = flightPlanService.find(flightPlanId);
        Airport departure = airportService.find(departureI);
        Airport destination = airportService.find(destinationI);
        Airplane airplane = flightPlanService.find(flightPlanId).getAirplane();
        SimpleDateFormat format;
        Date timetakeoff = null;
        Date timelanding = null;
        //当飞机计划为调机时，保存开始时间到结束时间总天数条调机
        if (type != null && type == FlightPlan.Type.tuning) {
            format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                timetakeoff = format.parse(format.format(takeoffTime));
                timelanding = format.parse(format.format(landingTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            List<Date> lDate = findDates(timetakeoff, timelanding);
            List<Date> tuningDate = flightPlanService.findByTakeoff(flightPlan1.getTuningDate());
            try {
                tuningDate.add(format.parse(format.format(flightPlan1.getTakeoffTime())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            lDate.removeAll(tuningDate);
            if (lDate != null && !lDate.isEmpty()) {
                for (Date date : lDate) {
                    FlightPlan flightPlan = new FlightPlan();
                    flightPlan.setTakeoffTime(date);
                    flightPlan.setLandingTime(timelanding);
                    flightPlan.setActualTakeoffTime(actualTakeoffTime);
                    flightPlan.setActualLandingTime(actualLandingTime);
                    flightPlan.setDestination(destination.getName());
                    flightPlan.setDeparture(departure.getName());
                    flightPlan.setAirplane(airplane);
                    flightPlan.setDepartureId(departure);
                    flightPlan.setDestinationId(destination);
                    flightPlan.setRegNo(airplane != null ? airplane.getRegNo() : null);
                    flightPlan.setAirplaneType(airplane != null ? airplane.getType() : null);
                    flightPlan.setCompany(airplane != null ? airplane.getCompany() : null);
                    flightPlan.setStatus(FlightPlan.FlightPlanStatus.userhidden);
                    flightPlan.setFlyingTime(flyingTime);
                    flightPlan.setTuningDate(format.format(takeoffTime) + format.format(landingTime));
                    flightPlan.setIsReal(false);
                    flightPlan.setType(type);
                    flightPlanService.price(flightPlan);
                    flightPlanService.save(flightPlan);
                }

            }
            flightPlan1.setTakeoffTime(timetakeoff);
            flightPlan1.setActualTakeoffTime(actualTakeoffTime);
            flightPlan1.setActualLandingTime(actualLandingTime);
            flightPlan1.setAirplane(airplane);
            flightPlan1.setDepartureId(departure);
            flightPlan1.setDestinationId(destination);
            flightPlan1.setRegNo(airplane != null ? airplane.getRegNo() : null);
            flightPlan1.setAirplaneType(airplane != null ? airplane.getType() : null);
            flightPlan1.setCompany(airplane != null ? airplane.getCompany() : null);
            flightPlan1.setStatus(FlightPlan.FlightPlanStatus.userhidden);
            flightPlan1.setFlyingTime(flyingTime);
            flightPlan1.setTuningDate(format.format(takeoffTime) + format.format(landingTime));
            flightPlan1.setIsReal(false);
            flightPlan1.setType(type);
            flightPlanService.update(flightPlan1);
            return SUCCESS_MESSAGE;
        } else {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        try {
            timelanding = format.parse(format.format(landingTime));
            timetakeoff = format.parse(format.format(takeoffTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        FlightPlan flightPlan = new FlightPlan();
        Calendar calendar = DateUtils.toCalendar(timelanding);
        if (type == FlightPlan.Type.carry ) {
            flightPlan1.setDestination(destination.getName());
            flightPlan1.setDeparture(departure.getName());
            flightPlan1.setLandingTime(timelanding);
        } else {
            calendar.add(Calendar.HOUR_OF_DAY, 24);
            calendar.add(Calendar.SECOND, -1);
            flightPlan1.setLandingTime(calendar.getTime());
        }
        flightPlan1.setTakeoffTime(timetakeoff);
        flightPlan1.setActualTakeoffTime(actualTakeoffTime);
        flightPlan1.setActualLandingTime(actualLandingTime);
        flightPlan1.setAirplane(airplane);
        flightPlan1.setDepartureId(departure);
        flightPlan1.setDestinationId(destination);
        flightPlan1.setRegNo(airplane != null ? airplane.getRegNo() : null);
        flightPlan1.setAirplaneType(airplane != null ? airplane.getType() : null);
        flightPlan1.setCompany(airplane != null ? airplane.getCompany() : null);
        flightPlan1.setStatus(FlightPlan.FlightPlanStatus.userhidden);
        flightPlan1.setFlyingTime(flyingTime);
        flightPlan1.setTuningDate(format.format(takeoffTime) + format.format(landingTime));
        flightPlan1.setIsReal(false);
        flightPlan1.setType(type);
        if (!isValid(flightPlan1)) {
            return ERROR_MESSAGE;
        }
        flightPlanService.price(flightPlan1);
        flightPlanService.update(flightPlan1);
        return SUCCESS_MESSAGE;
    }

    /**
     * 修改调机状态
     */
    @RequestMapping(value = "/updateTuning", method = RequestMethod.GET)
    public @ResponseBody
    Message updateTuning(Long id) {
        flightPlanService.updateIsReal(id, true);
        FlightPlan flightPlan = flightPlanService.find(id);
        List<FlightPlan> flightPlanList = flightPlanService.findByTuningDate(flightPlan.getTuningDate(), flightPlan.getAirplane());
        flightPlanList.remove(flightPlanService.find(id));
        for (FlightPlan flight : flightPlanList) {
            flightPlanService.delete(flight.getId());
        }
        return SUCCESS_MESSAGE;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    Message delete(Long id) {
        flightPlanService.delete(id);
        return SUCCESS_MESSAGE;
    }

    /**
     * 编辑特价航段
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Long id, ModelMap model) {
        model.addAttribute("flightPlan", flightPlanService.find(id));
        return "/admin/flight_plan/specialEdit";
    }


    /**
     * 更新特价航段
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(FlightPlan flightPlan, RedirectAttributes redirectAttributes) {
        FlightPlan tempFlightPlan = flightPlanService.find(flightPlan.getId());
        tempFlightPlan.setOriginalPrice(flightPlan.getOriginalPrice());
        tempFlightPlan.setSpecialprice(flightPlan.getSpecialprice());
        if (!isValid(tempFlightPlan)) {
            return ERROR_VIEW;
        }

        flightPlanService.update(tempFlightPlan);
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:specialList.jhtml";
    }


    /**
     * 更改状态
     */
    @RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
    public @ResponseBody
    Message changeStatus(Long id, String status) {
        FlightPlan flightPlan = flightPlanService.find(id);
        if ("forbidden".equals(status)) {
            flightPlan.setStatus(FlightPlan.FlightPlanStatus.userhidden);
        } else if ("activate".equals(status)) {
            flightPlan.setStatus(FlightPlan.FlightPlanStatus.usershow);
        }
        flightPlanService.update(flightPlan);
        return SUCCESS_MESSAGE;
    }

    /**
     * 列表页面 完成 接口
     */
    @RequestMapping(value = "/fulfill", method = RequestMethod.GET)
    public @ResponseBody
    Message fulfill(Long flightPlanId, Long departureI, Long destinationI, Date actualTakeoffTime, Date actualLandingTime) {
        Airport departure = airportService.find(departureI);
        Airport destination = airportService.find(destinationI);
        flightPlanService.updateDulfill(flightPlanId, departure, destination, actualTakeoffTime, actualLandingTime);
        return SUCCESS_MESSAGE;
    }

}
