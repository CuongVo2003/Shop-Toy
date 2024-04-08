package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.been.Account;

public interface AccountDAO extends JpaRepository<Account, String> {
	@Query("Select DISTINCT ar.account FROM Authority ar WHERE ar.role.id IN('DIRE','STAF')")
	List<Account> getAdministrators();
	
	@Query("SELECT a FROM Account a JOIN FETCH a.authorities auth WHERE a.username = ?1")
    Account findAccountWithRole(String username);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);
	
	Account findByUsername(String username);
}
