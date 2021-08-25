
package com.intern.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



//import com.sun.istack.NotNull;


@Entity
@Table(name = "question_paper")
public class QuestionPaper {

	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
 
    @Column(name = "topic")
    private String topic;

    
    @Column(name="marks")
    private Integer marks;
    
    @Column(name = "question")
    private String question;

    @Column(name = "option1")
    private String option1;
    
    @Column(name = "option2")
    private String option2;
    
    @Column(name = "option3")   
    private String option3;
    
    @Column(name = "option4")
    private String option4;
    
    @Column(name = "correctans")  
    private String correctans;

    

    public QuestionPaper() 
    {
    	
    }

    

    public QuestionPaper(Integer id,Integer marks, String topic, String question, String option1, String option2, String option3,
			String option4, String correctans) {
		super();
		this.id = id;
		this.topic = topic;
		this.question = question;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.correctans = correctans;
	}



	public Integer getMarks() {
		return marks;
	}



	public void setMarks(Integer marks) {
		this.marks = marks;
	}
	
	
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getTopic() {
		return topic;
	}



	public void setTopic(String topic) {
		this.topic = topic;
	}



	public String getQuestion() {
		return question;
	}



	public void setQuestion(String question) {
		this.question = question;
	}



	public String getOption1() {
		return option1;
	}



	public void setOption1(String option1) {
		this.option1 = option1;
	}



	public String getOption2() {
		return option2;
	}



	public void setOption2(String option2) {
		this.option2 = option2;
	}



	public String getOption3() {
		return option3;
	}



	public void setOption3(String option3) {
		this.option3 = option3;
	}



	public String getOption4() {
		return option4;
	}



	public void setOption4(String option4) {
		this.option4 = option4;
	}



	public String getCorrectans() {
		return correctans;
	}



	public void setCorrectans(String correctans) {
		this.correctans = correctans;
	}





   
}
