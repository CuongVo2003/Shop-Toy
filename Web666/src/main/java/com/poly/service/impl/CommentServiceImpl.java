package com.poly.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.been.Account;
import com.poly.been.Comment;
import com.poly.been.Product;
import com.poly.dao.AccountDAO;
import com.poly.dao.CommentDao;
import com.poly.dao.ProductDAO;
import com.poly.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentDao cDao;

	@Autowired
	AccountDAO aDao;

	@Autowired
	ProductDAO pDao;

	@Override
	public Comment addComment(String username, Integer productId, String content) {
		Account account = aDao.findByUsername(username);
        Product product = pDao.findById(productId).orElse(null);

        if (account != null && product != null) {
            Comment comment = new Comment();
            comment.setAccount(account);
            comment.setProduct(product);
            comment.setContent(content);
            comment.setCreatedDate(new Date());
            return cDao.save(comment);
        } else {
            // Xử lý trường hợp không tìm thấy tài khoản hoặc sản phẩm
            return null;
        }
	}

	@Override
	public List<Comment> getCommentsByProductId(Integer productId) {
		return cDao.findByProductProId(productId);
	}

}
