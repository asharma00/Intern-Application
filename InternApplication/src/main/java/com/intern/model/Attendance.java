package com.intern.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Table;



import javax.persistence.Id;
import javax.persistence.IdClass;

//primary key -> student_id and date

@Entity()
@Table(name = "attendance")
@IdClass(AttendanceCompositeKey.class)
public class Attendance {
	
	@Id
	@Column(name = "student_id")
	private Long studentId;
	
	@Id
	@Column(name = "attendance_date")
	private Date attendanceDate;
	
	@Column(name = "attendance_in_time")
	private String attendanceInTime;
	
	@Column(name = "attendance_out_time")
	private String attendanceOutTime;
	
	@Column(name = "attendance_status")
	private String attendanceStatus;
	//pending, approved, rejected
	@Column(name = "work_from_checkin")
	private String workFromCheckin;
	@Column(name = "late_checkin_reason")
	private String lateCheckinReason;
	@Column(name = "work_from_checkout")
	private String workFromCheckout;
	@Column(name = "early_checkout_reason")
	private String earlyCheckoutReason;
	@Column(name = "target_for_day")
	private String targetForDay;
	@Column(name = "work_status")
	private String workStatus;
	
	//attendance entered by which logged in user with time stamp
	@Column(name = "entry_by")
	private String entryByUser;
	@Column(name = "entry_time", columnDefinition="timestamp")
	private Date entryTimeUser;  
	
	public Attendance() {
		
	}

	public Attendance(Long studentId, Date attendanceDate, String attendanceInTime, String attendanceOutTime, String attendanceStatus, String workFromCheckin,String lateCheckinReason,String  workFromCheckout,String earlyCheckoutReason, String targetForDay, String workStatus, String entryByUser, Date entryTimeUser) {
		super();
		this.studentId = studentId;
		this.attendanceDate = attendanceDate;
		this.attendanceInTime = attendanceInTime;
		this.attendanceOutTime = attendanceOutTime;
		this.attendanceStatus = attendanceStatus;
		this.workFromCheckin=workFromCheckin;
		this.lateCheckinReason=lateCheckinReason;
		this.workFromCheckout=workFromCheckout;
		this.earlyCheckoutReason=earlyCheckoutReason;
		this.targetForDay=targetForDay;
		this.workStatus=workStatus;
		this.entryByUser=entryByUser;
		this.entryTimeUser=entryTimeUser;
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

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceInDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public String getAttendanceInTime() {
		return attendanceInTime;
	}

	public void setAttendanceInTime(String attendanceInTime) {
		this.attendanceInTime = attendanceInTime;
	}

	public String getAttendanceOutTime() {
		return attendanceOutTime;
	}

	public void setAttendanceOutTime(String attendanceOutTime) {
		this.attendanceOutTime = attendanceOutTime;
	}

	public String getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(String attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	

	public String getWorkFromCheckin() {
		return workFromCheckin;
	}

	public void setWorkFromCheckin(String workFromCheckin) {
		this.workFromCheckin = workFromCheckin;
	}

	public String getWorkFromCheckout() {
		return workFromCheckout;
	}

	public void setWorkFromCheckout(String workFromCheckout) {
		this.workFromCheckout = workFromCheckout;
	}

	public String getLateCheckinReason() {
		return lateCheckinReason;
	}

	public void setLateCheckinReason(String lateCheckinReason) {
		this.lateCheckinReason = lateCheckinReason;
	}

	public String getEarlyCheckoutReason() {
		return earlyCheckoutReason;
	}

	public void setEarlyCheckoutReason(String earlyCheckoutReason) {
		this.earlyCheckoutReason = earlyCheckoutReason;
	}

	public String getTargetForDay() {
		return targetForDay;
	}

	public void setTargetForDay(String targetForDay) {
		this.targetForDay = targetForDay;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	

}
