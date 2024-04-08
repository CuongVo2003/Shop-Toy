package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.been.Product;

public interface ProductDAO extends JpaRepository<Product, Integer>{
//	@Query("SELECT p FROM Product p WHERE p.category.cateId=?1")
	List<Product> findByCategoryCateId(String cid);
	
}
