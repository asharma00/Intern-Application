package com.intern.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "setmaster")

public class SetMaster {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
    @Column(name = "setname")
    private String setname;
    
    @Column(name = "set_time")
    private Integer setTime;
    
    //roaster entered by which logged in user with time stamp
  	@Column(name = "entry_by")
  	private String entryByUser;
  	@Column(name = "entry_time", columnDefinition="timestamp")
  	private Date entryTimeUser;
    
	public SetMaster() 
    {
    	
    }

    public SetMaster( String setname, String entryByUser, Date entryTimeUser) {
    	
    	this.setname = setname;
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

	public SetMaster( String setname, Integer setTime) {
    	
    	this.setname = setname;
    	this.setTime = setTime;
      
       
    }


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getSetname() {
		return setname;
	}


	public void setSetname(String setname) {
		this.setname = setname;
	}
	
	public Integer getSetTime() {
		return setTime;
	}

	public void setSetTime(Integer setTime) {
		this.setTime = setTime;
	}	

}
