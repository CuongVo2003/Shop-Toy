package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.been.Account;
import com.poly.been.Authority;
import com.poly.dao.AuthoritiesDao;
import com.poly.service.AccountService;
import com.poly.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService{

	@Autowired
	AuthoritiesDao atDao;
	@Autowired
	AccountService acDao;

	@Override
	public List<Authority> findAll() {
		return atDao.findAll();
	}

	@Override
	public Authority findById(Integer id) {
		return atDao.findById(id).orElse(null);
	}

	@Override
	public Authority saveAuthority(Authority authority) {
		return atDao.save(authority);
	}

	@Override
	public void deleteAuthority(Integer id) {
		atDao.deleteById(id);
		
	}


	@Override
	public List<Authority> findAuthoritiesOfAdministrators() {
		List<Account> accounts = acDao.getAdministrators();// lấy ra tk admin
		return atDao.authoritiesOf(accounts);//lấy quyền 
	}
}
