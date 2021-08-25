package com.intern.model;


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name =  "users_roles", uniqueConstraints = @UniqueConstraint(columnNames = "user_id"))
public class UsersRoles {
	@Id
	//@GeneratedValue(strategy =  GenerationType.AUTO	)
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "role_id")
	private Long roleId;
    
	public UsersRoles() {
		
	}

	public UsersRoles(Long userId, Long roleId) {
	
		this.userId = userId;
		this.roleId = roleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	
}
