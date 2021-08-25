package com.intern.repository;


import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intern.model.QuestionPaper;


@Repository
public interface QuestionRepository extends JpaRepository <QuestionPaper, Integer> { 
    List<QuestionPaper> findAll(); 
    
    @Query(value = "SELECT * FROM question_paper  WHERE topic = :s ", nativeQuery = true)  //If you are using @Param annotation, you should use same as ur entity class parameter 
	ArrayList<QuestionPaper> findexamtopicwise(@Param("s") String s);

    @Query(value = "SELECT q.id,q.correctans,q.marks,q.option1,q.option2,q.option3,q.option4,q.question,q.topic FROM question_paper q INNER JOIN questionset t on q.id=t.questionids WHERE q.topic=:s and t.setid=:set1 ", nativeQuery = true)  //If you are using @Param annotation, you should use same as ur entity class parameter 
	ArrayList<QuestionPaper> findexamsetwise(@Param("s") String s,@Param("set1") long set1);
    
	@Query(value="SELECT answer, correctans,topic,marks FROM question_paper e INNER JOIN response r ON e.id = r.qid where r.uid=:id", nativeQuery =true)
	ArrayList<Object> resultcalc(@Param("id") long id);
	
	@Query(value="SELECT topic,marks from question_paper ", nativeQuery =true)
	ArrayList<Object> resultadmin();
	
	@Query(value="SELECT q.topic,SUM(q.marks) from question_paper q inner join questionset s on q.id=s.questionids where setid=:sid group by topic", nativeQuery =true)
	ArrayList<Object> resultadminset(@Param("sid") long sid);
	
	@Transactional
	@Modifying
	@Query(value = "delete from question_paper where id = :id", nativeQuery = true)	
	int deleteById(@Param("id") long id);
	
	@Query(value="SELECT * from question_paper ", nativeQuery =true)
	ArrayList<Object> displayquestion();
	
	List<QuestionPaper> findAllByOrderByIdAsc();

//   Integrate
	@Query(value = "SELECT q.id,q.correctans,q.marks,q.option1,q.option2,q.option3,q.option4,q.question,q.topic FROM question_paper q INNER JOIN questionset t on q.id=t.questionids WHERE t.setid=:id order by id asc", nativeQuery = true)  //If you are using @Param annotation, you should use same as ur entity class parameter 
	ArrayList<QuestionPaper> findexamsetwiseAll(@Param("id") Long id);
}