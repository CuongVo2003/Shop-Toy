package com.poly.service;

import java.util.List;

import com.poly.been.Account;
import com.poly.been.Role;

public interface AccountService {
	List<Account> findAll();

	Account findById(String username);

	Account saveProduct(Account account);
	Account updateProduct(Account account);

	void deleteAccount(String username);

	List<Account> getAdministrators();

	public boolean existsByUsername(String username);

	public boolean existsByEmail(String email);

	public Account registerAccount(Account account);

	public Account getAccountByUsername(String username);
	
	 public Account findAccountWithRole(String username);

}
