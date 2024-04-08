package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.been.Category;
import com.poly.dao.CategoryDAO;
import com.poly.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryDAO cDao;
	
	@Override
	public List<Category> findAll() {
		return cDao.findAll();
	}

	@Override
	public Category findById(String id) {
		return cDao.findById(id).orElse(null);
	}

	@Override
	public Category saveCategory(Category category) {
		return cDao.save(category);
	}

	@Override
	public void deleteCategory(String id) {
		cDao.deleteById(id);
		
	}

}
