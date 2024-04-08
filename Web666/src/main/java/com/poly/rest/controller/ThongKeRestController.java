package com.poly.rest.controller;



import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.service.OrderService;
import com.poly.service.ThongKeService;
@CrossOrigin("*")
@RestController
@RequestMapping("/rest/thongke")
public class ThongKeRestController {
	@Autowired
	ThongKeService thongKeService;

	
	 @GetMapping("/statistics")
	    public Map<String, Object> getOrderStatistics(
	            @RequestParam(name = "days", defaultValue = "7") int days) {
	        Date startDate = thongKeService.calculateStartDate(days);
	        Date endDate = new Date();

	        return thongKeService.getOrderStatistics(startDate, endDate);
	    }

	    @GetMapping("/statistics/7days")
	    public Map<String, Object> getOrderStatisticsFor7Days() {
	        int days = 7;
	        Date startDate = thongKeService.calculateStartDate(days);
	        Date endDate = new Date();

	        return thongKeService.getOrderStatistics(startDate, endDate);
	    }

	    @GetMapping("/statistics/30days")
	    public Map<String, Object> getOrderStatisticsFor30Days() {
	        int days = 30;
	        Date startDate = thongKeService.calculateStartDate(days);
	        Date endDate = new Date();

	        return thongKeService.getOrderStatistics(startDate, endDate);
	    }

	    @GetMapping("/statistics/90days")
	    public Map<String, Object> getOrderStatisticsFor90Days() {
	        int days = 90;
	        Date startDate = thongKeService.calculateStartDate(days);
	        Date endDate = new Date();

	        return thongKeService.getOrderStatistics(startDate, endDate);
	    }

	    @GetMapping("/statistics/365days")
	    public Map<String, Object> getOrderStatisticsFor365Days() {
	        int days = 365;
	        Date startDate = thongKeService.calculateStartDate(days);
	        Date endDate = new Date();

	        return thongKeService.getOrderStatistics(startDate, endDate);
	    }
	
	
}
