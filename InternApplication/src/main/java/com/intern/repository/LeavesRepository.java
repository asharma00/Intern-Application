

package com.intern.repository;



import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intern.model.Leaves;
import com.intern.model.LeavesCompositeKey;







@Repository
public interface LeavesRepository extends JpaRepository<Leaves, LeavesCompositeKey> {
	
	@Query(value = "SELECT u.id,u.email,u.full_name,l.from_date,l.to_date,l.reason_for_leave,l.total_days, a.contact,l.leave_from_time,l.leave_to_time  FROM Leaves l inner join Users u on l.uid=u.id inner join applications a on u.email=a.email WHERE l.status='Initiated' ", nativeQuery = true)
	ArrayList<Object> getLeaves();
	
	
	@Query(value = "select to_char(l.from_date,'DD/MM/YYYY') as leave_start_string, l.leave_from_time, to_char(l.to_date,'DD/MM/YYYY') as leave_end_string, l.leave_to_time, l.reason_for_leave, l.status, l.total_days, a.contact, a.email, a.full_name,u.project from leaves l, users u, applications a where l.uid=u.id and a.email=u.email and u.email=:email order by from_date asc, full_name asc", nativeQuery = true)
	ArrayList<Object> findLeaveByEmail(@Param("email") String email); 
	
	
	
	@Query(value="select COALESCE(sum(total_days), 0) as sum from public.leaves where uid=:id", nativeQuery=true)
	Double getLeaveCountById(@Param("id") long id);
	
	@Query(value="select count(*) from leaves where uid=:id and (from_date between :leavefrom and :leaveto  OR to_date  between :leavefrom and :leaveto)", nativeQuery=true)
	
	Integer getExistingLeavesBetweenDay(@Param("id") long id, @Param("leavefrom") Date leavefrom, @Param("leaveto") Date leaveto);
	
	
	@Query(value = "SELECT u.id,u.email,u.full_name,u.project,l.from_date,l.to_date,l.reason_for_leave,l.total_days, a.contact,l.leave_from_time,l.leave_to_time  FROM Leaves l inner join Users u on l.uid=u.id inner join applications a on u.email=a.email and current_date between from_date and to_date order by project", nativeQuery = true)
	ArrayList<Object> getInternLeaveOnToday();
	
	
	@Query(value = "SELECT u.id,u.email,u.full_name,l.from_date,l.to_date,l.reason_for_leave,l.total_days,l.status ,l.leave_from_time,l.leave_to_time FROM Leaves l inner join Users u on l.uid=u.id WHERE status='Accepted' and  u.email=:email ", nativeQuery = true)
	ArrayList<Object> getIndividualLeaves(@Param("email") String email);
	
	@Query(value = "SELECT u.id,u.email,u.full_name,l.from_date,l.to_date,l.reason_for_leave,l.total_days,l.status ,l.leave_from_time,l.leave_to_time FROM Leaves l inner join Users u on l.uid=u.id WHERE  u.email=:email ", nativeQuery = true)
	ArrayList<Object> getAllIndividualLeaves(@Param("email") String email);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE leaves SET status='Accepted', approved_by=:currentUser, entry_time=:entryDateTime WHERE uid = :id and from_date=:fromDate", nativeQuery = true)	
	int acceptLeave(@Param("id") long id,@Param("fromDate") java.util.Date fromDate, @Param("currentUser") String currentUser, @Param("entryDateTime") Date entryDateTime);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE leaves SET status='Rejected' , reason_for_rejection=:reason, approved_by=:currentUser, entry_time=:entryDateTime WHERE uid = :id and from_date=:fromDate", nativeQuery = true)	
	int rejectLeave(@Param("id") long id,@Param("fromDate") java.util.Date fromDate,@Param("reason") String reason, @Param("currentUser") String currentUser, @Param("entryDateTime") Date entryDateTime);
	
	@Query(value = "SELECT holidaydate FROM datewiseholiday  WHERE  holidaydate BETWEEN  :fromDate AND :toDate", nativeQuery = true)
	ArrayList<java.util.Date> getholidayleavebettwodates(@Param("fromDate") java.util.Date fromDate, @Param("toDate") java.util.Date toDate);
	
	
	@Query(value = "SELECT holidaydate FROM datewiseholiday  WHERE  holidaydate >=  :fromDate", nativeQuery = true)
	ArrayList<java.util.Date> getholidayleave(@Param("fromDate") java.util.Date fromDate);
	
	//for mentor page
	@Query(value = "SELECT u.id,u.email,u.full_name,l.from_date,l.to_date,l.reason_for_leave,l.total_days, a.contact,l.leave_from_time,l.leave_to_time  FROM Leaves l inner join Users u on l.uid=u.id inner join applications a on u.email=a.email WHERE l.status='Initiated' and u.mentor_email=:mentorEmail", nativeQuery = true)
	ArrayList<Object> getLeaves2(@Param ("mentorEmail") String mentorEmail);
}