package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class OrderItemController {
	@Autowired
	private OrderItemRepository orderRepo;
	@Autowired
	private CategoryRepository categoryRepo;
	
	@GetMapping("/allcart")
	private String allCart(Model model) {
		List<OrderItem> orderItemList = orderRepo.findAll();
		model.addAttribute("orderItemList", orderItemList);
		List<Category> listCategories = categoryRepo.findAll();
		model.addAttribute("listCategories", listCategories);
		return "view_allcustomer_cart";

	}
}
