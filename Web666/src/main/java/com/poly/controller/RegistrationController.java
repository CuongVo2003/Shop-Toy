package com.poly.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.poly.been.Account;
import com.poly.been.Authority;
import com.poly.been.Role;
import com.poly.service.AccountService;
import com.poly.service.EmailService;

@Controller
public class RegistrationController {
	@Autowired
     AccountService accountService;

    @Autowired
     EmailService emailService;
     
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("account", new Account());
        return "user/register";
    }
    


    
    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute("account") @Validated Account account, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/register";
        }

        if (accountService.existsByUsername(account.getUsername()) || accountService.existsByEmail(account.getEmail())) {
            model.addAttribute("error", "Username or email đã tồn tại");
            return "user/register";
        }

        Account savedAccount = accountService.registerAccount(account);
        if (savedAccount != null) {
        	emailService.sendConfirmationEmail(account.getEmail(), account.getUsername(), account.getPassword());
            model.addAttribute("message", "Đăng ký thành công. Check mail của bạn để biết kiểm tra");
//            return "user/login";
            return "user/register";
        } else {
            model.addAttribute("error", "Đăng ký thất bại vui lòng thử lại");
            return "user/register";
        }
        
        
    }
}
