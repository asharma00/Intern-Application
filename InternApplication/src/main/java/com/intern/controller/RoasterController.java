package com.intern.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.intern.model.CustomRoaster;
import com.intern.model.Roaster;
import com.intern.model.SetMaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.intern.repository.RoasterRepository;
import com.intern.repository.UserRepository;

@RestController
@RequestMapping("/api/v3")
public class RoasterController {

	@Autowired
	private RoasterRepository roasterRepository;
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/roaster/getListDate")
	public ArrayList<Object> getAllDay()
	{
		try
		{
		ArrayList<Object>  dayList=new ArrayList<>();
		SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
		String[] days = new String[] { "SUNDAY", "MONDAY", "TUESDAY", "WENDESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);	
		for(int index = 0 ; index < 17; index++){
		    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		   // if( dayOfWeek!=Calendar.SUNDAY && dayOfWeek!=Calendar.SATURDAY) {
		    	if( dayOfWeek!=Calendar.SUNDAY) {
		      
		      //  System.out.println("Current day = " + days[dayOfWeek - 1]);
		        Object[] day = new Object[3];
		        day[0]=cal.getTime();
		        day[1]=formatDate.format(cal.getTime());
		        day[2]=days[dayOfWeek - 1];
		        dayList.add(day);
		       
		    }
		    cal.add(Calendar.DATE, 1);
		}
		return dayList;
	}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	@GetMapping("roaster/getAllIntern")
	
	public CustomRoaster getAllIntern(@RequestParam String projectName, @RequestParam String email) {	
		try
		{
			CustomRoaster customRoaster= new CustomRoaster();
			SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
			ArrayList<Object>  dayList=new ArrayList<>();
			String[] days = new String[] { "SUNDAY", "MONDAY", "TUESDAY", "WENDESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			/*
			int month = cal.get(Calendar.MONTH);
			
			 * int year; if(month<11) { year=cal.get(Calendar.YEAR); } else {
			 * year=cal.get(Calendar.YEAR)+1; } cal.set(year, month+1, 1);
			 */
			
			//int monthMaxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			//for(int index = 0 ; index < monthMaxDays; index++){
			for(int index = 0 ; index < 17; index++){
			    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			   // if( dayOfWeek!=Calendar.SUNDAY && dayOfWeek!=Calendar.SATURDAY) {
			    	if( dayOfWeek!=Calendar.SUNDAY) {
			       // System.out.println(formatDate.format(cal.getTime()));
			       // System.out.println("Current day = " + days[dayOfWeek - 1]);
			        Object[] day = new Object[3];
			        day[0]=cal.getTime();
			        day[1]=formatDate.format(cal.getTime());
			        day[2]=days[dayOfWeek - 1];
			        dayList.add(day);
			       
			    }
			    cal.add(Calendar.DATE, 1);
			}
			
			
			
			
			if(email.equals("All"))
			{
				ArrayList<Object> internList = this.roasterRepository.getAllIntern(projectName);
				customRoaster.setDayList(dayList);
				customRoaster.setInternList(internList);
				
			}
			else
			{
				ArrayList<Object> internList = this.roasterRepository.getInternByMail(projectName, email);
				customRoaster.setDayList(dayList);
				customRoaster.setInternList(internList);
				
			}
			
			return customRoaster;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	@GetMapping("roaster/getRoasterData")
	public String getRoaster(@RequestParam Long studentid, @RequestParam String roasterDate) 
	{
		try
		{
			String workFrom=null;
			Date dateValue=new SimpleDateFormat("dd-MM-yyyy").parse(roasterDate);
			workFrom = this.roasterRepository.getWorkFrom(studentid, dateValue);
		
			return workFrom;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	@PostMapping("/roaster/saveIntern")
	public int saveInternData(@RequestParam("studentId") Long studentId, @RequestParam("dateValue") String dateValue, @RequestParam("workFromValue") String workFromValue, @RequestParam("currentUser") String currentUser, @RequestParam("entryTime") String entryTime) {
		
		
		try {
			Date roasterDate=new SimpleDateFormat("dd-MM-yyyy").parse(dateValue);
			Date entryDateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entryTime);
			this.roasterRepository.save(new Roaster(studentId, roasterDate, workFromValue, currentUser, entryDateTime));
			return 1;
			}
		 catch (Exception ex) {
			System.out.println(ex);
			return 0;
		}
	}
}
