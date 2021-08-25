package com.intern.web.dto;

import java.util.Date;

public interface ApplicationBulkEditDto {
	
	public Long getApplicationId();
	
	public String getContact(); 
	
	public String getEmail();
	
	public String getFullName(); 
	
	public String getStatus();
	
	public String getScore();
	
	public String getSetName();
	
	public Long getSetId();
	
	public Long getUserId();
	
	public Date getExamDate(); 
	
}
