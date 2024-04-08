package com.poly.been;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "Accounts")
public class Account implements Serializable{
	@Id
    @Column(name = "Username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "email")
    private String email;
    
    @Column(name = "account_adress")
    private String account_adress;

    @Column(name = "phone")
    private String phone;

    @Column(name = "photo")
    private String photo;

    @OneToMany(mappedBy = "account" , fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Authority> authorities;
    
    @JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Order> orders;
    
//    @Override
//    public String toString() {
//        return "Account [username=" + username + ", fullname=" + fullname + ", email=" + email + "]";
//    }
//   
}
