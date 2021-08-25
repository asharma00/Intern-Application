package com.intern.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Table;



import javax.persistence.Id;
import javax.persistence.IdClass;

//primary key -> student_id and date

@Entity()
@Table(name = "roaster")
@IdClass(RoasterCompositeKey.class)
public class Roaster {
	@Id
	@Column(name = "student_id")
	private Long studentId;
	
	@Id
	@Column(name = "attendance_date")
	private Date attendanceDate;
	
	@Column(name = "roaster_plan")
	private String roasterPlan;
	
	//roaster entered by which logged in user with time stamp
	@Column(name = "entry_by")
	private String entryByUser;
	@Column(name = "entry_time", columnDefinition="timestamp")
	private Date entryTimeUser; 

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public String getRoasterPlan() {
		return roasterPlan;
	}

	public void setRoasterPlan(String roasterPlan) {
		this.roasterPlan = roasterPlan;
	}
	
	public Roaster()
	{
		
	}

	public Roaster(Long studentId, Date attendanceDate, String roasterPlan, String entryByUser, Date entryTimeUser) {
		super();
		this.studentId = studentId;
		this.attendanceDate = attendanceDate;
		this.roasterPlan = roasterPlan;
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
	
}
