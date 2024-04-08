package com.poly.been;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Comments")
public class Comment implements Serializable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId")
    private Integer commentId;

    @ManyToOne
    @JoinColumn(name = "username")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Column(name = "content", columnDefinition = "NVARCHAR(MAX)")
    private String content;

    @Column(name = "createdDate")
    private Date createdDate;

}
