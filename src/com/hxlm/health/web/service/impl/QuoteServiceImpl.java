/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.DateEditor;
import com.hxlm.health.web.dao.QuoteDao;
import com.hxlm.health.web.entity.Airplane;
import com.hxlm.health.web.entity.Airport;
import com.hxlm.health.web.entity.Quote;
import com.hxlm.health.web.entity.QuoteAirline;
import com.hxlm.health.web.service.AirlineService;
import com.hxlm.health.web.service.AirportService;
import com.hxlm.health.web.service.CommonService;
import com.hxlm.health.web.service.QuoteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Service - 报价
 * 
 * 
 * 
 */
@Service("quoteServiceImpl")
public class QuoteServiceImpl extends BaseServiceImpl<Quote, Long> implements QuoteService {

	@Resource(name = "quoteDaoImpl")
	public void setBaseDao(QuoteDao quoteDao) {
		super.setBaseDao(quoteDao);
	}

	@Resource(name = "commonServiceImpl")
	private CommonService commonService;
	@Resource(name = "airportServiceImpl")
	private AirportService airportService;
	@Resource(name = "airlineServiceImpl")
	private AirlineService airlineService;


	/**
	 * 报价
	 * @param quote
	 * @return
	 */
	public Quote price(Quote quote){

		// 总费用
		BigDecimal totalAmount = new BigDecimal(0);
		// 总时间
		Double v_total_hour = 0d;
		// 国内空载时间
		Double int_empty_hour = 0d;
		// 国内载客时间
		Double int_loaded_hour = 0d;
		// 国外空载时间
		Double ext_empty_hour = 0d;
		// 国外载客时间
		Double ext_loaded_hour = 0d;
		BigDecimal totalMaintenance = new BigDecimal(0);
		// 调正天数
		int  stopDay = 0;
		// 上一个航段的飞行时长
		float beforeLoadTime = 0f;

		// 飞机
		Airplane airplane = quote.getAirplane();

		// 飞机的虚拟基地
		Set<Airport> airportList = airplane.getAirports();

		// 任务航段
		List<QuoteAirline> quoteAirlineList = quote.getQuoteAirlineList();

		List<QuoteAirline> quoteAirlineLinkedList = new LinkedList<QuoteAirline>();

		for(int i=0;i<quoteAirlineList.size();i++) {
			Float v_tmp_hour = 0f;
			Float v_tmp_hour2 = 0f;
			Float v_tmp_hour3 = 0f;
			Float v_tmp_hour4 = 0f;
			// 任务航段
			QuoteAirline quoteAirline = quoteAirlineList.get(i);
			quoteAirline.setQuote(quote);
			Airport start = quoteAirline.getDeparture();
			Airport end = quoteAirline.getDestination();
			// 起飞时间
			Date date =  quoteAirline.getTakeoffTime();
			Calendar takeoffCalendar = Calendar.getInstance();
			takeoffCalendar.setTime(date);
			// 始发机场不是虚拟基地
			if(!airportList.contains(quoteAirline.getDeparture())){
				// 距始发机场最近的虚拟基地
				Airport base = airportService.findNearestAirport(start, airportList);
				if(i == 0){ // 第一个航段
					// 将飞机从最近虚拟基地调到始发机场的飞行时间
					v_tmp_hour = airlineService.lineHour(base, start, null, airplane.getTypeId(), takeoffCalendar.get(Calendar.MONTH), Double.valueOf(airplane.getCruisingSpeed().toString()));
					QuoteAirline quoteAirline1 = new QuoteAirline();
					quoteAirline1.setQuote(quote);
					quoteAirline1.setIsSpecial(true);
					quoteAirline1.setDeparture(base);
					quoteAirline1.setDepartureId(base.getId());
					quoteAirline1.setDestination(start);
					quoteAirline1.setDestinationId(start.getId());
					quoteAirline1.setTimeCost(v_tmp_hour);
					quoteAirline1.setSpecialType(QuoteAirline.SpecialType.before);
					quoteAirline1.setTakeoffTime(takeoffCalendar.getTime());
					quoteAirline1.setPassengerNum(0);
					// 增加前调机
					quoteAirlineLinkedList.add(quoteAirline1);
					if(start.getLocations() == Airport.Location.external || base.getLocations() == Airport.Location.external){
						ext_empty_hour = ext_empty_hour + v_tmp_hour;
						v_total_hour = v_total_hour + v_tmp_hour;
					} else {
						int_empty_hour = int_empty_hour + v_tmp_hour;
						v_total_hour = v_total_hour + v_tmp_hour;
					}
					// 航段任务飞行时间
					v_tmp_hour3 = airlineService.lineHour(start, end, null, airplane.getTypeId(), takeoffCalendar.get(Calendar.MONTH), Double.valueOf(airplane.getCruisingSpeed().toString()));
					QuoteAirline quoteAirline2 = new QuoteAirline();
					quoteAirline2.setQuote(quote);
					quoteAirline2.setDeparture(start);
					quoteAirline2.setDepartureId(start.getId());
					quoteAirline2.setDestination(end);
					quoteAirline2.setDestinationId(end.getId());
					quoteAirline2.setTakeoffTime(quoteAirline.getTakeoffTime());
					quoteAirline2.setTimeCost(v_tmp_hour3);
					quoteAirline2.setIsSpecial(false);
					quoteAirline2.setPassengerNum(quoteAirline.getPassengerNum());
					// 增加任务航段
					quoteAirlineLinkedList.add(quoteAirline2);
					if(start.getLocations() == Airport.Location.external || end.getLocations() == Airport.Location.external){
						ext_loaded_hour = ext_loaded_hour + v_tmp_hour3;
						v_total_hour = v_total_hour + v_tmp_hour3;
					} else {
						int_loaded_hour = int_loaded_hour + v_tmp_hour3;
						v_total_hour = v_total_hour + v_tmp_hour3;
					}
					beforeLoadTime = v_tmp_hour3;
				} else { // 非第一个航段

					// 上一个航段起飞时间
					Date beforeTakeoffDate = quoteAirlineList.get(i-1).getTakeoffTime();
					// 降落时间
					Calendar loadCalendar = Calendar.getInstance();
					loadCalendar.setTime(beforeTakeoffDate);
					int minutes = (int)(beforeLoadTime*60);
					loadCalendar.add(Calendar.MINUTE, minutes);
					//停留天数
					int days = DateEditor.daysBetween(loadCalendar.getTime(), quoteAirline.getTakeoffTime()) -1 ;
					if(days < 0) {
						days = 0;
					}
					// 始发机场不等于上一航段的终点机场时，停留天数已经计算过，此处不在计算
					if(start != quoteAirlineList.get(i-1).getDestination()){
						days = 0;
					} else {
						// 调机月份
						Integer oldMonth = loadCalendar.get(Calendar.MONTH);
						// 将飞机从始发机场调回最近虚拟基地的飞行时间(这是第一步)
						v_tmp_hour2 = airlineService.lineHour(start, base, null, airplane.getTypeId(), oldMonth, Double.valueOf(airplane.getCruisingSpeed().toString()));
						// 将飞机从最近虚拟基地调到始发机场的飞行时间（这是第二步）
						v_tmp_hour = airlineService.lineHour(base, start, null, airplane.getTypeId(), takeoffCalendar.get(Calendar.MONTH), Double.valueOf(airplane.getCruisingSpeed().toString()));
						// 计算调机费用
						BigDecimal airlinePrice = new BigDecimal(0);
						if(start.getLocations() == Airport.Location.external || base.getLocations() == Airport.Location.external){
							airlinePrice = airplane.getExtEmptyPrice().multiply(new BigDecimal(v_tmp_hour2 + v_tmp_hour));
						} else {
							airlinePrice = airplane.getEmptyPrice().multiply(new BigDecimal(v_tmp_hour2 + v_tmp_hour));
						}
						// 加上 地面费用
						airlinePrice.add(base.getInlandCost().getTransferCost().multiply(new BigDecimal(2))).add(start.getInlandCost().getTransferCost().multiply(new BigDecimal(2)));
						// 不调机，最低小时飞行费用
						BigDecimal lowestCost = new BigDecimal(0);
						if(start.getLocations() == Airport.Location.external){
							lowestCost = airplane.getExtEmptyPrice().multiply(new BigDecimal(days * Float.valueOf(airplane.getMinimumHour())));
						} else {
							lowestCost = airplane.getEmptyPrice().multiply(new BigDecimal(days * Float.valueOf(airplane.getMinimumHour())));
						}
						// 如果调机费用小于最低小时飞行费用，进行调机
						if(airlinePrice.compareTo(lowestCost) < 0){
							QuoteAirline quoteAirline1 = new QuoteAirline();
							quoteAirline1.setQuote(quote);
							quoteAirline1.setIsSpecial(true);
							quoteAirline1.setDeparture(start);
							quoteAirline1.setDepartureId(start.getId());
							quoteAirline1.setCenter(base);
							quoteAirline1.setCenterId(base.getId());
							quoteAirline1.setDestination(start);
							quoteAirline1.setDestinationId(start.getId());
							quoteAirline1.setTimeCost(v_tmp_hour2 + v_tmp_hour);
							quoteAirline1.setSpecialType(QuoteAirline.SpecialType.succession);
							quoteAirline1.setPassengerNum(0);
							// 增补调机航段
							quoteAirlineLinkedList.add(quoteAirline1);

							if(start.getLocations() == Airport.Location.external || base.getLocations() == Airport.Location.external){
								ext_empty_hour = ext_empty_hour + v_tmp_hour2 + v_tmp_hour;
								v_total_hour = v_total_hour + v_tmp_hour2 + v_tmp_hour;
							} else {
								int_empty_hour = int_empty_hour + v_tmp_hour2 + v_tmp_hour;
								v_total_hour = v_total_hour + v_tmp_hour2 + v_tmp_hour;
							}
							// 增加调正天数
							stopDay = stopDay  + days;
						}
					}

					// 航段任务飞行时间
					v_tmp_hour3 = airlineService.lineHour(start, end, null, airplane.getTypeId(), takeoffCalendar.get(Calendar.MONTH), Double.valueOf(airplane.getCruisingSpeed().toString()));
					QuoteAirline quoteAirline3 = new QuoteAirline();
					quoteAirline3.setQuote(quote);
					quoteAirline3.setDeparture(start);
					quoteAirline3.setDepartureId(start.getId());
					quoteAirline3.setDestination(end);
					quoteAirline3.setDestinationId(end.getId());
					quoteAirline3.setTakeoffTime(quoteAirline.getTakeoffTime());
					quoteAirline3.setTimeCost(v_tmp_hour3);
					quoteAirline3.setIsSpecial(false);
					quoteAirline3.setPassengerNum(quoteAirline.getPassengerNum());
					// 增加任务航段
					quoteAirlineLinkedList.add(quoteAirline3);
					if(start.getLocations() == Airport.Location.external || end.getLocations() == Airport.Location.external){
						ext_loaded_hour = ext_loaded_hour + v_tmp_hour3;
						v_total_hour = v_total_hour + v_tmp_hour3;
					} else {
						int_loaded_hour = int_loaded_hour + v_tmp_hour3;
						v_total_hour = v_total_hour + v_tmp_hour3;
					}
					beforeLoadTime = v_tmp_hour3;
				}
			} else { // 始发机场是虚拟基地
				// 航段任务飞行时间
				v_tmp_hour3 = airlineService.lineHour(start, end, null, airplane.getTypeId(), takeoffCalendar.get(Calendar.MONTH), Double.valueOf(airplane.getCruisingSpeed().toString()));
				QuoteAirline quoteAirline3 = new QuoteAirline();
				quoteAirline3.setQuote(quote);
				quoteAirline3.setDeparture(start);
				quoteAirline3.setDepartureId(start.getId());
				quoteAirline3.setDestination(end);
				quoteAirline3.setDestinationId(end.getId());
				quoteAirline3.setTakeoffTime(quoteAirline.getTakeoffTime());
				quoteAirline3.setTimeCost(v_tmp_hour3);
				quoteAirline3.setIsSpecial(false);
				quoteAirline3.setPassengerNum(quoteAirline.getPassengerNum());
				// 增加任务航段
				quoteAirlineLinkedList.add(quoteAirline3);
				if(start.getLocations() == Airport.Location.external || end.getLocations() == Airport.Location.external){
					ext_loaded_hour = ext_loaded_hour + v_tmp_hour3;
					v_total_hour = v_total_hour + v_tmp_hour3;
				} else {
					int_loaded_hour = int_loaded_hour + v_tmp_hour3;
					v_total_hour = v_total_hour + v_tmp_hour3;
				}
				// 不是第一航段
				if(i>0){
					// 始发机场等于上一航段的终点机场时，计算调整时间
					if(start == quoteAirlineList.get(i-1).getDestination()){
						// 上一个航段起飞时间
						Date beforeTakeoffDate = quoteAirlineList.get(i-1).getTakeoffTime();
						// 降落时间
						Calendar loadCalendar = Calendar.getInstance();
						loadCalendar.setTime(beforeTakeoffDate);
						int minutes = (int)(beforeLoadTime*60);
						loadCalendar.add(Calendar.MINUTE, minutes);
						//停留天数
						int days = DateEditor.daysBetween(loadCalendar.getTime(), quoteAirline.getTakeoffTime()) -1 ;
						if(days < 0) {
							days = 0;
						}
						stopDay = stopDay + days;
					}
				}
				beforeLoadTime = v_tmp_hour3;
			}

			// 如果下一个航段不是连续的
			if(i < quoteAirlineList.size() - 1 && end != quoteAirlineList.get(i+1).getDeparture()){
				// 机场1
				Airport tmpStart = end;
				// 机场2
				Airport tmpEnd = quoteAirlineList.get(i+1).getDeparture();

				// 本航段的降落时间
				takeoffCalendar.add(Calendar.MINUTE, (int)(v_tmp_hour3*60));
				// 下一个航段的起飞时间
				Date nextTakeoff = quoteAirlineList.get(i+1).getTakeoffTime();
				Calendar nextTakeoffCalendar = Calendar.getInstance();
				nextTakeoffCalendar.setTime(nextTakeoff);
				// 间隔天数
				int spaceDays = DateEditor.daysBetween(takeoffCalendar.getTime(), nextTakeoff) - 1;
				if(spaceDays < 0){
					spaceDays = 0;
				}

				int month = 0;
				if(airportList.contains(tmpStart)){
					month = nextTakeoffCalendar.get(Calendar.MONTH);
				} else {
					month = takeoffCalendar.get(Calendar.MONTH);
				}
				// 连接两个不连续机场的飞行时间
				v_tmp_hour4 = airlineService.lineHour(tmpStart, tmpEnd, null, airplane.getTypeId(), month, Double.valueOf(airplane.getCruisingSpeed().toString()));

				// 其中一个是虚拟基地，或者间隔时间小于等于0天，或者如果直接调机时长大于停留最低小时要求，不产生调机
				if(airportList.contains(tmpStart) || airportList.contains(tmpEnd) || spaceDays <= 0 || v_tmp_hour4 > Float.valueOf(airplane.getMinimumHour())*spaceDays){
					// 起飞日期
					Calendar calendar = Calendar.getInstance();
					if(airportList.contains(tmpStart)){
						calendar = nextTakeoffCalendar;
						calendar.add(Calendar.MINUTE, -(int)(v_tmp_hour4*60));
					} else {
						calendar = takeoffCalendar;
					}

					QuoteAirline quoteAirline1 = new QuoteAirline();
					quoteAirline1.setQuote(quote);
					quoteAirline1.setIsSpecial(true);
					quoteAirline1.setDeparture(tmpStart);
					quoteAirline1.setDepartureId(tmpStart.getId());
					quoteAirline1.setDestination(tmpEnd);
					quoteAirline1.setDestinationId(tmpEnd.getId());
					quoteAirline1.setTimeCost(v_tmp_hour4);
					quoteAirline1.setTakeoffTime(calendar.getTime());
					quoteAirline1.setSpecialType(QuoteAirline.SpecialType.connect);
					quoteAirline1.setPassengerNum(0);
					// 增补不连续航段
					quoteAirlineLinkedList.add(quoteAirline1);
					if(tmpStart.getLocations() == Airport.Location.external || tmpEnd.getLocations() == Airport.Location.external){
						ext_empty_hour = ext_empty_hour + v_tmp_hour4;
						v_total_hour = v_total_hour + v_tmp_hour4;
					} else {
						int_empty_hour = int_empty_hour + v_tmp_hour4;
						v_total_hour = v_total_hour + v_tmp_hour4;
					}
					// 将停留天数加入调整天数
					if(airportList.contains(tmpStart) || airportList.contains(tmpEnd)){
						stopDay = stopDay + spaceDays;
					}
				} else {
					// 不调机的费用
					BigDecimal noChangeCost = new BigDecimal(0);
					if(tmpStart.getLocations() == Airport.Location.external || tmpEnd.getLocations() == Airport.Location.external){
						noChangeCost = airplane.getExtEmptyPrice().multiply(new BigDecimal(v_tmp_hour4));
						noChangeCost = noChangeCost.add(airplane.getExtLoadedPrice().multiply(new BigDecimal(spaceDays * Float.valueOf(airplane.getMinimumHour()))));
					} else {
						noChangeCost = airplane.getEmptyPrice().multiply(new BigDecimal(v_tmp_hour4));
						noChangeCost = noChangeCost.add(airplane.getLoadedPrice().multiply(new BigDecimal(spaceDays * Float.valueOf(airplane.getMinimumHour()))));
					}

					// 计算调机的费用
					// 距离两边最近的中间虚拟基地
					Airport tempAirport = airportService.findCenterAirport(tmpStart, tmpEnd, airportList);

					// 将飞机从始发机场调回最近虚拟基地的飞行时间(这是第一步)
					v_tmp_hour2 = airlineService.lineHour(tmpStart, tempAirport, null, airplane.getTypeId(), takeoffCalendar.get(Calendar.MONTH), Double.valueOf(airplane.getCruisingSpeed().toString()));


					// 将飞机从最近虚拟基地调到始发机场的飞行时间（这是第二步）
					v_tmp_hour = airlineService.lineHour(tempAirport, tmpEnd, null, airplane.getTypeId(), nextTakeoffCalendar.get(Calendar.MONTH), Double.valueOf(airplane.getCruisingSpeed().toString()));

					// 计算调机费用
					BigDecimal changeCost = new BigDecimal(0);
					if(tmpStart.getLocations() == Airport.Location.external || tempAirport.getLocations() == Airport.Location.external){
						changeCost = changeCost.add(airplane.getExtEmptyPrice().multiply(new BigDecimal(v_tmp_hour2)));
					} else {
						changeCost = changeCost.add(airplane.getEmptyPrice().multiply(new BigDecimal(v_tmp_hour2)));
					}
					if(tempAirport.getLocations() == Airport.Location.external || tmpEnd.getLocations() == Airport.Location.external){
						changeCost = changeCost.add(airplane.getExtEmptyPrice().multiply(new BigDecimal(v_tmp_hour)));
					} else {
						changeCost = changeCost.add(airplane.getEmptyPrice().multiply(new BigDecimal(v_tmp_hour)));
					}
					// 加上 地面费用
					changeCost.add(tempAirport.getInlandCost().getTransferCost().multiply(new BigDecimal(2)));

					// 调机费用小于不调机费用,进行调机
					if(changeCost.compareTo(noChangeCost) < 0){
						QuoteAirline quoteAirline1 = new QuoteAirline();
						quoteAirline1.setQuote(quote);
						quoteAirline1.setIsSpecial(true);
						quoteAirline1.setDeparture(tmpStart);
						quoteAirline1.setDepartureId(tmpStart.getId());
						quoteAirline1.setCenter(tempAirport);
						quoteAirline1.setCenterId(tempAirport.getId());
						quoteAirline1.setDestination(tmpEnd);
						quoteAirline1.setDestinationId(tmpEnd.getId());
						quoteAirline1.setTimeCost(v_tmp_hour2 + v_tmp_hour);
						quoteAirline1.setSpecialType(QuoteAirline.SpecialType.interrupted);
						quoteAirline1.setIsSpecial(true);
						quoteAirline1.setPassengerNum(0);
						// 增补调机航段
						quoteAirlineLinkedList.add(quoteAirline1);

						if(tmpStart.getLocations() == Airport.Location.external || tempAirport.getLocations() == Airport.Location.external){
							ext_empty_hour = ext_empty_hour + v_tmp_hour2 + v_tmp_hour;
							v_total_hour = v_total_hour + v_tmp_hour2 + v_tmp_hour;
						} else {
							int_empty_hour = int_empty_hour + v_tmp_hour2 + v_tmp_hour;
							v_total_hour = v_total_hour + v_tmp_hour2 + v_tmp_hour;
						}
						// 将停留天数加入调整天数
						stopDay = stopDay + spaceDays;
					} else { // 不调机
						QuoteAirline quoteAirline1 = new QuoteAirline();
						quoteAirline1.setQuote(quote);
						quoteAirline1.setIsSpecial(true);
						quoteAirline1.setDeparture(tmpStart);
						quoteAirline1.setDepartureId(tmpStart.getId());
						quoteAirline1.setDestination(tmpEnd);
						quoteAirline1.setDestinationId(tmpEnd.getId());
						quoteAirline1.setTimeCost(v_tmp_hour4);
						quoteAirline1.setTakeoffTime(takeoffCalendar.getTime());
						quoteAirline1.setSpecialType(QuoteAirline.SpecialType.connect);
						quoteAirline1.setIsSpecial(true);
						quoteAirline1.setPassengerNum(0);
						// 增补不连续航段
						quoteAirlineLinkedList.add(quoteAirline1);
						if(tmpStart.getLocations() == Airport.Location.external || tmpEnd.getLocations() == Airport.Location.external){
							ext_empty_hour = ext_empty_hour + v_tmp_hour4;
							v_total_hour = v_total_hour + v_tmp_hour4;
						} else {
							int_empty_hour = int_empty_hour + v_tmp_hour4;
							v_total_hour = v_total_hour + v_tmp_hour4;
						}
					}
				}
			}
		}
		// 如果最后终点不是虚拟基地
		QuoteAirline lastAirline = quoteAirlineList.get(quoteAirlineList.size()-1);
		Airport lastEnd = lastAirline.getDestination();
		// 最后航段起飞时间
		Date beforeTakeoffDate = lastAirline.getTakeoffTime();
		// 降落时间
		Calendar loadCalendar = Calendar.getInstance();
		loadCalendar.setTime(beforeTakeoffDate);
		int minutes = (int)(beforeLoadTime*60);
		loadCalendar.add(Calendar.MINUTE, minutes);
		if(!airportList.contains(lastEnd)){
			// 距最后终点机场最近的虚拟基地
			Airport base = airportService.findNearestAirport(lastEnd, airportList);
			Float v_tmp_hour = airlineService.lineHour(lastEnd, base, null, airplane.getTypeId(), loadCalendar.get(Calendar.MONTH), Double.valueOf(airplane.getCruisingSpeed().toString()));
			QuoteAirline quoteAirline1 = new QuoteAirline();
			quoteAirline1.setQuote(quote);
			quoteAirline1.setIsSpecial(true);
			quoteAirline1.setDeparture(lastEnd);
			quoteAirline1.setDepartureId(lastEnd.getId());
			quoteAirline1.setDestination(base);
			quoteAirline1.setDestinationId(base.getId());
			quoteAirline1.setTimeCost(v_tmp_hour);
			quoteAirline1.setSpecialType(QuoteAirline.SpecialType.after);
			quoteAirline1.setTakeoffTime(loadCalendar.getTime());
			quoteAirline1.setPassengerNum(0);
			// 增加后调机
			quoteAirlineLinkedList.add(quoteAirline1);
			if(lastEnd.getLocations() == Airport.Location.external || base.getLocations() == Airport.Location.external){
				ext_empty_hour = ext_empty_hour + v_tmp_hour;
				v_total_hour = v_total_hour + v_tmp_hour;
			} else {
				int_empty_hour = int_empty_hour + v_tmp_hour;
				v_total_hour = v_total_hour + v_tmp_hour;
			}
		}
		DecimalFormat df = new DecimalFormat("##0.00");
		// 总航程天数
		int totalDay = DateEditor.daysBetween(quoteAirlineList.get(0).getTakeoffTime(), loadCalendar.getTime()) + 1;
		quote.setTotalDay(totalDay);

		// 总飞行时长
		quote.setTotalHour(df.format(v_total_hour));

		// 计算最低消费小时数
		Double lowestHours = (totalDay - stopDay) * Double.valueOf(airplane.getMinimumHour());
		quote.setLowestHour(lowestHours.toString());
		quote.setLackHour("0");
		if(v_total_hour < lowestHours){
			// 补齐小时数
			quote.setLackHour(df.format(lowestHours - v_total_hour));
			int_loaded_hour = int_loaded_hour + (lowestHours - v_total_hour);
			v_total_hour = lowestHours;
		}
		// 实际消费小时数
		quote.setActualHours(df.format(v_total_hour));
		// 总飞行费用
		totalAmount = totalAmount.add(airplane.getEmptyPrice().multiply(new BigDecimal(int_empty_hour)))
				.add(airplane.getLoadedPrice().multiply(new BigDecimal(int_loaded_hour)))
				.add(airplane.getExtEmptyPrice().multiply(new BigDecimal(ext_empty_hour)))
				.add(airplane.getExtLoadedPrice().multiply(new BigDecimal(ext_loaded_hour)));

		// 总地面费用
		for(QuoteAirline quoteAirline : quoteAirlineLinkedList){
			totalMaintenance = totalMaintenance.add(quoteAirline.getMaintenanceCost());
		}
		quote.setTotalMaintenance(totalMaintenance);
		totalAmount = totalAmount.add(totalMaintenance);
		quote.setQuoteAirlineList(quoteAirlineLinkedList);
		DecimalFormat decimalFormat = new DecimalFormat("##0");
		quote.setTotalAmount(new BigDecimal(decimalFormat.format(totalAmount)));
		quote.setStopDays(stopDay);

		return  quote;
	}

}