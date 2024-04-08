package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.been.Category;

public interface CategoryDAO extends JpaRepository<Category, String>{

}
