package com.intern.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intern.repository.UserRepository;
import com.intern.repository.UsersRolesRepo;

@RestController
public class UserDetailsController {

	

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UsersRolesRepo usersRolesRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/email")
	public String currentUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = null;
		if (principal instanceof User) {
			email = ((User) principal).getUsername();
		}
		return email;
	}

	@GetMapping("/currentUser")
	public Object currentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = null;
		if (principal instanceof User) {
			email = ((User) principal).getUsername();
			System.out.println("Email of current user> "+email);
			return this.userRepository.findApplicationAndUser(email);
		}
		
		return null;
	}
	
	
	

	

	@PostMapping("/changePassword")
	public int changePassword(@RequestParam String currentPassword, @RequestParam String newPassword) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = null;
		if (principal instanceof User) {
			email = ((User) principal).getUsername();
			com.intern.model.User user = this.userRepository.findByEmail(email);
			if (passwordEncoder.matches(currentPassword, user.getPassword())) {
				user.setPassword(passwordEncoder.encode(newPassword));
				this.userRepository.save(user);
			} else {
				return 1;
			}

		}
		return 0;
	}
	
	
}
