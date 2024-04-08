package com.poly;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.poly.been.Account;
import com.poly.service.AccountService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	AccountService accountService;
	

	// mã hóa mật khẩu
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	 //cung cấp nguồn dữ liệu đăng nhập
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService( username ->{
			System.out.println("tc");
			try {
				Account user = accountService.findById(username);
				
				String password = getPasswordEncoder().encode(user.getPassword());
				
				String[] roles = user.getAuthorities().stream()
						.map(er -> er.getRole().getRoleId())
						.collect(Collectors.toList()).toArray(new String[0]);
				
				System.out.println("tc1"+username+password+roles);
				return User.withUsername(username).password(password).roles(roles).build();
				
			} catch (Exception e) {
				System.out.println("tc2"+username);
				throw new UsernameNotFoundException(username+ "not found! / securityConfig");
			}
		});

//		auth.userDetailsService();
	}

	//phân quyền 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
		.antMatchers("/order/**").authenticated()// khách hàng
		.antMatchers("/admin/**").hasAnyRole("STAF","DIRE")//admin, nhân viên
		.antMatchers("/rest/authorities").hasRole("DIRE")//admin
		.anyRequest().permitAll();
		
		http.formLogin()
		.loginPage("/security/login/form")
		.loginProcessingUrl("/security/login")
		.defaultSuccessUrl("/security/login/success",false)
		.failureUrl("/security/login/error");
		
		 // Cấu hình Remember Me
        http.rememberMe()
         .key("uniqueAndSecret")
         .tokenValiditySeconds(86400);

		
		
		
		http.exceptionHandling()
		.accessDeniedPage("/security/unauthoried");// đang nhập rồi và truy cập trang ko đk cấp quyền 
		
		http.logout()
		.logoutUrl("/security/logoff")
		.logoutSuccessUrl("/security/logoff/success");
		
		 
		
	}
	
	// cho phép truy xuất REST API từ bên ngoài (domain khác)
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS,"/**");
	}

}
