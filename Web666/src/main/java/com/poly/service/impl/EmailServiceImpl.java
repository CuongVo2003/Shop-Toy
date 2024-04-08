package com.poly.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.poly.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
	JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	

	@Override
	public void sendConfirmationEmail(String toEmail, String username, String password) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(fromEmail);
		mailMessage.setTo(toEmail);
		mailMessage.setSubject("Đăng ký thành công");
		  String emailContent = String.format("Cảm ơn bạn đã đăng ký. Tài khoản của bạn đã được tạo thành công với.%n%nUsername: %s%nPassword: %s", username, password);
		    mailMessage.setText(emailContent);

		javaMailSender.send(mailMessage);
		
	}



	@Override
	public void sendConfirmationEmailforgotPassword(String toEmail, String username, String password) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(fromEmail);
		mailMessage.setTo(toEmail);
		mailMessage.setSubject("Lấy lại mật khẩu thành công");
		  String emailContent = String.format("Chào bạn. Lấy lại với tên tài khoản và password là:%n%nUsername: %s%nPassword: %s", username, password);
		    mailMessage.setText(emailContent);

		javaMailSender.send(mailMessage);
		
	}

}
