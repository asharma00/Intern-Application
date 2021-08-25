package com.intern.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.JoinColumn;

@Entity
@Table(name =  "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO	)
	private Long id;
	
	@Column(name = "full_name")
	private String fullName;
	
	private String email;
	private String password;
	private java.util.Date join_date;
	private java.util.Date start_date;
	private java.util.Date end_date;
	private String project;
	private java.util.Date relieve_date;
	private Integer leave_beyond_limit_flag;
	@Column(name = "mentor_email")
	private String mentorEmail;
	
	@Column(name = "active_flag", columnDefinition = "boolean default true")    //1 represents mentor position
	private boolean activeFlagValue;  
	
	public  java.util.Date getJoin_date() {
		return join_date;
	}

	public void setJoin_date(java.util.Date join_date) {
		this.join_date = join_date;
	}

	public java.util.Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(java.util.Date end_date) {
		this.end_date = end_date;
	}
	
	
	public java.util.Date getRelieve_date() {
		return relieve_date;
	}

	public void setRelieve_date(java.util.Date relieve_date) {
		this.relieve_date = relieve_date;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}


	public String getMentorEmail() {
		return mentorEmail;
	}

	public void setMentorEmail(String mentorEmail) {
		this.mentorEmail = mentorEmail;
	}


	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(
		            name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
				            name = "role_id", referencedColumnName = "id"))
	
	private Collection<Role> roles;
	
	public User() {
		
	}
	
	public User(String fullName, String email, String password, Collection<Role> roles, boolean activeFlagValue) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		
		this.roles = roles;
		this.activeFlagValue = activeFlagValue;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return fullName;
	}
	public void setFirstName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Collection<Role> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public Integer getLeave_beyond_limit_flag() {
		return leave_beyond_limit_flag;
	}

	public void setLeave_beyond_limit_flag(Integer leave_beyond_limit_flag) {
		this.leave_beyond_limit_flag = leave_beyond_limit_flag;
	}

	public java.util.Date getStart_date() {
		return start_date;
	}

	public void setStart_date(java.util.Date start_date) {
		this.start_date = start_date;
	}

	public boolean isActiveFlagValue() {
		return activeFlagValue;
	}

	public void setActiveFlagValue(boolean activeFlagValue) {
		this.activeFlagValue = activeFlagValue;
	}

}