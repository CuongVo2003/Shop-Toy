package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.been.Comment;


public interface CommentDao extends JpaRepository<Comment, Integer>{
	List<Comment> findByProductProId(Integer productId);
}
