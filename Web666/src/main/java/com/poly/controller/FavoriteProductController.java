package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FavoriteProductController {
	@RequestMapping("spyt/view")
	public String view() {
		return "user/sp_yeuthich";
	}
}
