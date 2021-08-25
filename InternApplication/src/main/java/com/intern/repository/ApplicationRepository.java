 package com.intern.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intern.web.dto.ApplicationInterviewCallDto;
//import com.intern.web.dto.ApplicationSelectionDto;
import com.intern.model.Application;
import com.intern.web.dto.ApplicationBulkEditDto;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
	
	@Query(value = "SELECT * FROM applications  WHERE STATUS IN (:status)", nativeQuery = true)
	List<Application> findApplication(@Param("status") List<String> list);
	
	@Query(value = "SELECT contact,address FROM applications  WHERE email = :email", nativeQuery = true)
	ArrayList<Object> getprofile(@Param("email") String email);
	
	@Query(value = "SELECT contact FROM applications  WHERE email = :email", nativeQuery = true)
	String getContact(@Param("email") String email);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE APPLICATIONS SET STATUS = 'Approved' WHERE ID = :ID", nativeQuery = true)	
	int AcceptedApplication(@Param("ID") Long ID);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE APPLICATIONS SET STATUS = 'Rejected' WHERE ID = :ID", nativeQuery = true)
	int RejectedApplication(@Param("ID") Long ID);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE APPLICATIONS SET STATUS = 'RejectAfterInterview' WHERE ID = :applicantId", nativeQuery = true)
	int RejectedApplicationAfterInterview(@Param("applicantId") Long applicantId);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE APPLICATIONS SET STATUS = 'RejectAfterJoiningApprove' WHERE ID = :applicantId", nativeQuery = true)
	int RejectedApplicationAfterJoining(@Param("applicantId") Long applicantId);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE APPLICATIONS SET email =:newemail, contact=:phno, address=:address WHERE email = :email", nativeQuery = true)
	int saveprofile(@Param("email") String email,@Param("newemail") String newemail,@Param("address") String address,@Param("phno") String phno);
	
	@Query(value = "SELECT id FROM applications  WHERE email = :email ", nativeQuery = true)
	Long findid(@Param("email") String email);
	
	@Query(value = "SELECT a.id FROM Applications a inner join Users u on a.email=u.email where u.id=:id  ", nativeQuery = true)
	Long findid1(@Param("id") Long id);
	
	@Query(value = "SELECT full_name FROM Applications  where id=:id  ", nativeQuery = true)
	String finduserFullname(@Param("id") Long id);
	
	@Query(value = "SELECT contact FROM Applications  where id=:id  ", nativeQuery = true)
	String finduserContact(@Param("id") Long id);
	
	@Query(value = "SELECT email FROM Applications  where id=:id  ", nativeQuery = true)
	String finduserEmail(@Param("id") Long id);
	
	@Query(value = "SELECT * FROM Applications  where email=:userEmail  ", nativeQuery = true)
	public List<Application> findByEmail(String userEmail);
	
	public boolean existsByEmail(String email);
	
	 
	
	
	
//	Integrate
	@Query(value = "select a.id applicationId, a.contact, a.email, a.full_name fullName, a.status, a.score, u.id userId, sm.setname setName, f.set_id setId, f.examdate examDate from applications a, users u, flags f, setmaster sm where a.email = u.email and u.id = f.uid and f.set_id = sm.id and a.status = 'Exam' and f.flag=0\r\n"
	 		+ "UNION\r\n"
	 		+ "select a.id applicationId, a.contact, a.email, a.full_name fullName, a.status, a.score, Null as userId, Null as setName, Null as setId, Null as examDate from applications a where status in ('NoExam', 'Approved');", nativeQuery = true)
	 public	ArrayList<ApplicationBulkEditDto> getBulkEditApplications();
	
	@Query(value = "select * from applications a where email in (select email from users u, flags f where u.id = f.uid and f.flag = 0 and f.set_id = 0);", nativeQuery = true)
	public	ArrayList<Application> getOfflineExamApplications();

	//Latest Integrate
	@Query(value = "select a.id applicationId, a.full_name fullName, a.email, a.contact, a.status, a.score, a.school_name collegeName, a.area_of_interest areaOfInterest, u.id userId, f.set_id setId, sm.setname setName, f.examdate examDate, f.total_marks, f.java_marks, f.java_total, f.marks_out_of, f.android_marks, f.android_total, f.cloud_marks, f.cloud_total, f.sql_marks, f.sql_total, f.python_marks, f.python_total  from applications a, flags f, users u, setmaster sm where a.email = u.email and u.id = f.uid and f.set_id = sm.id and a.status = 'ExamOver'\r\n"
			+ "UNION \r\n"
			+ "select a.id applicationId, a.full_name fullName, a.email, a.contact, a.status, a.score, a.school_name collegeName, a.area_of_interest areaOfInterest, Null as userId, Null as setId, Null as setName, Null as examDate, Null as total_marks, Null as java_marks, Null as java_total, Null as marks_out_of, Null as android_marks, Null as android_total, Null as cloud_marks, Null as cloud_total, Null as sql_marks, Null as sql_total, Null as python_marks, Null as python_total from applications a where a.status = 'NoExam'", nativeQuery = true)
	ArrayList<ApplicationInterviewCallDto> applicationsForInterviewCall();

	@Query(value = "select a.id applicationId, a.full_name fullName, a.email, a.contact, a.status, a.score, a.school_name collegeName, a.area_of_interest areaOfInterest, u.id userId, Null as setName, f.set_id setId, f.examdate examDate, f.total_marks, f.java_marks, f.java_total, f.marks_out_of, f.android_marks, f.android_total, f.cloud_marks, f.cloud_total, f.sql_marks, f.sql_total, f.python_marks, f.python_total, f.interview_time, to_char (f.interview_time, 'DD-MM-YYYY HH24:MI') as interview_time_string, f.interview_meeting_link from applications a left join users u on a.email = u.email left join flags f on u.id = f.uid where a.status = 'Interview'", nativeQuery = true)
	List<ApplicationInterviewCallDto> getApplicationsToGiveInterviewMarks();
	
	@Query(value = "select a.id \"applicationId\", a.full_name \"fullName\", a.email, a.contact, a.status, a.score, a.school_name \"collegeName\", a.area_of_interest \"areaOfInterest\", u.id \"userId\", Null as setName, f.set_id \"setId\", f.examdate \"examDate\", f.total_marks, f.java_marks, f.java_total, f.marks_out_of, f.android_marks, f.android_total, f.cloud_marks, f.cloud_total, f.sql_marks, f.sql_total, f.python_marks, f.python_total, case \r\n" + 
			"when (a.status='Marked') then i.marks  \r\n" + 
			"else 'NA' \r\n" + 
			"end as \"interviewMarks\"  from applications a left join users u on a.email = u.email left join flags f on u.id = f.uid left join interview i on i.id=a.id  where a.status in ('NoInterview','Marked')", nativeQuery = true)
	List<ApplicationInterviewCallDto> getApplicationsToSelectInterns();
	
	@Query(value = "select a.id \"applicationId\", a.full_name \"fullName\", a.email, a.contact, a.status, a.score, a.school_name \"collegeName\", a.area_of_interest \"areaOfInterest\", u.id \"userId\",to_char(u.join_date, 'DD-MM-YYYY') \"joinDate\",u.join_date \"startDate\" ,u.end_date \"completionDate\", to_char(u.end_date, 'DD-MM-YYYY') \"endDate\",  u.project, u.mentor_email,  Null as setName, f.set_id \"setId\", f.examdate \"examDate\", f.total_marks, f.java_marks, f.java_total, f.marks_out_of, f.android_marks, f.android_total, f.cloud_marks, f.cloud_total, f.sql_marks, f.sql_total, f.python_marks, f.python_total, case \r\n" + 
			"when (f.interview_time is not null) then i.marks  \r\n" + 
			"else 'NA' \r\n" + 
			"end as \"interviewMarks\"  from applications a left join users u on a.email = u.email left join flags f on u.id = f.uid left join interview i on i.id=a.id  where a.status = :status", nativeQuery = true)
	List<ApplicationInterviewCallDto> getApplicationsOfSelectedIntern(@Param("status") String status);
}

