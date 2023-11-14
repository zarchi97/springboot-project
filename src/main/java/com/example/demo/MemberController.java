package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class MemberController {
	@Autowired
	private MemberRepository memberRepo;
@Autowired
private CategoryRepository categoryRepo;
@Autowired
private CartItemRepository cartItemRepo;
	@GetMapping("/members")
	public String viewMembers(Model model) {
		List<Member> listMembers = memberRepo.findAll();
		model.addAttribute("listMembers", listMembers);
		return "view_member";

	}

	@GetMapping("/addmember")
	public String addMember(Model model) {
		Member member = new Member();
		model.addAttribute("member", member);
		List<Category> listCategories = categoryRepo.findAll();
		model.addAttribute("listCategories", listCategories);

		return "add_member";
	}

	@PostMapping("/savemember")
	public String saveMember(@Valid Member member, BindingResult bindingResult, RedirectAttributes redirectAttribute,Model model) {
		if (bindingResult.hasErrors()) {
			return "add_member";
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodePassword = passwordEncoder.encode(member.getPassword());

		member.setPassword(encodePassword);
		member.setRole("ROLE_ADMIN");

		memberRepo.save(member);

		redirectAttribute.addFlashAttribute("success", "Member registered!");
		
		List<Category> listCategories = categoryRepo.findAll();
		model.addAttribute("listCategories", listCategories);

		return "redirect:/members";

	}

	@GetMapping("/editmember{id}")
	public String editMember(@PathVariable("id") Integer id, Model model) {
		Member member = memberRepo.getReferenceById(id);
		model.addAttribute("member", member);
		
		List<Category> listCategories = categoryRepo.findAll();
		model.addAttribute("listCategories", listCategories);

		return "edit_member";

	}

	@PostMapping("/editmember{id}")
	public String editsaveMember(Member member, RedirectAttributes redirectAttribute,Model model) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodePassword = passwordEncoder.encode(member.getPassword());

		member.setPassword(encodePassword);
		member.setRole("ROLE_ADMIN");

		memberRepo.save(member);

		redirectAttribute.addFlashAttribute("success", "Edit Successful!");
		
		List<Category> listCategories = categoryRepo.findAll();
		model.addAttribute("listCategories", listCategories);

		return "redirect:/members";

	}

	@GetMapping("/deletemember{id}")
	public String deleteMember(@PathVariable("id") Integer id) {
		memberRepo.deleteById(id);
		return "redirect:/members";

	}

	// sign up
	@GetMapping("/signup")
	public String signupUser(Model model) {
		Member member = new Member();
		model.addAttribute("member", member);
		
		List<Category> listCategories = categoryRepo.findAll();
		model.addAttribute("listCategories", listCategories);

		return "sign_up";
	}

	@PostMapping("/signupcustomer")
	public String saveUser(@Valid Member member, BindingResult bindingResult, RedirectAttributes redirectAttribute,Model model) {
		if (bindingResult.hasErrors()) {

			return "sign_up";
		}
		Member m = memberRepo.findByUsername(member.getUsername());
		if (m != null) {
			redirectAttribute.addFlashAttribute("successUsername", "Invalid Username!Try other name!");
			return "redirect:/signup";

		}

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodePassword = passwordEncoder.encode(member.getPassword());

		member.setPassword(encodePassword);
		member.setRole("ROLE_USER");

		memberRepo.save(member);
		redirectAttribute.addFlashAttribute("successUsername", "User registered!");
		List<Category> listCategories = categoryRepo.findAll();
		model.addAttribute("listCategories", listCategories);

		return "redirect:/login";

	}

	
}
