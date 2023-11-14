package com.example.demo;

import java.security.Principal;

 

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.websocket.server.PathParam;

@Controller
public class CartItemController {
	@Autowired
	private CartItemRepository cartItemRepo;
	@Autowired
	private OrderItemRepository orderRepo;
	@Autowired
	private ItemRepository itemRepo;
	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private MemberRepository memberRepo;
@Autowired
private JavaMailSender javaMailSender;
	@GetMapping("/cart")
	public String showCart(Model model, Principal principal) {

		// get currently logged in user
		MemberDetails loggedInMember = (MemberDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		int loggedInMemberId = loggedInMember.getMember().getId();
		// get shopping cart items added by this user
		// *Hint You will need to use the method we added in the CartItemRepository
		List<CartItem> cartItemList = cartItemRepo.findAllByMemberId(loggedInMemberId);
		List<Category> listCategories = categoryRepo.findAll();
		model.addAttribute("listCategories", listCategories);
		// add the shopping cart items to the model

		double total = 0;
		for (CartItem cartItem : cartItemList) {
			// calculate the total cost of all item in the shopping cart
			double num = cartItem.getQuantity() * cartItem.getItem().getPrice();
			DecimalFormat df = new DecimalFormat("#.##");
			String formattedNum = df.format(num);
			double trimmedNum = Double.parseDouble(formattedNum);

			cartItem.setSubtotal(trimmedNum);

			total += cartItem.getSubtotal();

			DecimalFormat df1 = new DecimalFormat("#.##");
			String formattedNum1 = df1.format(total);
			double trimmedNum1 = Double.parseDouble(formattedNum1);

			/* String convertedToString = String.valueOf(total); */

		}
		model.addAttribute("cartItemList", cartItemList);

		// Add the shopping cart total to the model
		model.addAttribute("total", total);
		return "cart";

	}

	@PostMapping("/cartadd{id}")
	public String addToCart(@PathVariable("id") int id, @RequestParam("quantity") int quantity,
			@RequestParam("color") String color, Principal principal, Model model) {

		// get currently logged in user
		MemberDetails loggedInMember = (MemberDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		int loggedInMemberId = loggedInMember.getMember().getId();
		List<Category> listCategories = categoryRepo.findAll();
		model.addAttribute("listCategories", listCategories);
		// Check in the cartItemRepo if item was previously added by user
		// Hint* we will need to write a new method in the cartItemRepository
		boolean flag = false;
		CartItem cItem = new CartItem();
		List<CartItem> cartItemList = cartItemRepo.findAllByMemberId(loggedInMemberId);
		for (CartItem cartItem : cartItemList) {
			if (cartItem.getItem().getId() == id) {
				cItem = cartItem;
				flag = true;
				break;
			}
		}

		// if the item was previously added, then we get the quantity that was
		// previously added and increase that
		// save the cartItem object back to the repository

		if (flag) {
			cItem.setQuantity(cItem.getQuantity() + quantity);
			/* cItem.setColor(color); */
			cartItemRepo.save(cItem);
		}
		// if the item was NOT previously added.
		// then prepare the item and member objects
		else {
			Item item = itemRepo.getReferenceById(id);
			Member member = memberRepo.getReferenceById(loggedInMemberId);

			// create a new cartitem object
			CartItem cartItem = new CartItem();
			// set the item and member as well as the quantity in the new cartItem
			// object
			cartItem.setItem(item);
			cartItem.setMember(member);
			cartItem.setQuantity(quantity);
			cartItem.setColor(color);
			// save the new cartItem object to the Repo
			cartItemRepo.save(cartItem);
		}
		return "redirect:/cart";

	}

	@PostMapping("/cartupdate{id}")
	public String updateCart(@PathVariable("id") int cartItemId, @PathParam("qty") int qty,Model model) {

		// get cartItem object by cartitem's id
		CartItem cartItem = cartItemRepo.getReferenceById(cartItemId);
		List<Category> listCategories = categoryRepo.findAll();
		model.addAttribute("listCategories", listCategories);
		// set the quantity of the cartItem object retrieved

		if (qty > 0) {
			cartItem.setQuantity(qty);
			cartItemRepo.save(cartItem);
		} else {
			cartItemRepo.deleteById(cartItemId);
		}
		// save the cartItem back to the cartRepository

		return "redirect:/cart";

	}

	@GetMapping("/cartremove{id}")
	public String removeFromCart(@PathVariable("id") int cartItemId) {
		cartItemRepo.deleteById(cartItemId);
		return "redirect:/cart";

	}

	public void sendEmail(String to, String subject, String body) {
	  SimpleMailMessage msg = new SimpleMailMessage();
	  msg.setTo(to);
	  msg.setSubject(subject); 
	  msg.setText(body); 
		/* System.out.println("Sending"); */
	  javaMailSender.send(msg); 
		/* System.out.println("Sent"); */
	  }
	 
	@PostMapping("/paypal")
	public String processOrder(Model model, @RequestParam("cartTotal") double cartTotal,
			@RequestParam("orderId") String orderId, @RequestParam("transactionId") String transactionId,
			Principal principal) {
		//dropdown
		List<Category> listCategories = categoryRepo.findAll();
		model.addAttribute("listCategories", listCategories);
		/*
		 * System.out.println(orderId); System.out.println(transactionId);
		 */
		// Get currently logged in user
		MemberDetails loggedInMember = (MemberDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		int loggedInMemberId = loggedInMember.getMember().getId();
		// Retrieve cart items purchased
		List<CartItem> cartItemList = cartItemRepo.findAllByMemberId(loggedInMemberId);
		// Get member object
		Member member = memberRepo.getReferenceById(loggedInMemberId);
		// Loop to iterate through all cart items
		for (int i = 0; i < cartItemList.size(); i++) {
			// for (CartItem cartItem : cartItemList) {
			// Retrieve details about current cart item
			CartItem cartItem = cartItemList.get(i);
			double subtotal = 0;
			double total = 0;
			int qty = cartItem.getQuantity();
			String color=cartItem.getColor();
			double price = cartItem.getItem().getPrice();
			subtotal = qty * price;
			total += subtotal;
			cartItem.setSubtotal(subtotal);
			// Update item table
			Item item = itemRepo.getReferenceById(cartItem.getItem().getId());
			item.setQuantity(item.getQuantity() - qty);
			itemRepo.save(item);
           //Add item to order table
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderId(orderId);
			orderItem.setQuantity(qty);
			orderItem.setSubtotal(subtotal);
			orderItem.setTransactionId(transactionId);
			orderItem.setItem(item);
			orderItem.setMember(member);
			orderItem.setColor(color);
			orderRepo.save(orderItem);
		}
		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("orderId", orderId);
		model.addAttribute("transactionId", transactionId);
//clear cart items belonging to member
		cartItemRepo.deleteAll(cartItemList);
//cartItemList.clear();
//model.addAttribute("cartItemList", cartItemList);
//Pass info to view to display success page
		model.addAttribute("success", "THANK YOU FOR YOUR ORDER!");
//Send email
		String subject = "Order is confirmed!";
		String body = "Thank you for your order!\n" + "Order ID: " + orderId + "\n" + "Transaction ID: "
				+ transactionId;
		String to = member.getEmail();
		sendEmail(to, subject, body); 
		return "success";
	}

	

}
