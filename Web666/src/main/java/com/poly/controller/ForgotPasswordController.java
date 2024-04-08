package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.been.Account;
import com.poly.service.AccountService;
import com.poly.service.EmailService;

@Controller
public class ForgotPasswordController {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	EmailService emailService;
	
	 @GetMapping(value = "/fgpw/view")
	    public String showRegistrationForm(Model model) {
	        model.addAttribute("account", new Account());
	        return "user/forgotPassword";
	    }
	
	
	@RequestMapping(value = "/fgpw/view", method = RequestMethod.POST)
	public String forgotPassword(@RequestParam("username") String username, Model model) {
		Account account = accountService.getAccountByUsername(username);
        if (account != null) {
            // Tạo mật khẩu ngẫu nhiên
            String newPassword = generateRandomPassword();

            // Cập nhật mật khẩu mới cho tài khoản
            account.setPassword(newPassword);
            accountService.updateProduct(account);

            // Gửi email chứa mật khẩu mới
            emailService.sendConfirmationEmailforgotPassword(account.getEmail(), account.getUsername(), newPassword);

            // Hiển thị thông báo cho người dùng
            model.addAttribute("message", "Một email chứa mật khẩu mới đã được gửi tới địa chỉ email của bạn.");
        } else {
            model.addAttribute("error", "Tên người dùng không tồn tại.");
        }
		
		return "user/forgotPassword";
	}
	
	private String generateRandomPassword() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+=-";
	    StringBuilder password = new StringBuilder();
	    int length = 3; 

	    for (int i = 0; i < length; i++) {
	        int index = (int) (Math.random() * chars.length());
	        password.append(chars.charAt(index));
	    }

	    return password.toString();
    }
	
}
