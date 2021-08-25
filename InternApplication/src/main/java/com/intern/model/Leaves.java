package com.intern.model;





import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


@Entity  //specifies that the class is an entity and is mapped to a database table 
@Table(name="leaves")  //specifies the name of database table to be used for mapping


@IdClass(LeavesCompositeKey.class)


public class Leaves {
	
	@Id    //specifies primary key of an entity
	//@GeneratedValue(strategy=GenerationType.AUTO)  //provides for specification of generation strategies for the value of primary keys
	private Long uid;
	@Id 
	@Column(name = "from_date")
	private java.util.Date fromDate;
	

	private String approved_by;
	private String status;
	private String reason_for_leave;
	private Float total_days;
	//private java.util.Date exam_date;
	@Column(name = "to_date")
	private java.util.Date toDate;
	private String leaveFromTime;
	private String leaveToTime;
	private String reason_for_rejection;
	
	//leave entered by which logged in user with time stamp
	@Column(name = "entry_time", columnDefinition="timestamp")
	private Date entryTimeUser;

public Leaves() {
		
	}



public Leaves(Long uid,String approved_by,String status,String reason_for_leave,Float total_days,java.util.Date fromDate,java.util.Date toDate, String leaveFromTime,String leaveToTime,String reason_for_rejection, Date entryTimeUser)

{   super();
	this.uid=uid;
	this.approved_by=approved_by;
	this.fromDate=fromDate;
	this.reason_for_leave=reason_for_leave;
	this.status=status;
	this.toDate=toDate;
	this.total_days=total_days;
	this.leaveFromTime=leaveFromTime;
	this.leaveToTime=leaveToTime;
	this.reason_for_rejection=reason_for_rejection;
	this.entryTimeUser = entryTimeUser;
}



public Date getEntryTimeUser() {
	return entryTimeUser;
}



public void setEntryTimeUser(Date entryTimeUser) {
	this.entryTimeUser = entryTimeUser;
}



	public String getReason_for_rejection() {
	return reason_for_rejection;
}



public void setReason_for_rejection(String reason_for_rejection) {
	this.reason_for_rejection = reason_for_rejection;
}



	public Long getUid() {
		return uid;
	}






	public void setUid(Long uid) {
		this.uid = uid;
	}






	public String getApproved_by() {
		return approved_by;
	}






	public void setApproved_by(String approved_by) {
		this.approved_by = approved_by;
	}






	public String getStatus() {
		return status;
	}






	public void setStatus(String status) {
		this.status = status;
	}






	public String getReason_for_leave() {
		return reason_for_leave;
	}






	public void setReason_for_leave(String reason_for_leave) {
		this.reason_for_leave = reason_for_leave;
	}






	public Float getTotal_days() {
		return total_days;
	}






	public void setTotal_days(Float total_days) {
		this.total_days = total_days;
	}






	public java.util.Date getFromDate() {
		return fromDate;
	}






	public void setFromDate(java.util.Date fromDate) {
		this.fromDate = fromDate;
	}






	public java.util.Date getToDate() {
		return toDate;
	}






	public void setToDate(java.util.Date toDate) {
		this.toDate = toDate;
	}



	public String getLeaveFromTime() {
		return leaveFromTime;
	}



	public void setLeaveFromTime(String leaveFromTime) {
		this.leaveFromTime = leaveFromTime;
	}



	public String getLeaveToTime() {
		return leaveToTime;
	}



	public void setLeaveToTime(String leaveToTime) {
		this.leaveToTime = leaveToTime;
	}



	
	
	
}
