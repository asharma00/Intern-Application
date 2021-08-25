package com.intern.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "file_names")
public class DatabaseFile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "file_id")
	private long fileId;
	@Column(name = "id")
	private long id;
	@Column(name = "file_name")
	private String fileName;
	@Column(name = "type")
	private int type;
	
	public DatabaseFile() {
		
	}
	
	public DatabaseFile(long id, String fileName, int type) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.type = type;
	}
	
	public long getFileId() {
		return fileId;
	}
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
