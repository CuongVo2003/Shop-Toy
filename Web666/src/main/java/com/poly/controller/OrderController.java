package com.poly.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.poly.been.Account;
import com.poly.been.Order;
import com.poly.service.AccountService;
import com.poly.service.OrderService;

@Controller
public class OrderController {
	@Autowired
	OrderService orderService;
	@Autowired
	private AccountService accountService;

	// xử lý admin
	@RequestMapping("admin/order")
	public String adminOrderList(Model model, HttpServletRequest request) {
		model.addAttribute("orders", orderService.findAll());
		return "/admin/order/index";
	}

	@RequestMapping("admin/orderDetail/{id}")
	public String adminOrderdetail(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("order", orderService.findById(id));

		return "/admin/order/detailOrderAdmin";
	}

	@RequestMapping("order/checkout")
	public String checkout(Model model) {
		// Lấy thông tin tài khoản từ cơ sở dữ liệu
		Account account = accountService
				.getAccountByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		// Đưa thông tin tài khoản vào model để truyền sang View
		model.addAttribute("account", account);
		return "user/checkout_thanhtoan";
	}

	@RequestMapping("order/list")
	public String list(Model model, HttpServletRequest request) {
		String username = request.getRemoteUser();
		model.addAttribute("orderList", orderService.findByUsername(username));
		return "user/my_orderlist";
	}

	@RequestMapping("order/detail/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {

		Order order = orderService.findById(id);
	    model.addAttribute("order", order);
	    return "user/detailOrder";
	}
	
//	 @PostMapping("order/detail/{id}")
//	    public String updateOrderStatus(@PathVariable("id") Integer id, Order updatedOrder, Model model) {
//	        Order order = orderService.findById(id);
//	        if (order != null) {
//	            order.setTinhTrang(updatedOrder.getTinhTrang());
//	            Order savedOrder = orderService.saveOrder(order);
//	            if (savedOrder != null) {
//	                model.addAttribute("successMessage", "Tình trạng đơn hàng đã được cập nhật thành công!");
//	            } else {
//	                model.addAttribute("errorMessage", "Đã xảy ra lỗi, không thể cập nhật tình trạng đơn hàng.");
//	            }
//	        } else {
//	            model.addAttribute("errorMessage", "Không tìm thấy đơn hàng có ID: " + id);
//	        }
//	        return "redirect:/";
//	    }
	
	



}
