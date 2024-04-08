package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.service.VoucherService;

@Controller
public class ShoppingCartController {
	@Autowired
	VoucherService voucherService;

	@RequestMapping("cart/view")
	public String view() {
		return "user/my_cart";
	}


	
	@RequestMapping(value = "/apply-voucher", method = { RequestMethod.GET, RequestMethod.POST })
	    public String applyVoucher(@RequestParam("voucherId") String voucherId, Model model) {
	        boolean applied = voucherService.applyVoucher(voucherId);
	        if (applied) {
	            double discountPercentage = voucherService.getDiscountPercentage(voucherId);
	            int discountPercentageInt = (int) discountPercentage;
	            String voucherName = voucherService.getVoucherName(voucherId);
	            model.addAttribute("successMsg", "Add voucher thành công!" );
	            model.addAttribute("discountPercentage", + discountPercentageInt );
	            model.addAttribute("voucherName",  voucherName );
	            model.addAttribute("voucherApplied", true);
	            
	        } else {
	        	model.addAttribute("voucherApplied", false);
	            model.addAttribute("errorMsg", "Add voucher thất bại vui lòng nhập đúng hoặc voucher đã hết hạn.");
	        }
	        return "user/checkout_thanhtoan";
	    }
	
	

}
