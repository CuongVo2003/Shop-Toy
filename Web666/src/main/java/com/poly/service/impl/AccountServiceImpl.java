package com.poly.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.been.Account;
import com.poly.been.Authority;
import com.poly.been.Role;
import com.poly.dao.AccountDAO;
import com.poly.dao.AuthorityDao;
import com.poly.dao.RoleDao;
import com.poly.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{
	@Autowired
	AccountDAO aDao;
	
	@Autowired
	RoleDao rDao;
	
	@Autowired
	AuthorityDao auDao;
	
	@Override
	public List<Account> findAll() {
		// TODO Auto-generated method stub
		return aDao.findAll();
	}

	@Override
	public Account findById(String username) {
		// TODO Auto-generated method stub
		return aDao.findById(username).orElse(null);
	}

	@Override
	public Account saveProduct(Account account) {
		// TODO Auto-generated method stub
		return aDao.save(account);
	}
	
	@Override
	public Account updateProduct(Account account) {
		// TODO Auto-generated method stub
		return aDao.save(account);
	}

	@Override
	public void deleteAccount(String username) {
		aDao.deleteById(username);
		
	}
	// lấy ra tk admin
	@Override
	public List<Account> getAdministrators() {
		return aDao.getAdministrators();
	}

	@Override
	public boolean existsByUsername(String username) {
		return aDao.existsByUsername(username);
	}

	@Override
	public boolean existsByEmail(String email) {
		return aDao.existsByEmail(email);
	}
	
	

	@Override
	public Account registerAccount(Account account) {
	    // Kiểm tra xem tài khoản đã tồn tại chưa
	    if (aDao.existsByUsername(account.getUsername()) || aDao.existsByEmail(account.getEmail())) {
	        return null; // Trả về null nếu tài khoản đã tồn tại
	    }

	    // Lưu tài khoản vào cơ sở dữ liệu
	    Account savedAccount = aDao.save(account);
	    if (savedAccount == null) {
	        // Xử lý khi lưu tài khoản không thành công
	        System.out.println("Lưu tài khoản không thành công");
	        return null;
	    }

	    // Lấy Role có roleId là "CUST" từ cơ sở dữ liệu
	    Role role = rDao.findByRoleId("CUST");
	    if (role == null) {
	        // Xử lý khi không tìm thấy role
	        System.out.println("Không tìm thấy role với roleId = CUST");
	        return null;
	    }

	    // Tạo đối tượng Authority và gán giá trị
	    Authority authority = new Authority();
	    authority.setRole(role);
	    authority.setAccount(savedAccount); // Gán tài khoản cho Authority

	    // Lưu Authority vào cơ sở dữ liệu
	    authority = auDao.save(authority);
	    if (authority == null) {
	        // Xử lý khi lưu Authority không thành công
	        System.out.println("Lưu Authority không thành công");
	        return null;
	    }

	    // Gán Authority cho Account và lưu Account vào cơ sở dữ liệu
	    List<Authority> authorities = new ArrayList<>();
	    authorities.add(authority);
	    savedAccount.setAuthorities(authorities);

	    return savedAccount;
	}

	@Override
	public Account getAccountByUsername(String username) {
		return aDao.findByUsername(username);
	}

	@Override
	public Account findAccountWithRole(String username) {
		 return aDao.findAccountWithRole(username);
	}

	



	

	
	


}
