package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.been.Account;
import com.poly.been.Authority;

public interface AuthoritiesDao extends JpaRepository<Authority, Integer>{
	// LẤY quyền trong account
		@Query("Select DISTINCT a FROM Authority a WHERE a.account IN ?1")
		List<Authority> authoritiesOf(List<Account> accounts);
}
