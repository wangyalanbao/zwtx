package com.hxlm.health.web.controller.admin;

import com.hxlm.health.web.*;
import com.hxlm.health.web.Message;
import com.hxlm.health.web.entity.*;
import com.hxlm.health.web.entity.Order;
import com.hxlm.health.web.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by guofeng on 2016/1/28.
 * Controller -- 行线 --
 */
@Controller("adminOrderAirlineController")
@RequestMapping("/admin/order_airline")
public class OrderAirlineController extends BaseController {

    @Resource(name = "orderAirlineServiceImpl")
    private OrderAirlineService orderAirlineService;
    @Resource(name = "orderPassengerServiceImpl")
    private OrderPassengerService orderPassengerService;
    @Resource(name = "orderCateringServiceImpl")
    private OrderCateringService orderCateringService;
    @Resource(name = "orderPickupServiceImpl")
    private OrderPickupService orderPickupService;
    @Resource(name = "orderServiceImpl")
    private OrderService orderService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Long orderId, Pageable pageable, ModelMap model) {
        Order order = orderService.find(orderId);
        model.addAttribute("orderAll", orderService.findAll());
        model.addAttribute("page", orderAirlineService.findPage(order, pageable));
        return "/admin/order_airline/list";
    }

    /**
     * 添加乘客信息
     */
    @RequestMapping(value = "/addPassenger", method = RequestMethod.GET)
    public String addPassenger(Long airlineId, ModelMap model) {
        model.addAttribute("airlineId", airlineId);
        model.addAttribute("airline", orderAirlineService.find(airlineId));
        return "/admin/order_airline/addPassenger";
    }

    /**
     * 保存乘客
     */
    @RequestMapping(value = "/savePassenger", method = RequestMethod.POST)
    public String save(Long[] passengersIds, Long airlineId, RedirectAttributes redirectAttributes) {
        OrderAirline orderAirline = orderAirlineService.find(airlineId);
        List<OrderPassenger> orderPassengerList = orderPassengerService.findList(passengersIds);
        List<OrderPassenger> airlinePassengers = orderAirline.getOrderPassengers();
        if (airlinePassengers == null && airlinePassengers.isEmpty() && orderPassengerList != null && !orderPassengerList.isEmpty()) {
            for (OrderPassenger passenger : orderPassengerList) {
                orderPassengerService.updateOrder(passenger.getId(), orderAirline, orderAirline.getTripOrder());
            }
        } else {
            for (OrderPassenger passenger : orderPassengerList) {
                orderPassengerService.updateOrder(passenger.getId(), orderAirline, orderAirline.getTripOrder());
            }
            for (OrderPassenger passenger : airlinePassengers) {
                if (!orderPassengerList.contains(passenger)) {
                    passenger.setOrderAirline(null);
                    passenger.setTripOrder(null);
                    orderPassengerService.updateOrder(passenger.getId(), null, null);
                }
            }
        }
        orderAirlineService.update(orderAirline);
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:addPassenger.jhtml?airlineId=" + airlineId;

    }

    /**
     * 查询乘客
     */
    @RequestMapping(value = "/passenger_select", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Map<String, Object>> messageSelect(String q) {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        //如果关键词不为空
        if (StringUtils.isNotEmpty(q)) {
            List<OrderPassenger> orderPassengers = orderPassengerService.searth(q, 5);
            for (OrderPassenger passenger : orderPassengers) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", passenger.getId());
                map.put("name", passenger.getName());
                map.put("sex", passenger.getSex());
                map.put("nationality", passenger.getNationality());
                map.put("birth", passenger.getBirth());
                map.put("identityType", passenger.getIdentityType());
                map.put("idCardNo", passenger.getIdCardNo());
                map.put("identityExpiryEnd", passenger.getIdentityExpiryEnd());
                data.add(map);
            }

        }
        return data;
    }

    /**
     * 添加行李配餐要求
     */
    @ResponseBody
    @RequestMapping(value = "/addCatering", method = RequestMethod.GET)
    public com.hxlm.health.web.Message save(OrderCatering orderCatering, Long airlineId) {
        orderCatering.setTripOrder(orderAirlineService.find(airlineId).getTripOrder());
        orderCatering.setOrderAirline(orderAirlineService.find(airlineId));
        ;

        if (!isValid(orderCatering)) {
            return ERROR_MESSAGE;
        }
        orderCateringService.save(orderCatering);
        return SUCCESS_MESSAGE;
    }

    /**
     * 编辑行李配餐要求
     */
    @RequestMapping(value = "/editCatering", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> editCatering(Long orderCateringId) {
        Map<String, Object> data = new HashMap<String, Object>();
        OrderCatering orderCatering = orderCateringService.find(orderCateringId);
        data.put("orderCatering", orderCatering);
        data.put("orderCateringId", orderCateringId);
        data.put("luggageRequest", orderCatering.getLuggageRequest());
        data.put("drinkRequest", orderCatering.getDrinkRequest());
        data.put("foodRequest", orderCatering.getDrinkRequest());
        data.put("otherRequest", orderCatering.getOtherRequest());
        return data;
    }

    /**
     * 更新
     */
    @ResponseBody
    @RequestMapping(value = "updateCatering", method = RequestMethod.GET)
    public Message update(Long orderPickupId, String luggageRequest, String drinkRequest, String foodRequest, String otherRequest) {
        orderCateringService.updateCatering(orderPickupId, luggageRequest, drinkRequest, foodRequest, otherRequest);
        return SUCCESS_MESSAGE;
    }

    /**
     * 订单接送人
     */
    @ResponseBody
    @RequestMapping(value = "/addPickup", method = RequestMethod.GET)
    public com.hxlm.health.web.Message save(OrderPickup orderPickup, Long airlineId) {
        OrderAirline orderAirline = orderAirlineService.find(airlineId);
        Order order = orderAirlineService.find(airlineId).getTripOrder();
        orderPickup.setOrderAirline(orderAirline);
        orderPickup.setTripOrder(order);
        if (!isValid(orderPickup)) {
            return ERROR_MESSAGE;
        }
        orderPickupService.save(orderPickup);
        return SUCCESS_MESSAGE;
    }

    /**
     * 编辑行李配餐要求
     */
    @ResponseBody
    @RequestMapping(value = "/editPickup", method = RequestMethod.GET)
    public Map<String, Object> editPickup(Long orderPickupId) {
        Map<String, Object> data = new HashMap<String, Object>();
        OrderPickup orderPickup = orderPickupService.find(orderPickupId);
        data.put("orderPickup", orderPickup);
        data.put("orderPickupId", orderPickupId);
        data.put("site", orderPickup.getSite());
        data.put("name", orderPickup.getName());
        data.put("contact", orderPickup.getContact());
        data.put("carNo", orderPickup.getCarNo());
        return data;
    }

    /**
     * 更新
     */
    @ResponseBody
    @RequestMapping(value = "updatePickup", method = RequestMethod.GET)
    public Message updatePickup(Long orderPickupId, String site, String name, String contact, String carNo) {
        orderPickupService.updatePickup(orderPickupId, site, name, contact, carNo);
        return SUCCESS_MESSAGE;
    }

    /**
     * 客户信息导出 "证件号码 ",
     */
    @RequestMapping(value = "/airlineExport", method = RequestMethod.GET)
    public ModelAndView airlineExport(Long airlineId,HttpServletResponse response,HttpServletRequest request) {

        OrderAirline orderAirline = orderAirlineService.find(airlineId);
        String filename = "";
        if (orderAirline.getTripOrder() !=null){

            filename ="订单"+orderAirline.getTripOrder().getSn()+ "乘客信息表";
        }else {
            filename = "乘客信息表";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        filename = filename + sdf.format(new Date()) + ".xls";

        try {
            // 下载对应的模版
//            URL url = new URL("http://localhost:8080/upload/file/201602/0217/bd6dd739-2598-458e-8976-8e6c87b414c2.xls");
//            BufferedInputStream input = new BufferedInputStream(url.openStream());
//            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(input);
            InputStream is = new FileInputStream(request.getSession().getServletContext().getRealPath("/") + "upload" + File.separator + "mould" + File.separator  + "passenger.xls");
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            hssfWorkbook.setSheetName(0, (orderAirline.getDeparture()!=null ? orderAirline.getDeparture() : "")+"-->"+(orderAirline.getDestination()!=null ? orderAirline.getDestination() : ""));
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
            //行
            HSSFRow row = sheet.getRow(1);
            //列
            HSSFCell cell = row.getCell(2);
            cell.setCellValue(orderAirline.getTripOrder() !=null ? (orderAirline.getTripOrder().getCustomerId() !=null ? orderAirline.getTripOrder().getCustomerId().getRealName() : "") : "");

            cell = row.getCell(7);
            cell.setCellValue(orderAirline.getTripOrder() !=null ? (orderAirline.getTripOrder().getCustomerId() !=null ? orderAirline.getTripOrder().getCustomerId().getTelephone() : "") : "");
            //行线，时间
            row = sheet.getRow(3);
            cell = row.getCell(2);
            if(orderAirline.getTakeoffTime()  !=null){
                cell.setCellValue(orderAirline.getTakeoffTime());
            }
            cell = row.getCell(5);
            cell.setCellValue((orderAirline.getDeparture()!=null ? orderAirline.getDeparture() : "")+"，至"+(orderAirline.getDestination()!=null ? orderAirline.getDestination() : ""));
            //乘客
            if(orderAirline.getOrderPassengers() != null && orderAirline.getOrderPassengers().size() > 0){
                for (int i = 0; i < orderAirline.getOrderPassengers().size(); i++) {
                    OrderPassenger passenger = orderAirline.getOrderPassengers().get(i);
                    row = sheet.getRow(8 + i);
                    cell = row.getCell(0);
                    cell.setCellValue(i + 1);
                    cell = row.getCell(1);
                    cell.setCellValue(passenger.getName() !=null ? passenger.getName() : "");
                    cell = row.getCell(2);
                    if(passenger.getSex() != null){
                        if(OrderPassenger.Sex.male == passenger.getSex()){
                            cell.setCellValue("男");
                        }else {
                            cell.setCellValue("女");
                        }
                    }else {
                        cell.setCellValue("");
                    }
                    cell = row.getCell(3);
                    cell.setCellValue(passenger.getNationality() !=null ? passenger.getNationality() : "");
                    cell = row.getCell(4);
                    if(passenger.getBirth() !=null){
                        cell.setCellValue(passenger.getBirth());
                    }
                    cell = row.getCell(5);
                    if(passenger.getIdentityType() != null){
                        if (OrderPassenger.IdentityTypes.idCard == passenger.getIdentityType()){
                            cell.setCellValue("身份证");
                        }else if (OrderPassenger.IdentityTypes.passport == passenger.getIdentityType()){
                            cell.setCellValue("护照");
                        }else if (OrderPassenger.IdentityTypes.officerCard == passenger.getIdentityType()){
                            cell.setCellValue("港澳通行证");
                        }else if (OrderPassenger.IdentityTypes.homeCard == passenger.getIdentityType()){
                            cell.setCellValue("回乡证");
                        }else if (OrderPassenger.IdentityTypes.elseCard == passenger.getIdentityType()){
                            cell.setCellValue("其他");
                        }
                    }else {
                        cell.setCellValue("");
                    }

                    cell = row.getCell(6);
                    if (passenger.getIdCardNo() !=null){
                        cell.setCellValue(passenger.getIdCardNo());
                    }

                    cell = row.getCell(8);
                    if (passenger.getIdentityExpiryEnd() !=null){

                        cell.setCellValue(passenger.getIdentityExpiryEnd());
                    }

                }
            }

            //行李和配餐要求
            row = sheet.getRow(40);
            cell = row.getCell(3);
            cell.setCellValue(orderAirline.getOrderCatering() !=null ? (orderAirline.getOrderCatering().getLuggageRequest() !=null ? orderAirline.getOrderCatering().getLuggageRequest() : "") : "");
            row = sheet.getRow(42);
            cell = row.getCell(3);
            cell.setCellValue(orderAirline.getOrderCatering() !=null ? (orderAirline.getOrderCatering().getFoodRequest() !=null ? orderAirline.getOrderCatering().getFoodRequest() : "") : "");
            row = sheet.getRow(44);
            cell = row.getCell(3);
            cell.setCellValue(orderAirline.getOrderCatering() !=null ? (orderAirline.getOrderCatering().getDrinkRequest() !=null ? orderAirline.getOrderCatering().getDrinkRequest() : "") : "");
            row = sheet.getRow(46);
            cell = row.getCell(3);
            cell.setCellValue(orderAirline.getOrderCatering() !=null ? (orderAirline.getOrderCatering().getOtherRequest() !=null ? orderAirline.getOrderCatering().getOtherRequest() : "") : "");
             //接送人信息
            row = sheet.getRow(52);
            cell = row.getCell(0);
            cell.setCellValue(orderAirline.getOrderPickup() !=null ? (orderAirline.getOrderPickup().getSite() !=null ? orderAirline.getOrderPickup().getSite() : "") : "");
            cell = row.getCell(4);
            cell.setCellValue(orderAirline.getOrderPickup() !=null ? (orderAirline.getOrderPickup().getName() !=null ? orderAirline.getOrderPickup().getName() : "") : "");
            cell = row.getCell(6);
            cell.setCellValue(orderAirline.getOrderPickup() !=null ? (orderAirline.getOrderPickup().getContact() !=null ? orderAirline.getOrderPickup().getContact() : "") : "");
            cell = row.getCell(7);
            cell.setCellValue(orderAirline.getOrderPickup() !=null ? (orderAirline.getOrderPickup().getCarNo() !=null ? orderAirline.getOrderPickup().getCarNo() : "") : "");


            response.setContentType("application/force-download");
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));

            ServletOutputStream out = response.getOutputStream();
            hssfWorkbook.write(out);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
