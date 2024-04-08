package com.poly.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.been.Category;
import com.poly.been.Product;
import com.poly.service.CategoryService;
import com.poly.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/categories")
public class CategoriesRestController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;

	@GetMapping()
	public List<Category> getAll() {
		return categoryService.findAll();
	}

	// lấy id
	@GetMapping("{id}")
	public Category getOne(@PathVariable("id") String id) {
		return categoryService.findById(id);
	}

	@PostMapping()
	public Category create(@RequestBody Category category) {
		return categoryService.saveCategory(category);
	}

	@PutMapping("{id}")
	public Category update(@PathVariable("id") String id, @RequestBody Category category) {
		return categoryService.saveCategory(category);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") String id) {

		categoryService.deleteCategory(id);
	}
}
