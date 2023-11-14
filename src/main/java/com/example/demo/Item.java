package com.example.demo;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Item {
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	 	private int id;
	
	@NotNull
	@NotEmpty(message = "Item not empty!")
	@Size(min=5,max=50,message = "Length should be between 5 and 50!")
	private String name;
	
	@NotNull
	@NotEmpty(message = "Color not empty!")
	@Size(min=5,max=20,message = "Length should be between 5 and 20!")
	private String color;
	
	
	@Min(value=1,message = "Quantity should be a positive whole number!")
	private int quantity;
	
	@DecimalMin(value="0.1",message = "Price should be positive numerical value! ")
    private double price;
	
	
	@Min(value=0,message = "Size should be a positive whole number!")
	private int size;
	
	/* @NotNull(message = "Image not empty") */
	private String imgName;
	
	private String description;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
@ManyToOne
@JoinColumn(name="category_id", nullable=false)

@NotNull(message = "Category not empty")
private Category category;

	public Category getCategory() {
	return category;
}
public void setCategory(Category category) {
	this.category = category;
}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public Item(int id, String name, String color, int quantity, double price, int size, String imgName) {
		super();
		this.id = id;
		this.name = name;
		this.color = color;
		this.quantity = quantity;
		this.price = price;
		this.size = size;
		this.imgName = imgName;
	}
public Item() {
	// TODO Auto-generated constructor stub
}
}
