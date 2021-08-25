package com.intern.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "interview")
public class Interview {
	
	
	
		@Id
	   private long id; 
	    
	 
	    @Column(name = "marks")
	    private String marks;

	    
	    @Column(name="remarks")
	    private String remarks;
	    
	   
	    //interview marks entered by which logged in user with time stamp
		@Column(name = "entry_by")
		private String entryByUser;
		@Column(name = "entry_time", columnDefinition="timestamp")
		private Date entryTimeUser;

		
	    public Interview() 
	    {
	    	
	    }

	    

	    public Interview(long id,String marks, String remarks, String entryByUser, Date entryTimeUser) {
			super();
			this.id = id;
			this.marks= marks;
			this.remarks= remarks;
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



		public String getMarks() {
			return marks;
		}



		public void setMarks(String marks) {
			this.marks = marks;
		}
		
		
		public long getId() {
			return id;
		}



		public void setId(long id) {
			this.id = id;
		}



		public String getRemarks() {
			return remarks;
		}



		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}



		

}
