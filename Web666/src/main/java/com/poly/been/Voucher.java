package com.poly.been;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Vouchers")
public class Voucher implements Serializable {
	@Id
	@Column(name = "vouId")
	private String vouId;

	@Column(name = "discount_percentage")
	private Double discountPercentage;

	@Column(name = "expiry_date")
	private LocalDate  expiryDate;

	@Column(name = "quantity")
	private int quantity;

	@OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Order> orders;

}
