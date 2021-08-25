
package com.intern.repository;

import java.util.ArrayList;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import com.intern.model.SetMaster;







@Repository
public interface SetmasterRepository extends JpaRepository <SetMaster, Long> { 
  //  List<SetMaster> findAll(); 
    
    
    
    @Query(value = "SELECT setname FROM setmaster", nativeQuery = true)
	ArrayList<Object> findallSets();
    

    @Query(value = "SELECT * FROM setmaster where id NOT IN(select DISTINCT setid from questionset)", nativeQuery = true)
    List<SetMaster> findAll();
    
    @Query(value = "SELECT * FROM setmaster where id not in (select distinct set_id from flags where set_id !=0)", nativeQuery = true)
    List<SetMaster> findAllques();
	
}