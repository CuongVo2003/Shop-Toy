package com.poly.been;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@SuppressWarnings("serial")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Products")
public class Product implements Serializable{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proId")
    private Integer proId;

    @Column(name = "prodNname")
    private String prodName;

    @Column(name = "image1")
    private String image1;

    @Column(name = "image2")
    private String image2;

    @Column(name = "image3")
    private String image3;

    @Column(name = "imageIcon")
    private String imageIcon;

    @Column(name = "price")
    private double price;
    @Temporal(TemporalType.DATE)
    @Column(name = "createDate")
    private Date createDate;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "describe")
    private String describe;

    @Column(name = "year_old")
    private String yearOld;

    @Column(name = "trademark")
    private String trademark;

    @Column(name = "origin")
    private String origin;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    
     Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderDetail> orderDetails;

   
}
