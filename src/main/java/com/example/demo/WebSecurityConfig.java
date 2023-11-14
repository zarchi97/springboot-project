package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Bean
	public MemberDetailsService memberDetailsService(){
		return new MemberDetailsService();
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	@Bean
	public DaoAuthenticationProvider authenticatoionProvider() {
		DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(memberDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider ;
		
	}
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/").permitAll()
				.requestMatchers("/signup","/signupcustomer").permitAll()
				.requestMatchers("/viewuser").permitAll()
				.requestMatchers("/viewusercategory{id}").permitAll()
				.requestMatchers("/detailitem{id}").permitAll()
				.requestMatchers( "/addCategory"," /saveCategory"," /editcategory/*","/deletecategory/*", "/category").hasRole("ADMIN")
				.requestMatchers("/item").hasRole("ADMIN")
				.requestMatchers("/allcart").hasRole("ADMIN") 
				.requestMatchers("/cart").hasRole("USER")
				.requestMatchers("/addItem","/saveItem","/edititem/*","deleteitem/*").hasRole("ADMIN")
				.requestMatchers("/members","/savemember","/addmember","/deletemember","/editmember").hasAnyRole("ADMIN","USER")
				.requestMatchers("/bootstrap-5.3.1-dist/*/*").permitAll()
				.requestMatchers("/about").permitAll()
				.requestMatchers("/CSS/*").permitAll()
				.requestMatchers("/img/*").permitAll()
				.anyRequest().authenticated()
				)
		.formLogin((form) -> form.loginPage("/login").permitAll()
				.defaultSuccessUrl("/", true)
				)
		.logout((logout) -> logout.logoutUrl("/logout").permitAll())
		.exceptionHandling().accessDeniedPage("/403");

		return http.build();
		
	}	
	}
	

