
package com.intern.repository;


import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.intern.model.QuestionSet;


@Repository
public interface QuestionSetRepository extends JpaRepository <QuestionSet,Integer> { 
  
	@Query(value = "SELECT q.question, q.topic from question_paper q INNER JOIN questionset s ON q.id=s.questionids where setid=:setid", nativeQuery = true) 
	ArrayList<Object> findQues(@Param("setid") long setid);
	
	
}