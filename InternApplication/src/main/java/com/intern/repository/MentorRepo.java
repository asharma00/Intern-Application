package com.intern.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intern.model.Mentor;


@Repository
public interface MentorRepo extends JpaRepository<Mentor, String> {

	
	@Query(value = "select  m.email, m.name, m.designation, u.id from mentor m, users u where m.email=u.mentor_email and u.email=:email ", nativeQuery = true)
	ArrayList<Object> getExistingMentor(@Param("email") String email);
	

	
	//for deleting mentor details from mentor table 
	@Transactional
	@Modifying
	@Query(value="delete from mentor where email=:oldEmail",nativeQuery=true)
	int deleteMentorForUpdate(@Param("oldEmail") String oldEmail);
	
	//for updating mentor email from user table
	@Transactional
	@Modifying
	@Query(value="update mentor set flag=:value, entry_by=:currentUser, entry_time=:entryDateTime where email=:oldEmail",nativeQuery=true)
	int updateMentorFlag(@Param("oldEmail") String oldEmail,@Param("value") int value, @Param("currentUser") String currentUser, @Param("entryDateTime") Date entryDateTime);
	
	List<Mentor> findByFlagValue(int value);
	
}
