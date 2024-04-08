package com.poly.been;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public  class ThongKeDTO implements Serializable{
	private long totalOrders;
    private double totalMoney;
    private long totalQuantity;
    private long totalUniqueCustomers;
}
