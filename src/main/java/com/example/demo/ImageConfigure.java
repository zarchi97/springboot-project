package com.example.demo;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class ImageConfigure implements WebMvcConfigurer{
@Override

public void addResourceHandlers(ResourceHandlerRegistry registry) {
	// TODO Auto-generated method stub
	registry.addResourceHandler("/uploads/**").addResourceLocations("file:uploads/")
	.setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
}
	
}
