package com.poly.been;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "OrderDetails")
public class OrderDetail implements Serializable {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ordetailId")
    private Integer ordetailId;

	@ManyToOne
	@JoinColumn(name = "order_id")
	@JsonIgnore
	private Order order;

	@ManyToOne
	@JoinColumn(name = "product_id")
	
	private Product product;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "price")
	private double price;
}
