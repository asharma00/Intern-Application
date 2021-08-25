package com.intern.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.intern.model.User;
import com.intern.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);

	User findByEmail(String username);
}
