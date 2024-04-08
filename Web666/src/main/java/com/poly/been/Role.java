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
@Table(name = "Roles")
public class Role implements Serializable{
	@Id
    @Column(name = "roleId")
    private String roleId;

    @Column(name = "roleName")
    private String roleName;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Authority> authorities;
}
