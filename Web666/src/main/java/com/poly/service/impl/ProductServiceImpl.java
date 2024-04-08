package com.poly.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.been.OrderDetail;
import com.poly.been.Product;
import com.poly.been.ProductSaleDTO;
import com.poly.dao.ProductDAO;
import com.poly.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	ProductDAO pDao;

	@Override
	public List<Product> findAll() {
		 return pDao.findAll();
	}

	@Override
	public Product findById(Integer id) {
		return pDao.findById(id).orElse(null);
	}

	@Override
	public Product saveProduct(Product product) {
		return pDao.save(product);
	}

	@Override
	public void deleteProduct(Integer id) {
		 pDao.deleteById(id);
		
	}

	@Override
	public List<Product> findByCategoryCateId(String cid) {
		return pDao.findByCategoryCateId(cid);
	}

	@Override
	public Product update(Product product) {
		return pDao.save(product);
	}

	@Override
	public List<ProductSaleDTO> findTopSellingProducts(int limit) {
		List<ProductSaleDTO> topProducts = new ArrayList<>();

        List<Product> products = pDao.findAll();
        for (Product product : products) {
            int totalQuantity = calculateTotalQuantity(product.getOrderDetails());
            double productPrice = product.getPrice();
            String productImage = product.getImage1();
			topProducts.add(new ProductSaleDTO(product, totalQuantity, productPrice,productImage));
        }

        topProducts.sort(Comparator.comparingInt(ProductSaleDTO::getQuantity).reversed());

        return topProducts.stream().limit(limit).collect(Collectors.toList());
	}
	
	private int calculateTotalQuantity(List<OrderDetail> orderDetails) {
        return orderDetails.stream().mapToInt(OrderDetail::getQuantity).sum();
    }

	@Override
	public List<ProductSaleDTO> getSalesByPeriod(int days) {
		// TODO Auto-generated method stub
		return null;
	}
}
