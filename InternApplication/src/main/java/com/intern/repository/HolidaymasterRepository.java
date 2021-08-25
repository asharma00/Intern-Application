package com.intern.repository;

import java.util.List;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intern.model.Holidaymaster;




@Repository
public interface HolidaymasterRepository extends JpaRepository <Holidaymaster, Long> { 
    List<Holidaymaster> findAll(); 
    
    
	
}