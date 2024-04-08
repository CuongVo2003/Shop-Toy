package com.poly.rest.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.been.Order;

import com.poly.service.OrderService;


@CrossOrigin("*")
@RestController
@RequestMapping("rest/orders")
public class OrderRestController {
	@Autowired
	OrderService orderService;
	
	
	
	@PostMapping("/confirm/{id}")
	@ResponseBody
	public ResponseEntity<?> confirmOrder(@PathVariable("id") Integer id) {
	    Order order = orderService.findById(id);
	    if (order != null) {
	        order.setTinhTrang(true);
	        orderService.saveOrder(order);
	        return ResponseEntity.ok().body(Collections.singletonMap("success", true));
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	            .body(Collections.singletonMap("success", false));
	    }
	}
	
	@PostMapping()
	public Order create(@RequestBody JsonNode orderData) {
		return orderService.create(orderData);
	}

}
