package com.poly.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.been.Order;

public interface OrderDAO extends JpaRepository<Order, Integer> {
	@Query("SELECT o FROM Order o WHERE o.account.username=?1")
	List<Order> findByUsername(String username);

	// Thống kê

	// Đếm số đơn hàng trong khoảng thời gian
	int countOrdersByCreateDateBetween(Date startDate, Date endDate);

	// Tính tổng số tiền của đơn hàng trong khoảng thời gian
	@Query("SELECT COALESCE(SUM(od.price), 0) FROM OrderDetail od WHERE od.order.createDate BETWEEN ?1 AND ?2")
	double sumOrderAmountByCreateDateBetween(Date startDate, Date endDate);

	// Tính tổng số lượng của đơn hàng trong khoảng thời gian
	@Query("SELECT COALESCE(SUM(od.quantity), 0) FROM OrderDetail od WHERE od.order.createDate BETWEEN ?1 AND ?2")
	int sumOrderQuantityByCreateDateBetween(Date startDate, Date endDate);
	
	List<Order> findByCreateDateBetween(Date startDate, Date endDate);
	

}
