package com.luanvan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("store/manage")
public class StorePage {

	@GetMapping("/")
	public String index() {
		return "admin/index";
	}
	
	@GetMapping("/product")
	public String manageProduct() {
		return "admin/manage-product";
	}
	@GetMapping("/promotion")
	public String managePromotion() {
		return "admin/manage-promotion";
	}
	@GetMapping("/review")
	public String manageReview() {
		return "admin/manage-review";
	}
	
	@GetMapping("/neworder")
	public String manageOrder() {
		return "admin/manage-order";
	}
	
	
	@GetMapping("/qa")
	public String manageQATypes() {
		return "admin/manage-qa";
	}
	
	@GetMapping("/dashboard")
	public String dashBoard() {
		return "admin/dashboard-store";
	};
	
	@GetMapping("/inventory")
	public String inventory() {
		return "admin/manage-inventory";
	};
	
}
