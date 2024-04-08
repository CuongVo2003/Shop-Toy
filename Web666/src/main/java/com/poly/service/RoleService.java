package com.poly.service;

import java.util.List;

import com.poly.been.Role;


public interface RoleService {
	List<Role> findAll();
	Role findById(String id);
	Role saveRole(Role role);
	void deleteRole(String id);
	public Role getRoleByRoleId(String roleId);
}
