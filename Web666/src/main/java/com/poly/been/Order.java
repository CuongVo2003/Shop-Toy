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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@SuppressWarnings("serial")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Orders")
public class Order implements Serializable{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ordId")
    private Integer ordId;

    @ManyToOne
    @JoinColumn(name = "username")
    @JsonIgnoreProperties("orders")
    private Account account;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "order_address")
    private String order_address;

    @Column(name = "ord_phone")
    private String ordPhone;
    
    @Column(name = "priceSum")
    private Float priceSum;
    
    @Column(name = "ghiChu")
    private String ghiChu;
    
    @Column(name = "tinhTrang")
    private Boolean tinhTrang;
    
    @ManyToOne
    @JoinColumn(name = "voucher_id")
    @JsonIgnore
    private Voucher voucher;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore
    List<OrderDetail> orderDetails;
}
