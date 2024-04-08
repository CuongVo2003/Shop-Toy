package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.been.Role;
import com.poly.dao.RoleDao;
import com.poly.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	RoleDao rDao;

	@Override
	public List<Role> findAll() {
		return rDao.findAll();
	}

	@Override
	public Role findById(String id) {
		return rDao.findById(id).orElse(null);
	}

	@Override
	public Role saveRole(Role role) {
		return rDao.save(role);
	}

	@Override
	public void deleteRole(String id) {
		rDao.deleteById(id);
		
	}

	@Override
	public Role getRoleByRoleId(String roleId) {
		return rDao.findByRoleId(roleId);
	}
}
