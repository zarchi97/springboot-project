package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class CategoryController {
	@Autowired
	private CategoryRepository categoryRepo;

	@GetMapping("/category")
	private String viewCategory(Model model) {
		List<Category> listCategories = categoryRepo.findAll();
		model.addAttribute("listCategories", listCategories);
		return "view_category";
	}

	@GetMapping("/addCategory")
	private String addCategory(Model model) {
		Category category = new Category();
		model.addAttribute("category", category);
		List<Category> listCategories=categoryRepo.findAll();
		model.addAttribute("listCategories", listCategories); 
		return "add_category";
	}

	@PostMapping("/saveCategory")
	public String saveCategory(@Valid Category category, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "add_category";
		}
		categoryRepo.save(category);
		return "redirect:/category";
	}

	// edit category
	@GetMapping("/editcategory{id}")
	public String editCategory(@PathVariable("id") Integer id, Model model) {
		Category category = categoryRepo.getReferenceById(id);
		model.addAttribute("category", category);
		List<Category> listCategories=categoryRepo.findAll();
		model.addAttribute("listCategories", listCategories); 
		return "edit_category";
	}

	@PostMapping("/editsavecategory{id}")
	public String saveUpdatedCategory(Category category,Model model) {
		categoryRepo.save(category);
		List<Category> listCategories=categoryRepo.findAll();
		model.addAttribute("listCategories", listCategories); 
		return "redirect:/category";
	}

	@GetMapping("/deletecategory{id}")
	public String deleteCategory(@PathVariable("id") Integer id,Model model) {
		categoryRepo.deleteById(id);
		 List<Category> listCategories=categoryRepo.findAll();
		 model.addAttribute("listCategories", listCategories); 
		return "redirect:/category";
	}
	@GetMapping("/dropdown")
	private String dropCategory(Model model) {
		List<Category> listCategories = categoryRepo.findAll();
		model.addAttribute("listCategories", listCategories);
		return "/fragment/header";
	}

}