package com.intern.web.dto;

import java.util.Date;

public interface ApplicationInterviewCallDto {
	
    public Long getApplicationId();
	
	public String getContact(); 
	
	public String getEmail();
	
	public String getFullName(); 
	
	public String getStatus();
	
	public String getScore();
	
	public String getSetName();
	
	public String getCollegeName();
	
	public String getAreaOfInterest();
	
	public Long getSetId();
	
	public Long getUserId();
	public String getJoinDate();
	public String getEndDate();
	public Date getStartDate();
	public Date getCompletionDate();
	public String getProject();
	public String getMentor_email();
	public Date getExamDate(); 
	
	public Integer getPython_total();
	
	public Integer getPython_marks();
	
	public Integer getCloud_total();
	
	public Integer getCloud_marks();
	
	public Integer getAndroid_total();
	
	public Integer getAndroid_marks();
	
	public Integer getJava_total();
	
	public Integer getJava_marks();
	
	public Integer getSql_total();
	
	public Integer getSql_marks();
	
	public Integer getTotal_marks();
	
	public Integer getMarks_out_of();
	public String getInterviewMarks();
	public Date getInterview_time();
	public String getInterview_time_string();
	public String getInterview_meeting_link();
}
