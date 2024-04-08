package com.poly.service;

import java.util.List;

import com.poly.been.Authority;



public interface AuthorityService {
	List<Authority> findAll();
	Authority findById(Integer id);
	Authority saveAuthority(Authority authority);
	void deleteAuthority(Integer id);
	List<Authority> findAuthoritiesOfAdministrators();
}
