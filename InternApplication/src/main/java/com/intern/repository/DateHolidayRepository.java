package com.intern.repository;


import java.util.ArrayList;
import java.util.Date;

import com.intern.model.DatewiseholidayId;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intern.model.Datewiseholiday;


@Repository
public interface DateHolidayRepository extends JpaRepository <Datewiseholiday, DatewiseholidayId>{
	List<Datewiseholiday> findAll();

	
	@Query(value = "SELECT h.holidaydate,e.holidaydescription,e.holidaytype  FROM DATEWISEHOLIDAY h INNER JOIN HOLIDAYMASTER e ON h.id=e.id WHERE EXTRACT(YEAR FROM h.holidaydate)=:year order by holidaydate", nativeQuery = true)
	ArrayList<Object> getAllHolidays(@Param("year") int year);
	
	@Query(value = "SELECT e.holidaydescription,d.holidaydate,e.holidaytype,d.id FROM DATEWISEHOLIDAY d INNER JOIN HOLIDAYMASTER e ON d.id=e.id WHERE EXTRACT(YEAR FROM d.holidaydate)=:year order by holidaydate", nativeQuery = true) 
	ArrayList<Object> findYear(@Param("year") int year);
	
	
	@Query(value = "select * from datewiseholiday", nativeQuery = true)
	ArrayList<Object> check();
	
	@Query(value = "select id from datewiseholiday where holidaydate=:x1", nativeQuery = true)
	Long findhol (@Param("x1") java.util.Date x1);
	
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM datewiseholiday WHERE id=:id and holidaydate=:x1", nativeQuery = true)	
	int deleteHoli(@Param("id") long id,@Param("x1") java.sql.Date x1);
	
	@Transactional
	@Modifying
	@Query(value = "update datewiseholiday set holidaydate=:x, entry_by=:currentUser, entry_time=:entryTime where id=:id and holidaydate=:x1", nativeQuery = true)	
	int updatehol(@Param("id") long id, @Param("x") java.util.Date x,@Param("x1") java.util.Date x1, @Param("currentUser") String currentUser, @Param("entryTime") Date entryTime);
	
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE datewiseholiday SET holidaydate=:holidaydate WHERE id = :id ", nativeQuery = true)	
	int editandsaveholiday(@Param("id") long id,@Param("holidaydate") java.sql.Date holidaydate);
	
}
