package com.poly.service.impl;


import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.been.Order;
import com.poly.been.ThongKeDTO;
import com.poly.dao.OrderDAO;
import com.poly.dao.OrderDetailDAO;
import com.poly.service.ThongKeService;
@Service
public class ThongKeServiceImpl implements ThongKeService{
	
	@Autowired
	OrderDAO orderDAO;
	
	@Autowired
	OrderDetailDAO orderDetailDAO;

	@Override
	public Date calculateStartDate(int days) {
		   long millis = System.currentTimeMillis() - (days * 24 * 60 * 60 * 1000L);
	        return new Date(millis);
	}

	@Override
	public Map<String, Object> getOrderStatistics(Date startDate, Date endDate) {
		long totalOrders = orderDAO.countOrdersByCreateDateBetween(startDate, endDate);
        double totalMoney = orderDAO.sumOrderAmountByCreateDateBetween(startDate, endDate);
        long totalQuantity = orderDAO.sumOrderQuantityByCreateDateBetween(startDate, endDate);
        long totalUniqueCustomers = countUniqueCustomers(startDate, endDate);

        // Tạo một map để lưu trữ kết quả thống kê
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalOrders", totalOrders);
        statistics.put("totalMoney", totalMoney);
        statistics.put("totalQuantity", totalQuantity);
        statistics.put("totalUniqueCustomers", totalUniqueCustomers);

        return statistics;
      }

	@Override
	public long countUniqueCustomers(Date startDate, Date endDate) {
		// Sử dụng phương thức của DAO để truy vấn danh sách các đơn hàng trong khoảng thời gian được cung cấp
	    List<Order> orders = orderDAO.findByCreateDateBetween(startDate, endDate);
	    
	    // Tạo một HashSet để lưu trữ các tên khách hàng duy nhất
	    Set<String> uniqueCustomers = new HashSet<>();
	    
	    // Duyệt qua danh sách các đơn hàng và thêm tên khách hàng vào HashSet
	    for (Order order : orders) {
	        uniqueCustomers.add(order.getAccount().getUsername());
	    }
	    
	    // Trả về số lượng phần tử trong HashSet, đây chính là số lượng khách hàng duy nhất
	    return uniqueCustomers.size();
	}

//	@Override
//	public Date calculateStartDate(int days) {
//		// Tính ngày bắt đầu dựa trên số ngày
//        // Ví dụ, nếu ngày = 7, trả về ngày 7 ngày trước từ ngày hôm nay
//        // Bạn có thể sử dụng thư viện như java.time để xử lý ngày tháng
//        // Ở đây, tôi sử dụng cách tiếp cận đơn giản cho mục đích minh họa
//		long millis = System.currentTimeMillis() - (days * 24 * 60 * 60 * 1000L);
//        return new Date(millis);
//	}
//
//	@Override
//	public Map<String, Object> getOrderStatistics(Date startDate, Date endDate) {
//		// Thực hiện logic của bạn để lấy thống kê từ cơ sở dữ liệu
//        // Bạn có thể sử dụng orderRepository để tương tác với cơ sở dữ liệu
//        // Để đơn giản, tôi sử dụng một phương thức giữ chỗ ở đây
//
//        // Lấy tổng số đơn hàng, tổng số tiền và tổng số lượng cho khoảng ngày cụ thể
//		int totalOrders = orderDAO.countOrdersByCreateDateBetween(startDate, endDate);
//        double totalAmount = orderDAO.sumOrderAmountByCreateDateBetween(startDate, endDate);
//        int totalQuantity = orderDAO.sumOrderQuantityByCreateDateBetween(startDate, endDate);
//
//        Map<String, Object> statistics = new HashMap<>();
//        statistics.put("totalOrders", totalOrders);
//        statistics.put("totalAmount", totalAmount);
//        statistics.put("totalQuantity", totalQuantity);
//        return statistics;
//	}

	
	

}
