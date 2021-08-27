package com.intern.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "task_allotment")

public class TaskAllotment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "intern_id")
	private Long intern_id;
	
	@Column(name = "tester_id")
	private Long tester_id;
	
	@Column(name = "task_alloted")
	private String task_alloted;
	
	@Column(name = "start_date", columnDefinition="date")
	private Date start_date;
	
	@Column(name = "end_date", columnDefinition="date")
	private Date end_date;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "assigned_by")
	private String assigned_by;

	public String getAssigned_by() {
		return assigned_by;
	}

	public void setAssigned_by(String assigned_by) {
		this.assigned_by = assigned_by;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIntern_id() {
		return intern_id;
	}

	public void setIntern_id(Long intern_id) {
		this.intern_id = intern_id;
	}

	public Long getTester_id() {
		return tester_id;
	}

	public void setTester_id(Long tester_id) {
		this.tester_id = tester_id;
	}

	public String getTask_alloted() {
		return task_alloted;
	}

	public void setTask_alloted(String task_alloted) {
		this.task_alloted = task_alloted;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TaskAllotment(Long intern_id, Long tester_id, String task_alloted, Date start_date,
			Date end_date, String status, String assigned_by) {
		super();
		this.intern_id = intern_id;
		this.tester_id = tester_id;
		this.task_alloted = task_alloted;
		this.start_date = start_date;
		this.end_date = end_date;
		this.status = status;
		this.assigned_by = assigned_by;
	}
	
}
