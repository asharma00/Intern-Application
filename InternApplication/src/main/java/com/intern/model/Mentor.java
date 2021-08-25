package com.intern.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mentor")
public class Mentor {

	private String name;
	@Id
	
	@Column(name = "email")
	private String email;
	private String designation;
	
	@Column(name = "flag", columnDefinition = "integer default 1")    //1 represents mentor position
	private int flagValue;   							//0 represents some other position but record still present in mentor table
	
	//mentor entered by which logged in user with time stamp
	@Column(name = "entry_by")
	private String entryByUser;
	@Column(name = "entry_time", columnDefinition="timestamp")
	private Date entryTimeUser;
	
	public Mentor() {
		super();
	}
	public Mentor(String name, String email, String designation, int flagValue, String entryByUser, Date entryTimeUser) {
		super();
		this.name = name;
		this.email = email;
		this.designation = designation;
		this.flagValue = flagValue;
		this.entryByUser = entryByUser;
		this.entryTimeUser = entryTimeUser;
	}
	public String getEntryByUser() {
		return entryByUser;
	}
	public void setEntryByUser(String entryByUser) {
		this.entryByUser = entryByUser;
	}
	public Date getEntryTimeUser() {
		return entryTimeUser;
	}
	public void setEntryTimeUser(Date entryTimeUser) {
		this.entryTimeUser = entryTimeUser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public int getFlagValue() {
		return flagValue;
	}
	public void setFlagValue(int flagValue) {
		this.flagValue = flagValue;
	}
	
}
