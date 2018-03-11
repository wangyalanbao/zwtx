package com.hxlm.health.web.controller.admin;

import com.hxlm.health.web.*;
import com.hxlm.health.web.Message;
import com.hxlm.health.web.Order;
import com.hxlm.health.web.entity.*;
import com.hxlm.health.web.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by zhangyongrun on 15/12/18.
 */

@Controller("adminQuoteController")
@RequestMapping("/admin/quote")
public class QuoteController extends BaseController {

    @Resource(name = "quoteServiceImpl")
    private QuoteService quoteService;
    @Resource(name = "airplaneServiceImpl")
    private AirplaneService airplaneService;
    @Resource(name = "airportServiceImpl")
    private AirportService airportService;
    @Resource(name = "adminServiceImpl")
    private AdminService adminService;
    @Resource(name = "airlineServiceImpl")
    private AirlineService airlineService;
    @Resource(name = "snServiceImpl")
    private SnService snService;
    @Resource(name = "capacityServiceImpl")
    private CapacityService capacityService;
    @Resource(name = "companyServiceImpl")
    private CompanyService companyService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Long airplaneId, Quote.Status status,  Pageable pageable, ModelMap model){
        Admin admin = adminService.getCurrent();
        model.addAttribute("airplaneId", airplaneId);
        model.addAttribute("status", status);
        List<Filter> filterList = new ArrayList<Filter>();
        if(admin.getCompany() != null){
            Filter filter = new Filter();
            filter.setProperty("company");
            filter.setValue(admin.getCompany());
            filter.setOperator(Filter.Operator.eq);
            filterList.add(filter);
        }
        model.addAttribute("airplaneList", airplaneService.findList(null, filterList, null));
        if(airplaneId != null){
            Filter filter = new Filter();
            filter.setProperty("airplaneId");
            filter.setValue(airplaneId);
            filter.setOperator(Filter.Operator.eq);
            filterList.add(filter);
        }
        if(status != null){
            Filter filter = new Filter();
            filter.setProperty("status");
            filter.setValue(status);
            filter.setOperator(Filter.Operator.eq);
            filterList.add(filter);
        }
        pageable.setFilters(filterList);
        pageable.setOrderProperty("createDate");
        pageable.setOrderDirection(Order.Direction.desc);
        Page<Quote> quotePage = quoteService.findPage(pageable);
        model.addAttribute("page", quotePage);
        return "/admin/quote/list";
    }

    /**
     * 保存报价
     */
    @RequestMapping(value = "/saveQuote",method = RequestMethod.POST)
    public String saveQuote(Quote quote, Integer[] select){
        Admin admin = adminService.getCurrent();
        List<Quote> quoteList = quote.getQuotes();
        for(Integer num:select){
            Quote saveQuote = quoteList.get(num);

            saveQuote.setDeparture(airportService.find(saveQuote.getDepartureId()));
            saveQuote.setDestination(airportService.find(saveQuote.getDestinationId()));
            saveQuote.setAirplane(airplaneService.find(saveQuote.getAirplaneId()));

            saveQuote.setStatus(Quote.Status.unconfirmed);
            if(saveQuote.getCustomAmount() != null){
                saveQuote.setTotalAmount(saveQuote.getCustomAmount());
            }
            List<QuoteAirline> quoteAirlineList = saveQuote.getQuoteAirlineList();
            Iterator<QuoteAirline> iterator = quoteAirlineList.iterator();
            while (iterator.hasNext()){
                QuoteAirline quoteAirline = iterator.next();
                if(quoteAirline.getIsDelete() == true){
                    iterator.remove();
                } else {
                    quoteAirline.setDeparture(airportService.find(quoteAirline.getDepartureId()));
                    quoteAirline.setDestination(airportService.find(quoteAirline.getDestinationId()));
                    quoteAirline.setCenter(airportService.find(quoteAirline.getCenterId()));
                    quoteAirline.setQuote(saveQuote);
                    if(quoteAirline.getIsSpecial() == null){
                        quoteAirline.setIsSpecial(false);
                    }
                }
            }
            saveQuote.setQuoteAirlineList(quoteAirlineList);
            saveQuote.setSn(snService.generate(Sn.Type.quote));
            saveQuote.setCompany(admin.getCompany());
            quoteService.save(saveQuote);

            // 生成运力确认
            Capacity capacity = new Capacity();
            capacity.setQuote(saveQuote);
            capacity.setQuoteId(saveQuote.getId());
            capacityService.save(capacity);
        }

        return "redirect:list.jhtml";
    }

    /**
     * 重新计算前后调机
     * @param airplaneId
     * @param departureId
     * @param destinationId
     * @param takeoffTime
     * @return
     */
    @RequestMapping(value = "/oneLineCost",method = RequestMethod.GET)
    public  @ResponseBody
    Map<String, String> oneLineCost(Long airplaneId, Long departureId, Long destinationId, Date takeoffTime){
        QuoteAirline quoteAirline = new QuoteAirline();
        Airplane airplane = airplaneService.find(airplaneId);
        Airport departure = airportService.find(departureId);
        Airport destination = airportService.find(destinationId);
        Calendar takeoffCalendar = Calendar.getInstance();
        takeoffCalendar.setTime(takeoffTime);
        Float v_tmp_hour = airlineService.lineHour(departure, destination, null, airplane.getTypeId(), takeoffCalendar.get(Calendar.MONTH), Double.valueOf(airplane.getCruisingSpeed().toString()));
        DecimalFormat df = new DecimalFormat("##0.00");
        quoteAirline.setDeparture(departure);
        quoteAirline.setDepartureId(departureId);
        quoteAirline.setDestination(destination);
        quoteAirline.setDestinationId(destinationId);
        quoteAirline.setTakeoffTime(takeoffTime);
        quoteAirline.setTimeCost(Float.valueOf(df.format(v_tmp_hour)));
        quoteAirline.setIsSpecial(true);
        quoteAirline.setSpecialType(QuoteAirline.SpecialType.before);
        Quote quote = new Quote();
        quote.setAirplane(airplane);
        quoteAirline.setQuote(quote);

        Map<String, String> map = new HashMap<String, String>();
        map.put("timeCost", quoteAirline.getTimeCost().toString());
        map.put("departureId",departureId.toString());
        map.put("destinationId", destinationId.toString());
        map.put("maintenanceCost", quoteAirline.getMaintenanceCost().toString());
        map.put("flyingCost", quoteAirline.getFlyingCost().toString());

        return map;
    }

    /**
     * 报价
     */
    @RequestMapping(value = "/quote",method = RequestMethod.GET)
    public String quote(Long[] airplaneIds, Quote quote, ModelMap model) {
        List<Quote> quotes = new ArrayList<Quote>();
        List<QuoteAirline> quoteAirlineList = quote.getQuoteAirlineList();
        for(QuoteAirline quoteAirline : quoteAirlineList){
            quoteAirline.setDeparture(airportService.find(quoteAirline.getDepartureId()));
            quoteAirline.setDestination(airportService.find(quoteAirline.getDestinationId()));
        }
        quote.setDepartureId(quoteAirlineList.get(0).getDepartureId());
        quote.setDeparture(quoteAirlineList.get(0).getDeparture());
        quote.setDestinationId(quoteAirlineList.get(quoteAirlineList.size()-1).getDestinationId());
        quote.setDestination(quoteAirlineList.get(quoteAirlineList.size()-1).getDestination());
        quote.setTakeoffTime(quoteAirlineList.get(0).getTakeoffTime());
        for (Long planeId : airplaneIds){
            Airplane airplane = airplaneService.find(planeId);
            Quote tempQuote = new Quote();
            tempQuote.setPlanType(quote.getPlanType());
            tempQuote.setDepartureId(quote.getDepartureId());
            tempQuote.setDeparture(quote.getDeparture());
            tempQuote.setDestinationId(quote.getDestinationId());
            tempQuote.setDestination(quote.getDestination());
            tempQuote.setQuoteAirlineList(quoteAirlineList);
            tempQuote.setTakeoffTime(quote.getTakeoffTime());
            tempQuote.setRegNo(airplane.getRegNo());
            tempQuote.setType(airplane.getType());
            tempQuote.setAirplane(airplane);
            tempQuote.setAirplaneId(planeId);
            tempQuote.setStatus(Quote.Status.unconfirmed);

            tempQuote = quoteService.price(tempQuote);
            quotes.add(tempQuote);
        }

        Comparator<Quote> quoteComparator = new Comparator<Quote>() {
            @Override
            public int compare(Quote quote1, Quote quote2) {
                return quote1.getTotalAmount().compareTo(quote2.getTotalAmount());
            }
        };
        Collections.sort(quotes, quoteComparator);
        model.addAttribute("quotes", quotes);
        model.addAttribute("quote", quote);

        return "/admin/quote/quoteList";
    }


    /**
     * 删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public @ResponseBody
    Message delete(Long[] ids) {
        quoteService.delete(ids);
        return SUCCESS_MESSAGE;
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(Long companyId, String regNo, String type, ModelMap model){

        model.addAttribute("companyId", companyId);
        model.addAttribute("regNo", regNo);
        model.addAttribute("type", type);
        Admin admin = adminService.getCurrent();
        Company company = companyService.find(companyId);
        if(company == null){
            company = admin.getCompany();
        }
        List<Filter> filterList = new ArrayList<Filter>();
        if(company != null){
            Filter filter = new Filter();
            filter.setProperty("company");
            filter.setValue(company);
            filter.setOperator(Filter.Operator.eq);
            filterList.add(filter);
        }
        if(StringUtils.isNotEmpty(regNo)){
            Filter filter = new Filter();
            filter.setProperty("regNo");
            filter.setValue(regNo);
            filter.setOperator(Filter.Operator.eq);
            filterList.add(filter);
        }
        if(StringUtils.isNotEmpty(type)){
            Filter filter = new Filter();
            filter.setProperty("type");
            filter.setValue(type);
            filter.setOperator(Filter.Operator.eq);
            filterList.add(filter);
        }
        List<Company> companyList = new ArrayList<Company>();
        if(admin.getCompany() == null){
            model.addAttribute("companyList", companyService.findAll());
        } else {
            companyList.add(admin.getCompany());
            model.addAttribute("companyList", companyList);
        }
        List<Airplane> airplaneList = airplaneService.findList(null, filterList, null);
        model.addAttribute("airplaneList", airplaneList);
        model.addAttribute("airplaneNum", airplaneList.size());

        return "/admin/quote/airplanList";
    }
    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(RedirectAttributes redirectAttributes){

        return "redirect:list.jhtml";

    }

    /**
     * 查看
     */
    @RequestMapping(value = "/view",method = RequestMethod.GET)
    public String view(Long id,ModelMap model){
        Quote quote = quoteService.find(id);
        model.addAttribute("quote", quote);
        return "/admin/quote/view";
    }



    /**
     * 运力确认
     */
    @RequestMapping(value = "/confirmed",method = RequestMethod.GET)
    public String confirmed(Long id,ModelMap model){
        Quote quote = quoteService.find(id);
        model.addAttribute("quote", quote);
        model.addAttribute("capacity", quote.getCapacity());
        return "/admin/quote/confirmed";
    }

    /**
     * 保存运力确认
     */
    @RequestMapping(value = "/capacity",method = RequestMethod.POST)
    public String capacity(Capacity capacity,  ModelMap model){
        if(capacity.getBase() == null){
            capacity.setBase(false);
        }
        if(capacity.getFlightMission() == null){
            capacity.setFlightMission(false);
        }
        if(capacity.getIntelligenceMat() == null){
            capacity.setIntelligenceMat(false);
        }
        if(capacity.getMachineMat() == null){
            capacity.setMachineMat(false);
        }
        if(capacity.getFlightAttendantMat() == null){
            capacity.setFlightAttendantMat(false);
        }
        if(capacity.getMCC() == null){
            capacity.setMCC(false);
        }
        if(capacity.getServiceGuaranteeMat() == null){
            capacity.setServiceGuaranteeMat(false);
        }
        if(capacity.getWorkingCapitalMat() == null){
            capacity.setWorkingCapitalMat(false);
        }
        if(capacity.getSecurityManagementCenter() == null){
            capacity.setSecurityManagementCenter(false);
        }
        if(capacity.getCatering() == null){
            capacity.setCatering(false);
        }
        if(capacity.getHistoricalArgument() == null){
            capacity.setHistoricalArgument(false);
        }
        if(capacity.getIsCancelled() == null){
            capacity.setIsCancelled(false);
        }
        if(capacity.getForeignSupervisor() == null){
            capacity.setForeignSupervisor(false);
        }
        if(capacity.getOperationControl() == null){
            capacity.setOperationControl(false);
        }
        if(capacity.getFinanceDepartment() == null){
            capacity.setFinanceDepartment(false);
        }

        Quote quote = quoteService.find(capacity.getQuoteId());

        capacity.setQuote(quote);
        capacityService.update(capacity);
        if(capacity.getStatus() == Capacity.Status.confirmed){
            quote.setStatus(Quote.Status.confirmed);
        } else if(capacity.getStatus() == Capacity.Status.confirming){
            quote.setStatus(Quote.Status.confirming);
        } else if(capacity.getStatus() == Capacity.Status.unconfirmed){
            quote.setStatus(Quote.Status.unconfirmed);
        } else if(capacity.getStatus() == Capacity.Status.cancelled){
            quote.setStatus(Quote.Status.cancelled);
        }

        quoteService.update(quote);

        return "redirect:list.jhtml";
    }

    /**
     * 撤销报价
     */
    @RequestMapping(value = "cancelled",method = RequestMethod.GET)
    public String cancelled(Long  quoteId){
        Quote quote = quoteService.find(quoteId);
        quote.setStatus(Quote.Status.cancelled);
        quoteService.update(quote);
        Capacity capacity = quote.getCapacity();
        capacity.setIsCancelled(true);
        capacityService.update(capacity);
        return "redirect:list.jhtml";
    }



}
