package com.intern.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(TaskAllotmentHistoryId.class)
public class TaskAllotmentHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;
	 
	@Id
	@Column(name = "date", columnDefinition="timestamp")
	private Date date;
	
	@Column(name = "intern_id")
	private Long intern_id;

	@Column(name = "intern_name")
	private String intern_name;
	
	@Column(name = "tester_id")
	private Long tester_id;
	
	@Column(name = "testor_name")
	private String testor_name;
	
	@Column(name = "start_date", columnDefinition="date")
	private Date start_date;
	
	@Column(name = "end_date", columnDefinition="date")
	private Date end_date;
	
	@Column(name = "testing_result")
	private String testing_result;
	
	@Column(name = "testing_action")
	private String testing_action;
	
	@Column(name = "status")
	private String status;

	public TaskAllotmentHistory() {
		super();
	}
	
	public TaskAllotmentHistory(Date date, Long intern_id, String intern_name, Long tester_id,
			String testor_name, Date start_date, Date end_date, String testing_result, String testing_action,
			String status) {
		super();
		this.date = date;
		this.intern_id = intern_id;
		this.intern_name = intern_name;
		this.tester_id = tester_id;
		this.testor_name = testor_name;
		this.start_date = start_date;
		this.end_date = end_date;
		this.testing_result = testing_result;
		this.testing_action = testing_action;
		this.status = status;
	}


	public Long getIntern_id() {
		return intern_id;
	}

	public void setIntern_id(Long intern_id) {
		this.intern_id = intern_id;
	}

	public String getIntern_name() {
		return intern_name;
	}

	public void setIntern_name(String intern_name) {
		this.intern_name = intern_name;
	}

	public Long getTester_id() {
		return tester_id;
	}

	public void setTester_id(Long tester_id) {
		this.tester_id = tester_id;
	}
	
	public String getTestor_name() {
		return testor_name;
	}

	public void setTestor_name(String testor_name) {
		this.testor_name = testor_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getTesting_result() {
		return testing_result;
	}

	public void setTesting_result(String testing_result) {
		this.testing_result = testing_result;
	}

	public String getTesting_action() {
		return testing_action;
	}

	public void setTesting_action(String testing_action) {
		this.testing_action = testing_action;
	}
	
}
