package com.intern.model;








import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
//import javax.persistence.Table;


@Entity
@IdClass(DatewiseholidayId.class)
//@Table(name = "datewiseholiday")
public class Datewiseholiday {
	
	
	 @Id
	 private long id;
	   
 
	    
	 	@Id
	 	@Column(name = "holidaydate")
	    private java.util.Date holidaydate;
	    
	   
	 	//holiday date entered by which logged in user with time stamp
		@Column(name = "entry_by")
		private String entryByUser;
		@Column(name = "entry_time", columnDefinition="timestamp")
		private Date entryTimeUser;
	  

	    public Datewiseholiday() 
	    {
	    	
	    }

	    public Datewiseholiday (long id,java.util.Date holidaydate, String entryByUser, Date entryTimeUser) {
	    	this.id=id;
	        this.holidaydate = holidaydate;
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

		public void setId(long id) {
	        this.id = id;
	    }

	    public long getId() {
	        return id;
	    }
	    
	    public void setHolidaydate(java.util.Date holidaydate) {
	        this.holidaydate = holidaydate;
	    }
	    
	   
	    public java.util.Date getHolidaydate() {
	        return holidaydate;
	    } 

	   


}
