package com.intern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.intern.model.DatabaseFile;

@Repository
public interface DatabaseFileRepository extends JpaRepository<DatabaseFile, String>{

	@Query(value = "SELECT * FROM file_names WHERE id=:id", nativeQuery = true)
	List<DatabaseFile> getFiles(@Param("id") Long id);
	
	@Query(value = "SELECT file_name FROM file_names WHERE id=:id and type='4'", nativeQuery = true)
	String getFilesc(@Param("id") Long id);
	
	@Query(value = "SELECT file_name FROM file_names WHERE id=:id and type=:type", nativeQuery = true)
	String getFileName(Long id, Integer type);
	
}