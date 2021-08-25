package com.intern.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class RoasterCompositeKey implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
      @Column(name="student_id")
	private Long studentId;
	
      @Column(name="roaster_date")
	private Date attendanceDate;

	public RoasterCompositeKey() {
	}

	public RoasterCompositeKey(Long studentId, Date attendanceDate) {
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
