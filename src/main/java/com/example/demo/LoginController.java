package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	@Autowired
	private CategoryRepository categoryRepo;
	@GetMapping("/login")
	public String Login(Model model) {
		List<Category> listCategories = categoryRepo.findAll();
		model.addAttribute("listCategories", listCategories);
		return "login";
		
	}

}
