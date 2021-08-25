package com.intern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intern.model.UsersRoles;

@Repository
public interface UsersRolesRepo extends JpaRepository<UsersRoles, Long> {

	
	
}
