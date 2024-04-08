package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.been.Order;
import com.poly.been.OrderDetail;
import com.poly.dao.OrderDetailDAO;
import com.poly.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	OrderDetailDAO odDao;

	@Override
	public List<OrderDetail> findAll() {
		return odDao.findAll();
	}

	@Override
	public OrderDetail findById(Integer id) {
		return odDao.findById(id).orElse(null);
	}

	@Override
	public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
		return odDao.save(orderDetail);
	}

	@Override
	public void deleteOrderDetail(Integer id) {
		odDao.deleteById(id);

	}

	@Override
	public List<OrderDetail> getOrderDetailsByProductProId(Integer productId) {
		List<OrderDetail> save=  odDao.findByProductProId(productId);
		System.out.println("a" +save);
		 return save;
	}

	@Override
	public void deleteByProductProId(Integer productId) {
		odDao.deleteByProductProId(productId);
		
	}


	



}
