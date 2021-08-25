package com.intern.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.intern.model.MentorHistory;
import com.intern.model.MentorHistoryKey;

public interface MentorHistoryRepo extends JpaRepository<MentorHistory, MentorHistoryKey>{

	
	
	@Query(value = "select * from mentor_history where student_id = :studentId and mentor_email=:existingMentor and to_date is null limit 1", nativeQuery = true)
	MentorHistory findMentor(@Param("studentId") Long studentId, @Param("existingMentor") String existingMentor);
	
	
	//for updating mentor email
	@Transactional
	@Modifying
	@Query(value="update mentor_history set mentor_email=:newEmail where mentor_email=:oldEmail",nativeQuery=true)
	int updateMentorEmail(@Param("oldEmail") String oldEmail,@Param("newEmail") String newEmail);
	
}
