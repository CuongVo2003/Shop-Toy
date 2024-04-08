package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {
	// đăng nhập
	@RequestMapping("security/login/form")
	public String loginForm(Model model) {
		model.addAttribute("ms", "Vui lòng đăng nhập");
		return ("user/login");
	}
	
	@RequestMapping("security/login/success")
	public String loginSuccess(Model model) {
		return "redirect:/product/list";
	}
	@RequestMapping("security/login/error")
	public String loginError(Model model) {
		model.addAttribute("ms", "Đăng nhập thất bại");
		return ("user/login");
	}
	@RequestMapping("security/unauthoried")
	public String unauthoried(Model model) {
		model.addAttribute("ms", "Không có quyền truy xuất");
		return ("user/login");
	}
	
	@RequestMapping("security/logoff/success")
	public String logoffSeccuess(Model model) {
		model.addAttribute("ms", "Đăng xuất tc");
		return ("user/login");
	}
	@RequestMapping("user/account")
	public String updateAccount(Model model) {
		
		return ("user/my_account");
	}
	
	
	
}
