package com.example.demo;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
@Entity
public class CartItem {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
	
@ManyToOne
@JoinColumn(name="member_id",nullable=false)
private Member member;

@ManyToOne
@JoinColumn(name="item_id",nullable=false)
	private Item item;

private int quantity;
private String color;
@Transient
private double subtotal;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Member getMember() {
	return member;
}
public void setMember(Member member) {
	this.member = member;
}
public Item getItem() {
	return item;
}
public void setItem(Item item) {
	this.item = item;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
public String getColor() {
	return color;
}
public void setColor(String color) {
	this.color = color;
}
public double getSubtotal() {
	return subtotal;
}
public void setSubtotal(double subtotal) {
	this.subtotal = subtotal;
}
public CartItem(int id, Member member, Item item, int quantity, String color, double subtotal) {
	super();
	this.id = id;
	this.member = member;
	this.item = item;
	this.quantity = quantity;
	this.color = color;
	this.subtotal = subtotal;
}
public CartItem() {
	// TODO Auto-generated constructor stub
}
public void Round(CartItem cartItem, int i) {
	// TODO Auto-generated method stub
	
}
}
