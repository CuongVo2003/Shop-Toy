package com.poly.service;

import java.util.List;

import com.poly.been.Order;
import com.poly.been.OrderDetail;

public interface OrderDetailService {
	List<OrderDetail> findAll();

	OrderDetail findById(Integer id);

	OrderDetail saveOrderDetail(OrderDetail orderDetail);

	void deleteOrderDetail(Integer id);
	
	List<OrderDetail> getOrderDetailsByProductProId(Integer productId);
	

	void deleteByProductProId(Integer productId);

	
}
