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
import javax.persistence.UniqueConstraint;

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
@Table(name = "Authorities" ,uniqueConstraints = {
		@UniqueConstraint(columnNames = {"Username", "Roleid"})
})
public class Authority implements Serializable{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authId")
    private Integer authId;
	
	

    @ManyToOne
    @JoinColumn(name = "Username")
   
    private Account account;

    @ManyToOne
    @JoinColumn(name = "roleId")
//    @JsonIgnore
    private Role role;
    
    @Override
    public String toString() {
        return "Authority [authId=" + authId + "]";
    }

}
