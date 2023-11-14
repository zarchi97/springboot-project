package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
	@NotNull
	@NotEmpty(message = "Name not empty!")
	@Size(min=3,max=50,message = "Length should be between 3 and 50!")
private String name;
	@NotNull
	@NotEmpty(message = "Username not empty!")
	@Size(min=3,max=20,message = "Length should be between 3 and 20!")
private String username;
	@NotNull
	@NotEmpty(message = "Password not empty!")
	private String password;
	@NotNull
	@NotEmpty(message = "Email not empty!")
	private String email;
	
private String role;
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
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public Member(int id, String name, String username, String password, String email, String role) {
	super();
	this.id = id;
	this.name = name;
	this.username = username;
	this.password = password;
	this.email = email;
	this.role = role;
}
public Member() {
	// TODO Auto-generated constructor stub
}

}
