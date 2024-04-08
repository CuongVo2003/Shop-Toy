package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.been.Voucher;

@Repository
public interface VouchersDao extends JpaRepository<Voucher, String>{
	
}
