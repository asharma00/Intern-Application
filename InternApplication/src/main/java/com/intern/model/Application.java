package com.intern.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Entity
@Table(name="applications")
public class Application {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;
	@Column(name = "email")
	private String email;
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "parent_name")
	private String parentName;
	@Column(name = "address")
	private String address;
	@Column(name = "school_name")
	private String schoolName;
	@Column(name = "contact")
	private String contact;
	@Column(name = "school_id")
	private String schoolId;
	@Column(name = "score")
	private String score;
	@Column(name = "status")
	private String status;
	private Date date_of_birth;
	private String area_of_interest;
	@Column(name = "date_of_apply")
	private Date dateOfApply;
	public String getArea_of_interest() {
		return area_of_interest;
	}
	public void setArea_of_interest(String area_of_interest) {
		this.area_of_interest = area_of_interest;
	}
	public Application( String fullName,Date date_of_birth,String email, String parentName, String address, String schoolName,
			String contact, String schoolId, String score, String status,String area_of_interest, Date dateOfApply) {
		super();
		this.email = email;
		this.fullName = fullName;
		
		this.parentName = parentName;
		this.address = address;
		this.schoolName = schoolName;
		this.contact = contact;
		this.schoolId = schoolId;
		this.score = score;
		this.status=status;
		this.date_of_birth=date_of_birth;
		this.area_of_interest=area_of_interest;
		this.dateOfApply=dateOfApply;
	}
	public Date getDate_of_birth() {
		return date_of_birth;
	}
	public void setDate_of_birth(Date date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public Date getDateOfApply() {
		return dateOfApply;
	}
	public void setDateOfApply(Date dateOfApply) {
		this.dateOfApply = dateOfApply;
	}
	public Application() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
