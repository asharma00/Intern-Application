package com.intern.repository;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.intern.model.Attendance;
import com.intern.model.AttendanceCompositeKey;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, AttendanceCompositeKey>{
	
	@Query(value="SELECT * FROM ATTENDANCE WHERE attendance_status = 'Pending'", nativeQuery = true)
	ArrayList<Attendance> findPendingAttendance(); 
	
	@Query(value = "SELECT * FROM Attendance a INNER JOIN Users u ON a.student_id = u.id WHERE a.attendance_status = 'Pending' order by attendance_date desc, project, full_name", nativeQuery = true)
	ArrayList<Object> findPendingAttendanceAndUsers();
	
	
	@Query(value = "select u.id, u.email, u.full_name, u.project, \r\n" + 
			"a.attendance_date, a.attendance_in_time, a.attendance_out_time,\r\n" + 
			"a.late_checkin_reason,a.early_checkout_reason, a.target_for_day, a.work_status, a.work_from_checkin, a.work_from_checkout, a.attendance_status, case\r\n" + 
			"when u.mentor_email is null then 'NA'\r\n" + 
			"when u.mentor_email is not null then (select name||'/'||designation from mentor where email=u.mentor_email)\r\n" + 
			"end as mentor_name\r\n" + 
			"from Attendance a, Users u where  a.student_id = u.id and a.attendance_status = 'Pending' order by attendance_date desc, project, full_name", nativeQuery = true)
	ArrayList<Object> findPendingAttendanceAndUsers1();
	
	@Query(value = "SELECT * FROM Attendance a INNER JOIN Users u ON a.student_id = u.id WHERE a.attendance_status = 'Pending' and u.project=:projectName order by attendance_date desc, project, full_name", nativeQuery = true)
	ArrayList<Object> findPendingAttendanceAndUsersByProject(@Param("projectName") String projectName);
	
	
	@Query(value = "select u.id, u.email, u.full_name, u.project, \r\n" + 
			"a.attendance_date, a.attendance_in_time, a.attendance_out_time,\r\n" + 
			"a.late_checkin_reason,a.early_checkout_reason, a.target_for_day, a.work_status, a.work_from_checkin, a.work_from_checkout, a.attendance_status, case\r\n" + 
			"when u.mentor_email is null then 'NA'\r\n" + 
			"when u.mentor_email is not null then (select name||'/'||designation from mentor where email=u.mentor_email)\r\n" + 
			"end as mentor_name\r\n" + 
			"from Attendance a, Users u where  a.student_id = u.id and a.attendance_status = 'Pending' and u.project=:projectName order by attendance_date desc, project, full_name" , nativeQuery = true)
	ArrayList<Object> findPendingAttendanceAndUsersByProject1(@Param("projectName") String projectName);
	
	@Query(value = "SELECT * FROM Attendance a INNER JOIN Users u ON a.student_id = u.id WHERE a.attendance_status = 'Pending' and u.email=:email order by attendance_date desc, project, full_name", nativeQuery = true)
	ArrayList<Object> findPendingAttendanceAndUsersByProjectEmail(@Param("email") String email);
	
	@Query(value = "select u.id, u.email, u.full_name, u.project,  \r\n" + 
			"a.attendance_date, a.attendance_in_time, a.attendance_out_time, \r\n" + 
			"a.late_checkin_reason,a.early_checkout_reason, a.target_for_day, a.work_status, a.work_from_checkin, a.work_from_checkout, a.attendance_status, case \r\n" + 
			"when u.mentor_email is null then 'NA' \r\n" + 
			"when u.mentor_email is not null then (select name||'/'||designation from mentor where email=u.mentor_email) \r\n" + 
			"end as mentor_name \r\n" + 
			"from Attendance a, Users u where  a.student_id = u.id and a.attendance_status = 'Pending' and u.email=:email  order by attendance_date desc, project, full_name", nativeQuery = true)
	ArrayList<Object> findPendingAttendanceAndUsersByProjectEmail1(@Param("email") String email);
	
	@Query(value = "SELECT * FROM ATTENDANCE WHERE student_id = :id AND attendance_status = 'Accepted'", nativeQuery = true)
	ArrayList<Attendance> findAcceptedAttendance(@Param("id") Long id);
	
	@Query(value = "SELECT * FROM ATTENDANCE WHERE student_id = :id AND attendance_status = 'Pending'", nativeQuery = true)
	ArrayList<Attendance> findPendingAttendanceById(@Param("id") Long id);
	
	@Query(value = "SELECT * FROM ATTENDANCE WHERE student_id = :id AND attendance_status = 'Rejected'", nativeQuery = true)
	ArrayList<Attendance> findRejectedAttendance(@Param("id") Long id);
	
	@Query(value = "SELECT to_char(a.attendance_date,'DD/MM/YYYY') as attendance_date_string,a.attendance_in_time,a.attendance_out_time,COALESCE(a.target_for_day, 'N/A') as target_for_day, COALESCE(a.work_status, 'N/A') as work_status, u.email,u.full_name, u.project, a.attendance_status,ap.contact,TO_CHAR(u.join_date, 'DD-MM-YYYY') as join_date, TO_CHAR(u.end_date, 'DD-MM-YYYY') as end_date FROM ATTENDANCE a inner join Users u on a.student_id =u.id inner join Applications ap on u.email=ap.email where u.email=:email order by attendance_date", nativeQuery = true)
	ArrayList<Object> findApplication(@Param("email") String email); 
	
	@Query(value = "select  u.id, u.email, u.full_name, u.project, to_char(att.attendance_date, 'DD/MM/YYYY') as attendance_date_string,att.attendance_in_time, att.attendance_out_time,att.attendance_status, att.target_for_day, att.work_status from  public.attendance att, public.users u where u.id=att.student_id and att.attendance_date between :fromDateAsDate and :toDateAsDate and u.project=:projectName  order by attendance_date, project, full_name", nativeQuery = true)
	ArrayList<Object> findApplicationByDateAndProject(@Param("projectName") String projectName, @Param("fromDateAsDate") Date fromDateAsDate,@Param("toDateAsDate") Date toDateAsDate ); 
	
	@Query(value = "SELECT distinct email,full_name from  public.users  where project=:project and email in (select email from applications where status='Joined') order by full_name", nativeQuery = true)
	ArrayList<Object> findEmail(@Param("project") String project); 
	
	@Query(value = "SELECT distinct email,full_name, project from  public.users  where email in (select email from applications where status='Joined') order by project,full_name", nativeQuery = true)
	ArrayList<Object> findAllEmail(); 
	
	//mentor page for date wise attendance
	@Query(value = "select  u.id, u.email, u.full_name, u.project, to_char(att.attendance_date, 'DD/MM/YYYY') as attendance_date_string,att.attendance_in_time, att.attendance_out_time,att.attendance_status, att.target_for_day, att.work_status from  public.attendance att, public.users u where u.id=att.student_id and u.mentor_email=:mentorEmail and att.attendance_date between :fromDateAsDate and :toDateAsDate order by attendance_date, project, full_name", nativeQuery = true)
	ArrayList<Object> findApplicationByDateAndProject2(@Param("mentorEmail") String mentorEmail, @Param("fromDateAsDate") Date fromDateAsDate,@Param("toDateAsDate") Date toDateAsDate ); 

	//mentor page for retrieving email
	@Query(value = "SELECT distinct email,full_name from  public.users  where mentor_email=:mentorEmail order by full_name", nativeQuery = true)
	ArrayList<Object> findEmail2(@Param("mentorEmail") String mentorEmail); 
	
	//mentor page for displaying attendance
	@Query(value = "select u.id, u.email, u.full_name, u.project, \r\n" + 
			"a.attendance_date, a.attendance_in_time, a.attendance_out_time,\r\n" + 
			"a.late_checkin_reason,a.early_checkout_reason, a.target_for_day, a.work_status, a.work_from_checkin, a.work_from_checkout, a.attendance_status, case\r\n" + 
			"when u.mentor_email is null then 'NA'\r\n" + 
			"when u.mentor_email is not null then (select name||'/'||designation from mentor where email=u.mentor_email)\r\n" + 
			"end as mentor_name\r\n" + 
			"from Attendance a, Users u where  a.student_id = u.id and a.attendance_status = 'Pending' and u.mentor_email=:mentorEmail order by attendance_date desc, project, full_name" , nativeQuery = true)
	ArrayList<Object> findPendingAttendanceAndUsers2(@Param("mentorEmail") String mentorEmail);
	
	//mentor page for displaying particular attendance
	@Query(value = "select u.id, u.email, u.full_name, u.project,  \r\n" + 
			"a.attendance_date, a.attendance_in_time, a.attendance_out_time, \r\n" + 
			"a.late_checkin_reason,a.early_checkout_reason, a.target_for_day, a.work_status, a.work_from_checkin, a.work_from_checkout, a.attendance_status, case \r\n" + 
			"when u.mentor_email is null then 'NA' \r\n" + 
			"when u.mentor_email is not null then (select name||'/'||designation from mentor where email=u.mentor_email) \r\n" + 
			"end as mentor_name \r\n" + 
			"from Attendance a, Users u where  a.student_id = u.id and a.attendance_status = 'Pending' and u.email=:email and u.mentor_email=:mentorEmail order by attendance_date desc, project, full_name", nativeQuery = true)
	ArrayList<Object> findPendingAttendanceAndUsersByProjectEmail2(@Param("mentorEmail") String mentorEmail, @Param("email") String email);
	
}