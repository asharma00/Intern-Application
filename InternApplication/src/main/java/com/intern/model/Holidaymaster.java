package com.intern.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "holidaymaster")
public class Holidaymaster {

	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
   
    
  

    
    @Column(name = "holidaytype")
    private String holidaytype;

    
    @Column(name = "holidaydescription")
    private String holidaydescription;
    
    //holiday date entered by which logged in user with time stamp
	@Column(name = "entry_by")
	private String entryByUser;
	@Column(name = "entry_time", columnDefinition="timestamp")
	private Date entryTimeUser;
    

    public Holidaymaster() 
    {
    	
    }

    public Holidaymaster( String holidaytype,String holidaydescription, String entryByUser, Date entryTimeUser) {
    	
    	this.holidaytype = holidaytype;
        this.holidaydescription = holidaydescription;
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
    
    public void setHolidaytype(String holidaytype) {
        this.holidaytype = holidaytype;
    }
    
 

    public void setHolidaydescription(String holidaydescription) {
        this.holidaydescription = holidaydescription;
    }

   
    
    
    public String getHolidaytype() {
        return holidaytype;
    } 
    
    public String getHolidaydescription() {
        return holidaydescription;
    }

   
   
}
