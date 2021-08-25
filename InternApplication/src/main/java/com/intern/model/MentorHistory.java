package com.intern.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@Table(name="mentor_history") 
public class MentorHistory implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.persistence.Id
	@EmbeddedId
	private MentorHistoryKey id;
	@Column(name="to_date")
	private Date toDate;
public 	MentorHistory()
{
	
}
public MentorHistory(MentorHistoryKey id, Date toDate) {
	super();
	this.id = id;
	this.toDate = toDate;
}
public MentorHistoryKey getId() {
	return id;
}
public void setId(MentorHistoryKey id) {
	this.id = id;
}
public Date getToDate() {
	return toDate;
}
public void setToDate(Date toDate) {
	this.toDate = toDate;
}
	
}
