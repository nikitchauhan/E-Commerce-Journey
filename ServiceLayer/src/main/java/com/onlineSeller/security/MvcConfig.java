package com.onlineSeller.security;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SuppressWarnings("deprecation")
public class MvcConfig {

	
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**","/public/**")
                .addResourceLocations("classpath:/static/");
        
        
        
	}
	
}
