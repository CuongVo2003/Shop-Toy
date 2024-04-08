package com.poly.been;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSaleDTO {
	private Product product;
	private int quantity;
	private double price;
	private String image1;
}
