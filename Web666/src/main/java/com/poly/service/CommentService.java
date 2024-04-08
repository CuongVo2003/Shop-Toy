package com.poly.service;

import java.util.List;

import com.poly.been.Comment;

public interface CommentService {
	
	public Comment addComment(String username, Integer productId, String content);
	
	 // Phương thức để lấy danh sách bình luận của một sản phẩm dựa trên productId
    public List<Comment> getCommentsByProductId(Integer productId);

}
