package com.poly.service;

import java.util.List;
import java.util.Optional;

import com.poly.been.Product;
import com.poly.been.ProductSaleDTO;

public interface ProductService {
	List<Product> findAll();

	Product findById(Integer id);

	Product saveProduct(Product product);

	Product update(Product product);

	void deleteProduct(Integer id);

	List<Product> findByCategoryCateId(String cid);

	// thống kê
	List<ProductSaleDTO> findTopSellingProducts(int limit);

	List<ProductSaleDTO> getSalesByPeriod(int days);

}
