package com.intern.repository;



import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intern.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(String email);
	
	@Query(value = "SELECT u.id, a.email, a.full_name, f.file_name, to_char(u.join_date, 'DD-MM-YYYY') as join_date, to_char(u.end_date, 'DD-MM-YYYY') as end_date, u.project FROM Applications a INNER JOIN Users u ON u.email = a.email INNER JOIN file_names f ON a.id = f.id WHERE u.email = :email AND  f.type = 1", nativeQuery = true)
	Object findApplicationAndUser(@Param("email") String email);
	
	@Query(value = "SELECT * FROM users  where email=:email limit 1  ", nativeQuery = true)
	public User getUserByEmail(String email);
	
	@Query(value = "SELECT u.id from applications a inner join users u on a.email=u.email  WHERE a.id = :id ", nativeQuery = true) 
	Long getUserid(@Param("id") long  id);
	
	@Query(value = "SELECT * from users where email=:email ", nativeQuery = true) 
	ArrayList<User> getusersid(@Param("email") String email);
	
	@Query(value = "select u.email, ap.contact from public.users u, public.applications as ap  where u.id=:id and u.email=ap.email", nativeQuery = true)
	ArrayList<Object[]> getEmailIdByUid(@Param("id") Long id);
	
	
	@Query(value = "select COALESCE(leave_beyond_limit_flag, 0) from public.users  where id=:id", nativeQuery = true)
	Integer getLeavePermissionFlag(@Param("id") Long id);
	
	@Query(value="SELECT role_id FROM users_roles where user_id=:id",nativeQuery = true)
	Long getroleid(@Param("id") long id);
	
	@Query(value="SELECT a.full_name, a.contact FROM users u inner join applications a on u.email=a.email where u.email=:email and u.active_flag=:value",nativeQuery = true)
	ArrayList<Object> finduser(@Param("email") String email,@Param("value") boolean value);
	
	//UpdatedNow
	@Transactional
	@Modifying
	@Query(value="update users set join_date=:start_date, end_date=:end_date, project=:project, mentor_email=:mentor_email, start_date =:start_date where id=:id",nativeQuery = true)
	int savedate(@Param("start_date") java.util.Date start_date, @Param("end_date") java.util.Date end_date, @Param("id") long id, @Param("mentor_email") String mentor_email, @Param("project") String project); 
	
	
	@Transactional
	@Modifying
	@Query(value="delete from users_roles where user_id=:id",nativeQuery = true)
	int relieve(@Param("id") long id);
	
	@Transactional
	@Modifying
	@Query(value="delete from file_names where id=:userid and type=1",nativeQuery = true)
	int delfile(@Param("userid") long userid);
	
	
	@Transactional
	@Modifying
	@Query(value="delete from file_names where id=:userid and type=4",nativeQuery = true)
	int delfile4(@Param("userid") long userid);
	
	@Transactional
	@Modifying
	@Query(value="update users set password=:password where email=:email",nativeQuery = true)
	int savenewpass(@Param("email") String email,@Param("password") String password );
	

	@Query(value = "SELECT  a.email, a.full_name,a.contact, f.java_marks,f.android_marks,f.sql_marks,f.cloud_marks,f.python_marks,f.total_marks,f.startdate,f.enddate, f.java_total,f.android_total,f.sql_total,f.cloud_total,f.python_total,f.marks_out_of FROM Applications a INNER JOIN Users u ON u.email = a.email INNER JOIN Flags f ON u.id=f.uid where DATE(f.examdate) >=:fromDate and DATE(f.examdate) <=:toDate and f.flag=1", nativeQuery = true)
	ArrayList<Object> findApplication(@Param("fromDate") java.util.Date fromDate,@Param("toDate") java.util.Date toDate);
	
	@Query(value = "SELECT  a.email, a.full_name,a.contact, f.java_marks,f.android_marks,f.sql_marks,f.cloud_marks,f.python_marks,f.total_marks,f.startdate,f.enddate, f.java_total,f.android_total,f.sql_total,f.cloud_total,f.python_total,f.marks_out_of FROM Applications a INNER JOIN Users u ON u.email = a.email INNER JOIN Flags f ON u.id=f.uid where u.id=:userid", nativeQuery = true)
	ArrayList<Object> findresultuser(@Param("userid") long userid);
	
	
	@Query(value = "SELECT  a.email, a.full_name,a.contact,a.id FROM Applications a INNER JOIN Users u ON u.email = a.email  where u.end_date >=:fromDate and u.end_date<=:toDate  and a.status='Relieved' ", nativeQuery = true)
	ArrayList<Object> findcerti(@Param("fromDate") java.util.Date fromDate,@Param("toDate") java.util.Date toDate);
	
	//@Query(value = "SELECT  a.email,a.id, a.full_name, f.file_name FROM Applications a INNER JOIN Users u ON u.email = a.email INNER JOIN file_names f ON a.id = f.id where f.type=1 and a.id=:id", nativeQuery = true)
	//List<Object> findfile(@Param("id") long id);
	
	@Transactional
	@Modifying
	@Query(value="update users set email=:newemail where email=:email",nativeQuery = true)
	int saveprofile(@Param("email") String email,@Param("newemail") String newemail);
	
	@Transactional
	@Modifying
	@Query(value="delete from file_names where id=:userid and type=:type",nativeQuery = true)
	void delfileIndividual(@Param("userid") long userid, @Param("type") Integer type);
	
	@Transactional
	@Modifying
	@Query(value="delete from users_roles where user_id=:userid",nativeQuery = true)
	void deleteRoleById(@Param("userid") long userid);
	

	
	//for updating mentor details from user table
	@Transactional
	@Modifying
	@Query(value="update users set email=:newEmail where email=:oldEmail",nativeQuery=true)
	int updateMentorDetails(@Param("oldEmail") String oldEmail,@Param("newEmail") String newEmail);
	
	//for updating mentor_email from user table 
	@Transactional
	@Modifying
	@Query(value="update users set mentor_email=:newEmail where mentor_email=:oldEmail",nativeQuery=true)
	int updateUserMentor(@Param("oldEmail") String oldEmail,@Param("newEmail") String newEmail);
	
	//for updating value of active_flag in user table 
	@Transactional
	@Modifying
	@Query(value="update users set active_flag=:value where email=:oldEmail",nativeQuery=true)
	int updateMentorEmail(@Param("oldEmail") String oldEmail,@Param("value") boolean value);
	
	//for retrieving user_id for users_roles tables
	@Query(value="select id from users where email=:oldEmail",nativeQuery=true)
	Long getMentorId(@Param("oldEmail") String oldEmail);
	
} 