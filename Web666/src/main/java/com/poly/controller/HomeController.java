package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	@RequestMapping({"/","/home/index"})
	public String home() {
		return "redirect:/product/list";
	}
	
	@RequestMapping({"/admin","/admin/home/index"})
	public String admin() {
		return "redirect:/admin/index";
	
	}
	
	
	@RequestMapping({"/admin/index"})
	public String adminIndex() {
		return "/admin/index";
	
	}
	@RequestMapping({"/admin/product"})
	public String adminProduct() {
		return "/admin/product/index";
	
	}
	@RequestMapping({"/admin/account"})
	public String adminAccount() {
		return "/admin/account/index";
	
	}
	@RequestMapping({"/admin/category"})
	public String adminCategory() {
		return "/admin/categori/index";
	
	}
	@RequestMapping({"/admin/voucher"})
	public String adminVoucher() {
		return "/admin/voucher/index";
	
	}
	@RequestMapping({"/admin/authority"})
	public String adminAuthority() {
		return "/admin/authority/index";
	
	}
	
	@RequestMapping("/admin/order-statistics")
	public String showOrderStatisticsPage(Model model, @RequestParam(name = "days", defaultValue = "7") int days) {
		model.addAttribute("days", days);
		return "/admin/order-statistics"; // Trả về tên của trang HTML cần hiển thị
	}
	
	@RequestMapping({"/admin/t5Sp"})
	public String adminThongkeTop5SpBanChay() {
		return "/admin/top5spbanchay";
	
	}
	

	
}
