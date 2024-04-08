package com.poly.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.been.OrderDetail;
import com.poly.been.Product;
import com.poly.been.ProductSaleDTO;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Integer>{

	void deleteByProductProId(Integer proId);
	
	List<OrderDetail> findByProductProId(Integer proId);
	
	// thống kê 
	

	
	
}
