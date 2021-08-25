package com.intern.controller;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.intern.repository.DateHolidayRepository;
import com.intern.repository.HolidaymasterRepository;
import com.intern.repository.QuestionSetRepository;
import com.intern.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intern.model.Attendance;
import com.intern.model.AttendanceCompositeKey;
import com.intern.model.Datewiseholiday;
import com.intern.model.Holidaymaster;
import com.intern.repository.AttendanceRepository;
import com.intern.repository.UserRepository;

@RestController
@RequestMapping("/api/v3")
public class AttendanceController {

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private UserRepository userRepository;
	

	@Autowired
	private HolidaymasterRepository holidaymasterRepository;
	
	@Autowired
	private DateHolidayRepository dateHolidayRepository;
	

	@Autowired
	private QuestionSetRepository questionSetRepository;

	@PostMapping("/attendance/mark")
	public int markAttendance(Long id, String lateReason, String workFrom) {
		try {
			LocalTime time = LocalTime.now();
			String inTime = time.format(DateTimeFormatter.ofPattern("HH:mm"));
			Attendance at = new Attendance(id, new Date(), inTime, null, "Pending",workFrom,lateReason,null , null, null,null,null,null);
			AttendanceCompositeKey attendanceCompositeKey = new AttendanceCompositeKey(id, new Date());
			if (this.attendanceRepository.findById(attendanceCompositeKey).isPresent()) {
				return 0;
			} else {
				this.attendanceRepository.save(at);
				return 1;
			}
		} catch (Exception ex) {
			System.out.println(ex);
			return 0;
		}
	}

	@PostMapping("/attendance/markIn")
	public int markAttendanceIn(Long id, String lateReason,String targetDay, String workFromCheckin ) {
		try {
			System.out.println("Hello");
			LocalTime time = LocalTime.now();
			String inTime = time.format(DateTimeFormatter.ofPattern("HH:mm"));
			Attendance at = new Attendance(id, new Date(), inTime, null, "Pending",workFromCheckin,lateReason,null,null,targetDay,null,null,null );
			AttendanceCompositeKey attendanceCompositeKey = new AttendanceCompositeKey(id, new Date());
			System.out.println("Id is> "+  id);
			System.out.println("Date is>- "   +new Date());
			if (this.attendanceRepository.findById(attendanceCompositeKey).isPresent()) {
				System.out.println("Hi User, your attendance not recorded");
				return 0;
			} else {
				this.attendanceRepository.save(at);
				return 1;
			}
		} catch (Exception ex) {
			System.out.println(ex);
			return 0;
		}
	}
	
	@PostMapping("/attendance/markOut")
	public int markAttendanceOut(Long id, String earlyReason, String workStatusDay, String workFromCheckout) {
		try {
			LocalTime time = LocalTime.now();
			String outTime = time.format(DateTimeFormatter.ofPattern("HH:mm"));
			AttendanceCompositeKey attendanceCompositeKey = new AttendanceCompositeKey(id, new Date());
			Attendance at=this.attendanceRepository.findById(attendanceCompositeKey).get();
			if (at==null) {
				return 0;
			}
			else if(at.getAttendanceOutTime()!=null)
			{
				return 1;
			}
			else {
				at.setAttendanceOutTime(outTime);
			    at.setEarlyCheckoutReason(earlyReason);
			    at.setWorkFromCheckout(workFromCheckout);
			    at.setWorkStatus(workStatusDay);
				this.attendanceRepository.save(at);
				
				return 2;
			}
		} catch (Exception ex) {
			System.out.println(ex);
			return 0;
		}
	}
	
	
	
	
	@PostMapping("/attendance/adminMark")
	public int markAttendanceByEmail(@RequestParam String email, @RequestParam String markDate, @RequestParam String markInTime,@RequestParam String markOutTime, @RequestParam String workFrom, @RequestParam String targetDay,@RequestParam String workStatus, @RequestParam("currentUser") String currentUser, @RequestParam("entryTime") String entryTime) {
		try {
			 Date markDateAsDate=new SimpleDateFormat("dd/MM/yyyy").parse(markDate);
			 Date entryDateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entryTime);
			User user = userRepository.findByEmail(email);
             if(workFrom!=null)
             {
            	 if(!"".equals(workFrom))
            	 {
            		 workFrom="Home"; 
            	 }
             }
			Attendance at = new Attendance(user.getId(), markDateAsDate, markInTime, markOutTime, "Pending", workFrom, null,workFrom, null, targetDay,workStatus, currentUser, entryDateTime);
			
			AttendanceCompositeKey attendanceCompositeKey = new AttendanceCompositeKey(user.getId(), markDateAsDate);
			if (this.attendanceRepository.findById(attendanceCompositeKey).isPresent()) {
			System.out.println("Attendance already recorded for the date:> "+ markDate);
			return 0;
			}
			else
			{
			this.attendanceRepository.save(at);
			return 1;
			
			}
			} catch (Exception ex) {
			System.out.println(ex);
			return 2;
		}
	}
	

	@GetMapping("/attendance/pendingAttendance")
	public ArrayList<Object> getPendingAttendance() {
		try {
			ArrayList<Object> attendanceAndUsersList = this.attendanceRepository.findPendingAttendanceAndUsers1();
			return attendanceAndUsersList;
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
	}
	
	@GetMapping("/attendance/pendingAttendanceMentor")
	public ArrayList<Object> getPendingAttendanceMentor(@RequestParam ("mentorEmail") String mentorEmail) 
	{
		try 
		{
			ArrayList<Object> attendanceAndUsersList = this.attendanceRepository.findPendingAttendanceAndUsers2(mentorEmail);
			return attendanceAndUsersList;
		} 
		catch (Exception ex) 
		{
			System.out.println(ex);
			return null;
		}
	}
	
	@GetMapping("/attendance/pendingAttendanceByProject")
	public ArrayList<Object> getPendingAttendanceByProject(@RequestParam String projectName, @RequestParam String email) {
		try {
			if(projectName.equals("All")&&email.equals("All"))
			{
				ArrayList<Object> attendanceAndUsersList = this.attendanceRepository.findPendingAttendanceAndUsers1();
				return attendanceAndUsersList;
			}
			else
			{
				if(email.equals("All"))
				{
			ArrayList<Object> attendanceAndUsersList = this.attendanceRepository.findPendingAttendanceAndUsersByProject1(projectName);
			return attendanceAndUsersList;
				}
				else
				{
					ArrayList<Object> attendanceAndUsersList = this.attendanceRepository.findPendingAttendanceAndUsersByProjectEmail1(email);
					return attendanceAndUsersList;
				}
			}
			
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
	}
	
	@GetMapping("/attendance/pendingAttendanceByMentor2")
	public ArrayList<Object> getPendingAttendanceByMentor2(@RequestParam("mentorEmail") String mentorEmail, @RequestParam String email) 
	{
		try 
		{	
				ArrayList<Object> attendanceAndUsersList = this.attendanceRepository.findPendingAttendanceAndUsersByProjectEmail2(mentorEmail,email);
				return attendanceAndUsersList;
		}
		catch (Exception ex) 
		{
			System.out.println(ex);
			return null;
		}
	}
	
	@PostMapping("/attendance/pendingAttendance")
	public int markPendingAttendance(@RequestParam("id") Long id, @RequestParam("markDate") String markDate, @RequestParam("status")  String status, @RequestParam("currentUser") String currentUser, @RequestParam("entryTime") String entryTime) {
		try {
			 Date markDateAsDate=new SimpleDateFormat("yyyy-MM-dd").parse(markDate);
			 System.out.println("Date value of Attendance: "+markDateAsDate);
			 Date entryDateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entryTime);
			AttendanceCompositeKey attendanceCompositeKey = new AttendanceCompositeKey(id, markDateAsDate);
			if (this.attendanceRepository.findById(attendanceCompositeKey).isPresent()) {
				Attendance attendance = this.attendanceRepository.findById(attendanceCompositeKey).get();
				attendance.setAttendanceStatus(status);
				if(status!="Pending")
				{
					attendance.setEntryByUser(currentUser);
					attendance.setEntryTimeUser(entryDateTime);
				}
				else
				{
					attendance.setEntryByUser(null);
					attendance.setEntryTimeUser(null);
				}
				this.attendanceRepository.save(attendance);
				return 1;
			} else {
				return 0;
			}
		} catch (Exception ex) {
			System.out.println(ex);
			return 0;
		}
	}

	@DeleteMapping("/attendance/reject")
	public int getUsersAttendanceByDate(@RequestParam String date, @RequestParam Long id) {
		try {
			 Date markDateAsDate=new SimpleDateFormat("dd/MM/yyyy").parse(date);
			this.attendanceRepository.deleteById(new AttendanceCompositeKey(id, markDateAsDate));
			return 1;
		} catch (Exception ex) {
			System.out.println(ex);
			return 0;
		}
	}
	
	@GetMapping("/attendance/acceptedAttendance")
	public ArrayList<Attendance> getAcceptedAttendance(@RequestParam Long id) {
		try {
			return this.attendanceRepository.findAcceptedAttendance(id);
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
	}
	
	@GetMapping("/attendance/rejectedAttendance")
	public ArrayList<Attendance> getRejectedAttendance(@RequestParam Long id) {
		try {
			return this.attendanceRepository.findRejectedAttendance(id);
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
	}
	@GetMapping("/attendance/pendingAttendanceById")
	public ArrayList<Attendance> getPendingAttendanceById(@RequestParam Long id) {
		try {
			return this.attendanceRepository.findPendingAttendanceById(id);
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
	}
	
	@GetMapping("/display")
	public List<Holidaymaster> holidaydropdownDisplay() {
		
	
		try
		{
			List<Holidaymaster> holidaydropdown=this.holidaymasterRepository.findAll();
			return holidaydropdown;
		}
		catch(Exception ex)
		{
			System.out.println(ex);
			return null;
		}
	}
	
	
	
	
	
	
	
	 @PostMapping("/submitdate")
		public int submitanswer5( @RequestParam("id") Long id, @RequestParam("holidaydate") String holidaydate, @RequestParam("currentUser") String currentUser, @RequestParam("entryTime") String entryTime) 
	    {
			try {	SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
			Date x=formatter.parse(holidaydate);
			Date entryDateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entryTime);
				this.dateHolidayRepository.save(new Datewiseholiday(id,x,currentUser,entryDateTime));
					          
				return 1;
				}
			 catch (Exception ex) {
				System.out.println(ex);
				return 0;
			}
		}
	 
	 @PostMapping("/submitdate2")
		public int updatehol( @RequestParam("id") long id, @RequestParam("holidaydate") String holidaydate, @RequestParam("hdate") String hdate, @RequestParam("currentUser") String currentUser, @RequestParam("entryTime") String entryTime) 
	    {
	    	
	
			try {
				
				SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
			Date x=formatter.parse(holidaydate);
				
				java.sql.Date x1=new java.sql.Date(Long.parseLong(hdate));
				Date entryDateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entryTime);
				
				this.dateHolidayRepository.updatehol(id,x,x1,currentUser,entryDateTime);
					         
				return 1;
				}
			 catch (Exception ex) {
				System.out.println(ex);
				return 0;
			}
		}
	 
	 @GetMapping("/currentyearwiseholiday")
		public ArrayList<Object> holidaydropdownyearDisplay( @RequestParam("year") String year1) {
			
		
			try
			{
				int year=Integer.valueOf(year1);
				//java.util.Date year=new SimpleDateFormat("yyyy").parse(year1);
				ArrayList<Object> datewiseholiday=this.dateHolidayRepository.findYear(year);
				return datewiseholiday;
			}
			catch(Exception ex)
			{
				System.out.println(ex);
				return null;
			}
		}
	 
	 
	 @GetMapping("/viewquessetwise")
		public ArrayList<Object> Viewquessetwise( @RequestParam("setid") long setid) {
			
		
			try
			{
				
				ArrayList<Object> listofques=this.questionSetRepository.findQues(setid);
				return listofques;
			}
			catch(Exception ex)
			{
				System.out.println(ex);
				return null;
			}
		}
	 
	 
	 
	 
}


	
	
	
	
	
	


