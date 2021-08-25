



package com.intern.repository;


import java.util.ArrayList;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.intern.model.Responses;
import com.intern.model.ResponsesCompositeKey;



@Repository
public interface Responserepository extends JpaRepository<Responses,ResponsesCompositeKey>{  //here Responses is the name of model class where table is mapped and long is the datatype of its primary key ie id
	//spring automatically implements this repository interface in a bean that has the same name (with a change in the case ie responserepository
	@Query(value = "SELECT * FROM response  WHERE qid = :id and uid=:uid ", nativeQuery = true) //add and userid=something
	ArrayList<Responses> findanswered(@Param("id") int id,@Param("uid") long uid);//change this

	@Transactional
	void deleteByUid(Long userid);

}