package com.intern.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) 
	{
		registry.addViewController("/").setViewName("home");
		//welcome page index^
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		//this tells what the welcome page
		registry.addViewController("/toregister").setViewName("index");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/register").setViewName("registration");
		registry.addViewController("/admin").setViewName("admin");
		registry.addViewController("/instruction").setViewName("instruction");
		//registry.addViewController("/login").setViewName("admin");
		registry.addViewController("/user").setViewName("user");
		registry.addViewController("/mentorPage").setViewName("mentorpage");  //for mentor-activities page
		
	}
}	
