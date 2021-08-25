package com.intern.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intern.model.Application;
import com.intern.model.Flags;



@Repository
public interface Flagsrepository  extends JpaRepository<Flags,Long>{  
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE flags SET flag = 1 WHERE uid = :id", nativeQuery = true)	
	int setflag(@Param("id") long id);
	
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE flags SET total_marks =:marks,java_marks=:jmarks,android_marks=:amarks,sql_marks=:smarks,python_marks=:pmarks,cloud_marks=:cmarks,   marks_out_of =:total_marks,java_total=:java_total,android_total=:android_total,sql_total=:sql_total,python_total=:python_total,cloud_total=:cloud_total WHERE uid = :id", nativeQuery = true)	
	int saveresult(@Param("id") long id,@Param("marks") int marks,@Param("pmarks") int pmarks,@Param("cmarks") int cmarks,@Param("smarks") int smarks,@Param("jmarks") int jmarks,@Param("amarks") int amarks,@Param("total_marks") int total_marks,@Param("python_total") int python_total,@Param("cloud_total") int cloud_total,@Param("sql_total") int sql_total,@Param("java_total") int java_total,@Param("android_total") int android_total);
	
	
	@Query(value = "SELECT * FROM flags  WHERE uid = :id ", nativeQuery = true) //add and userid=something
	ArrayList<Flags> findmarks(@Param("id") long  id);
	
	
	@Query(value = "SELECT * FROM flags  WHERE uid = :id ", nativeQuery = true) 
	ArrayList<Flags> checkflag(@Param("id") long  id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE flags SET enddate=:enddate WHERE uid = :id", nativeQuery = true)	
	int saveexamenddate(@Param("id") long  id,@Param("enddate") java.util.Date enddate);
	
	@Query(value = "SELECT examdate FROM flags  WHERE uid = :id ", nativeQuery = true) 
	java.util.Date getdate(@Param("id") long  id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE flags SET startdate=:startdate WHERE uid = :id", nativeQuery = true)	
	int saveexamstartdate(@Param("id") long  id,@Param("startdate") java.util.Date startdate);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE flags SET examdate=:examStartDate WHERE uid = :id", nativeQuery = true)	
	int saveNewExamDate(@Param("id") long  id,@Param("examStartDate") java.util.Date examStartDate);
	
	//IntegrateLatest
	@Query(value = "SELECT flag FROM flags  WHERE uid = :userid ", nativeQuery = true) 
	Integer getIdforExam(@Param("userid") long  userid);
	
	@Query(value = "SELECT set_id FROM flags  WHERE uid = :id ", nativeQuery = true) 
	Long findsetofuser(@Param("id") long  id);
    
	public Flags findByUid(Long uid);
}
