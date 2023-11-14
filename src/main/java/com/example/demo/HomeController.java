package com.example.demo;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@Autowired
	private CategoryRepository categoryRepo;
	
	@GetMapping("/")
	private String indexPage(Model model) {
		 List<Category> listCategories=categoryRepo.findAll();
		 model.addAttribute("listCategories", listCategories);
		 return "index";
	}
	@GetMapping("/about")
	private String aboutFashion(Model model) {
		 List<Category> listCategories=categoryRepo.findAll();
		 model.addAttribute("listCategories", listCategories);
		 return "about";
	}
	  @GetMapping("/403") 
	   private String error403() { 
	  	   return "403"; 
	   }
	

	
}