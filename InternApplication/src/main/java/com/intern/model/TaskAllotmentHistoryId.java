package com.intern.model;

import java.io.Serializable;
import java.util.Date;

public class TaskAllotmentHistoryId implements Serializable {
	
	private static final long serialVersionUID = 2L;

	private long id;
	private Date date;
	
	public TaskAllotmentHistoryId() {
		super();
	}
	
	public TaskAllotmentHistoryId(long id, Date date) {
		super();
		this.id = id;
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
