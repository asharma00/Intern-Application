package com.intern.model;


import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

//primary key=uid+qid
@Entity  //specifies that the class is an entity and is mapped to a database table 
@Table(name="response")  //specifies the name of database table to be used for mapping
@IdClass(ResponsesCompositeKey.class)
public class Responses {
	
	@Id    //specifies primary key of an entity
	//@GeneratedValue(strategy=GenerationType.AUTO)  //provides for specification of generation strategies for the value of primary keys
	private Integer qid;
	
	private String answer;
	private long uid;
	
	
	
public Responses() {
		
	}
	
	
	
	
	public Integer getQid() {
	return qid;
}




public void setQid(Integer qid) {
	this.qid = qid;
}




public String getAnswer() {
	return answer;
}




public void setAnswer(String answer) {
	this.answer = answer;
}




	public Responses(Integer qid, String answer, Long uid) {
		super();
		
		this.qid=qid;
		this.answer=answer;
		this.uid=uid;
		
	}




	public long getUid() {
		return uid;
	}




	public void setUid(long uid) {
		this.uid = uid;
	}
	

}
