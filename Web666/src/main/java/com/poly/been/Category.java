package com.poly.been;

import java.io.Serializable;
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
@Table(name = "Categories")
public class Category implements Serializable {
	@Id
	@Column(name = "cateId")
	 String cateId;

	@Column(name = "cateName")
	String cateName;

	@OneToMany(mappedBy = "category")
	@JsonIgnore
	 List<Product> products;

}
