package com.intern.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intern.model.DatabaseFile;
import com.intern.repository.DatabaseFileRepository;

@Service
public class DatabaseFileService {

	@Autowired
	private DatabaseFileRepository dbFileRepository;

	public DatabaseFile storeFile(Long id, String fileName, int type) {
		DatabaseFile dbFile = new DatabaseFile(id, fileName, type);
		return dbFileRepository.save(dbFile);

	}

	public List<DatabaseFile> getFiles(Long id) {
		return dbFileRepository.getFiles(id);
	}
	
	public String getFilesc(Long id) {
		return dbFileRepository.getFilesc(id);
	}
	
	public String getFileName(Long id, Integer type) {
		return dbFileRepository.getFileName(id, type);
	}
}