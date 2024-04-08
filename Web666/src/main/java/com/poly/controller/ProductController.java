package com.poly.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.been.Category;
import com.poly.been.Comment;
import com.poly.been.Product;
import com.poly.service.CategoryService;
import com.poly.service.CommentService;
import com.poly.service.ProductService;

@Controller
public class ProductController {
	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	CommentService commentService;

	@RequestMapping("/product/list")
	public String list(Model model) {
		List<Category> categories = categoryService.findAll();
		model.addAttribute("catesIndex", categories);
		
		// hiện theo category
		// Tạo map chứa danh sách sản phẩm của từng danh mục
		Map<String, List<Product>> productMap = new HashMap<>();
		for (Category category : categories) {
			List<Product> productList = productService.findByCategoryCateId(category.getCateId());
			productMap.put("items_" + category.getCateId(), productList);
		}
		model.addAttribute("productMap", productMap);
		return "user/index";
	}



	@RequestMapping("product/shop")
	public String shop(Model model, @RequestParam("cid") Optional<String> cid,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "pageSize", defaultValue = "13") String pageSizeStr) {

		int pageSize = 8; // Giá trị mặc định nếu không thể chuyển đổi

		try {
			// Cố gắng chuyển đổi pageSize từ String sang int
			pageSize = Integer.parseInt(pageSizeStr);
		} catch (NumberFormatException e) {
			// Xử lý nếu có lỗi chuyển đổi
			e.printStackTrace(); // Hoặc log lỗi
		}

		List<Product> list;

		if (cid.isPresent()) {// nếu có
			list = productService.findByCategoryCateId(cid.get());// .get là để lấy 1 thứ j đó
			model.addAttribute("items", list);
		} else {
			list = productService.findAll();
			model.addAttribute("items", list);
		}

		// xửa lý page

		// Số lượng sản phẩm trên mỗi trang
		int totalItems = list.size();
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);

		// Lấy sublist tương ứng với trang hiện tại
		int startIndex = (page - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, totalItems);
		List<Product> itemsPerPage = list.subList(startIndex, endIndex);

		model.addAttribute("items", itemsPerPage);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);
		model.addAttribute("pageSizes", Arrays.asList(9, 10, 11));

		return "user/sanpham";
	}

	@RequestMapping("product/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Product item = productService.findById(id);
		model.addAttribute("item", item);
		
		// Load danh sách bình luận của sản phẩm
        List<Comment> comments = commentService.getCommentsByProductId(id);
        model.addAttribute("comments", comments);
		
		return "user/chitietsp";
	}
	
	@PostMapping("product/{productId}/comments")
    public String addComment(@PathVariable Integer productId,
                             @RequestParam String username,
                             @RequestParam String content,
                             RedirectAttributes redirectAttributes) {
        // Thêm bình luận vào cơ sở dữ liệu
        Comment comment = commentService.addComment(username, productId, content);

        // Redirect về trang chi tiết sản phẩm sau khi thêm bình luận
        return "redirect:/product/detail/" + productId;
    }

}
