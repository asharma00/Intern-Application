package com.intern.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MentorHistoryKey implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(name="student_id")
	private Long studentId;
	@Column(name="mentor_email")
	private String mentorEmail;
	@Column(name="from_date")
	private Date fromDate;
	
	
	public MentorHistoryKey()
	{
		
	}


	public MentorHistoryKey(Long studentId, String mentorEmail, Date fromDate) {
		super();
		this.studentId = studentId;
		this.mentorEmail = mentorEmail;
		this.fromDate = fromDate;
	}


	public Long getStudentId() {
		return studentId;
	}


	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}


	public String getMentorEmail() {
		return mentorEmail;
	}


	public void setMentorEmail(String mentorEmail) {
		this.mentorEmail = mentorEmail;
	}


	public Date getFromDate() {
		return fromDate;
	}


	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
}
