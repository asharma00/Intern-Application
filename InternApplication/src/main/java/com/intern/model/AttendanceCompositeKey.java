package com.intern.model;

import java.io.Serializable;
import java.util.Date;

//composite classes must be serializable
public class AttendanceCompositeKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long studentId;
	
	private Date attendanceDate;
	

	
	public AttendanceCompositeKey() {
	}

	public AttendanceCompositeKey(Long studentId, Date attendanceDate) {
		super();
		this.studentId = studentId;
		this.attendanceDate = attendanceDate;
	}

	

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

	

	
	
	
	
	
	
}

