package com.intern.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
@IdClass(SetCompositeKey.class)
@Entity
@Table(name = "questionset")
public class QuestionSet {
	
	
	 @Id   
	 @Column(name = "questionids")
	 private Integer questionids;
	@Id
	 @Column(name = "setid")
	 private long setid;

	 


	 
	 public QuestionSet() 
	 {
	    	
	 }


	 public QuestionSet(Integer questionids,long setid) 
	 {
	    	this.questionids=questionids;
	    	this.setid=setid;
	 }



	public Integer getQuestionids() {
		return questionids;
	}





	public void setQuestionids(Integer questionids) {
		this.questionids = questionids;
	}





	public long getSetid() {
		return setid;
	}





	public void setSetid(long setid) {
		this.setid = setid;
	}

	 

}
