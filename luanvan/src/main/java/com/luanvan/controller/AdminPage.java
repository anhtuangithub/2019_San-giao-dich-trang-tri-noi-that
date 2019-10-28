package com.luanvan.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("admin/manage")
public class AdminPage {

	
	@GetMapping("/category")
	public String manageCategory() {
		return "admin/manage-category";
	}
	
	@GetMapping("/producer")
	public String manageProducer() {
		return "admin/manage-producer";
	}
	
	@GetMapping("/color")
	public String manageColor() {
		return "admin/manage-color";
	}
	
	@GetMapping("/material")
	public String manageMateril() {
		return "admin/manage-material";
	}
	
	@GetMapping("/orderstatus")
	public String manageOrderStatus() {
		return "admin/manage-orderstatus";
	}
	
	@GetMapping("/membertypes")
	public String manageMemberTypes() {
		return "admin/manage-membertype";
	}
	
	@GetMapping("/store")
	public String manageStore() {
		return "admin/manage-store";
	}
	
	@GetMapping("/customer")
	public String manageCustomer() {
		return "admin/manage-customer";
	}
	
	@GetMapping("/dashboard")
	public String dashBoard() {
		return "admin/dashboard-admin";
	};
	
	
	@GetMapping("/product-all")
	public String productAll() {
		return "admin/manage-all-product";
	};
	
}
