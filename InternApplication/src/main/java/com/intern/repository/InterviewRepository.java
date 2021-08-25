package com.intern.repository;

import java.util.ArrayList;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.intern.model.Interview;




@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long>{
	
	@Query(value="SELECT * FROM interview WHERE id =:id ", nativeQuery = true)
	ArrayList<Interview> getmarks(@Param("id") Long id); 
	
	
}
