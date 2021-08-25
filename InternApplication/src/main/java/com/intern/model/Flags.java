package com.intern.model;





import javax.persistence.Entity;

import javax.persistence.Id;

import javax.persistence.Table;


@Entity  //specifies that the class is an entity and is mapped to a database table 
@Table(name="flags")  //specifies the name of database table to be used for mapping

public class Flags {
	
	@Id    //specifies primary key of an entity
	//@GeneratedValue(strategy=GenerationType.AUTO)  //provides for specification of generation strategies for the value of primary keys
	private Long uid;
	
	private int flag;
	
	private int total_marks;
	private int sql_marks;
	private int java_marks;
	private int android_marks;
	private int cloud_marks;
	private int python_marks;
	
	private int marks_out_of;
	private int sql_total;
	private int java_total;
	private int android_total;
	private int cloud_total;
	private int python_total;
	private java.util.Date examdate;
	private java.util.Date startdate;
	private java.util.Date enddate;
	private Long set_id;
	private java.util.Date interview_time;
	private String exam_meeting_link;
	private String interview_meeting_link;
	public int getMarks_out_of() {
		return marks_out_of;
	}


	public void setMarks_out_of(int marks_out_of) {
		this.marks_out_of = marks_out_of;
	}


	public int getSql_total() {
		return sql_total;
	}


	public void setSql_total(int sql_total) {
		this.sql_total = sql_total;
	}


	public int getJava_total() {
		return java_total;
	}


	public void setJava_total(int java_total) {
		this.java_total = java_total;
	}


	public int getAndroid_total() {
		return android_total;
	}


	public void setAndroid_total(int android_total) {
		this.android_total = android_total;
	}


	public int getCloud_total() {
		return cloud_total;
	}


	public void setCloud_total(int cloud_total) {
		this.cloud_total = cloud_total;
	}


	public int getPython_total() {
		return python_total;
	}


	public void setPython_total(int python_total) {
		this.python_total = python_total;
	}







	//private java.util.Date exam_date;
	
	public java.util.Date getStartdate() {
		return startdate;
	}


	public void setStartdate(java.util.Date startdate) {
		this.startdate = startdate;
	}


	public java.util.Date getEnddate() {
		return enddate;
	}


	public void setEnddate(java.util.Date enddate) {
		this.enddate = enddate;
	}


	public Flags()
	{
		
	}
	
	
	public java.util.Date getExamdate() {
		return examdate;
	}


	public void setExamdate(java.util.Date examdate) {
		this.examdate = examdate;
	}


	public Flags(Long uid,int flag,int total_marks,int java_marks,int sql_marks,int android_marks,int cloud_marks,int python_marks,java.util.Date examdate,Long set_id,int marks_out_of,int java_total,int sql_total,int android_total,int cloud_total,int python_total, String exam_meeting_link) {
		super();
		
		this.uid=uid;
		this.flag=flag;
		this.total_marks=total_marks;
		this.sql_marks=sql_marks;
		this.java_marks=java_marks;
		this.android_marks=android_marks;
		this.python_marks=python_marks;
		this.cloud_marks=cloud_marks;
		this.examdate= examdate;
		this.set_id=set_id;
		
		this.marks_out_of=marks_out_of;
		this.sql_total=sql_total;
		this.java_total=java_total;
		this.android_total=android_total;
		this.python_total=python_total;
		this.cloud_total=cloud_total;
		this.exam_meeting_link=exam_meeting_link;
		
		
	}
	public Flags(Long uid,int flag,int total_marks,int java_marks,int sql_marks,int android_marks,int cloud_marks,int python_marks,java.util.Date examdate,Long set_id,int marks_out_of,int java_total,int sql_total,int android_total,int cloud_total,
			int python_total, String exam_meeting_link, java.util.Date interview_time, String interview_meeting_link) {
		super();
		
		this.uid=uid;
		this.flag=flag;
		this.total_marks=total_marks;
		this.sql_marks=sql_marks;
		this.java_marks=java_marks;
		this.android_marks=android_marks;
		this.python_marks=python_marks;
		this.cloud_marks=cloud_marks;
		this.examdate= examdate;
		this.set_id=set_id;
		
		this.marks_out_of=marks_out_of;
		this.sql_total=sql_total;
		this.java_total=java_total;
		this.android_total=android_total;
		this.python_total=python_total;
		this.cloud_total=cloud_total;
		this.exam_meeting_link=exam_meeting_link;
		this.interview_time=interview_time;
		this.interview_meeting_link=interview_meeting_link;
		
	}
	
	
public Long getSet_id() {
		return set_id;
	}


	public void setSet_id(Long set_id) {
		this.set_id = set_id;
	}


public int getTotal_marks() {
		return total_marks;
	}


public void setTotal_marks(int total_marks) {
		this.total_marks = total_marks;
	}


public int getSql_marks() {
		return sql_marks;
	}



public void setSql_marks(int sql_marks) {
		this.sql_marks = sql_marks;
	}



public int getJava_marks() {
		return java_marks;
	}


public void setJava_marks(int java_marks) {
		this.java_marks = java_marks;
	}



public int getAndroid_marks() {
		return android_marks;
	}



public void setAndroid_marks(int android_marks) {
		this.android_marks = android_marks;
	}



public int getCloud_marks() {
		return cloud_marks;
	}



public void setCloud_marks(int cloud_marks) {
		this.cloud_marks = cloud_marks;
	}





public int getPython_marks() {
		return python_marks;
	}



public void setPython_marks(int python_marks) {
		this.python_marks = python_marks;
	}






public Long getUid() {
		return uid;
	}



public void setUid(Long uid) {
		this.uid = uid;
	}







	public int getFlag() {
		return flag;
	}







	public void setFlag(int flag) {
		this.flag = flag;
	}


	public String getExam_meeting_link() {
		return exam_meeting_link;
	}


	public void setExam_meeting_link(String exam_meeting_link) {
		this.exam_meeting_link = exam_meeting_link;
	}


	public java.util.Date getInterview_time() {
		return interview_time;
	}


	public void setInterview_time(java.util.Date interview_time) {
		this.interview_time = interview_time;
	}


	public String getInterview_meeting_link() {
		return interview_meeting_link;
	}


	public void setInterview_meeting_link(String interview_meeting_link) {
		this.interview_meeting_link = interview_meeting_link;
	}	
	
	
	
}
