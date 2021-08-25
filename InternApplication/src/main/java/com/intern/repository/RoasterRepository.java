package com.intern.repository;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.intern.model.Roaster;
import com.intern.model.RoasterCompositeKey;

@Repository
public interface RoasterRepository extends JpaRepository<Roaster, RoasterCompositeKey> {

	
	
	@Query(value = "select r.student_id,u.email, u.full_name, r.roaster_date, r.roaster_plan from roaster r, users u \r\n" + 
			"where EXTRACT(MONTH FROM roaster_date)=1 and EXTRACT(YEAR FROM roaster_date)=2021 and u.id=r.student_id and project=:projectName order by roaster_date, full_name", nativeQuery = true)
	ArrayList<Object> findAllRoaster(@Param("projectName") String projectName);
	
	@Query(value="select id, email, full_name from users where relieve_date is null and project=:projectName", nativeQuery=true )
	ArrayList<Object>   getAllIntern(@Param("projectName") String projectName)  ;
	
	@Query(value="select id, email, full_name from users where relieve_date is null and project=:projectName and email=:email", nativeQuery=true )
	ArrayList<Object>   getInternByMail(@Param("projectName") String projectName, @Param ("email") String email)  ;

	@Query(value="select distinct roaster_plan from roaster where student_id=:studentid and roaster_date=:dateValue", nativeQuery = true)
	String getWorkFrom(Long studentid, Date dateValue);
}
