package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.been.Role;

public interface RoleDao extends JpaRepository<Role, String>{
	Role findByRoleId(String roleId);
}
