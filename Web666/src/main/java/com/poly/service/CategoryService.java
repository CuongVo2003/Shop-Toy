package com.poly.service;

import java.util.List;

import com.poly.been.Category;

public interface CategoryService {

	List<Category> findAll();
	Category findById(String id);
	Category saveCategory(Category category);
	void deleteCategory(String id);
}
