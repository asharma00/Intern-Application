package com.intern.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intern.model.Role;





@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE role SET name ='ROLE_USER' WHERE id = :rid", nativeQuery = true)	
	int changerole(@Param("rid") long rid);
}
