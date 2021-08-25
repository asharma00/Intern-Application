package com.intern.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intern.model.Mentor;
import com.intern.model.MentorHistory;
import com.intern.model.MentorHistoryKey;
import com.intern.model.User;
import com.intern.repository.MentorHistoryRepo;
import com.intern.repository.MentorRepo;
import com.intern.repository.UserRepository;

@RestController
@RequestMapping("/api/mentor")
public class MentorController {

	@Autowired
	private MentorRepo mentorRepo;
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private MentorHistoryRepo mentorHistoryRepo;
	
	
	@PostMapping("/add/addMentor")
	public int addMentorByEmail(@RequestParam String email, @RequestParam String name, @RequestParam String designation, @RequestParam("currentUser") String currentUser, @RequestParam("entryTime") String entryTime) 
	{
		try 
		{
			Date entryDateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entryTime);
		
        //  Mentor mentor= mentorRepo.findById(email).get();
          if(mentorRepo.findById(email).isPresent())
          {
        	  System.out.println("Inside null value field:>   ");
        	  return 0;
          }
          else
          {
        	  Mentor m=new Mentor(name, email, designation,1,currentUser,entryDateTime);
        	  this.mentorRepo.save(m);
        	  return 1;
          }
			
		} catch (Exception ex) {
			System.out.println(ex);
			return 0;
		}
	}
	
	
	@GetMapping("get/getMentor")
	public List<Mentor> getMentor()
	{
		List<Mentor> mList=null;
		try
		{
		mList=	mentorRepo.findByFlagValue(1);
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return mList;
	}
	
	@GetMapping("get/getMentorByMailId")
	public ArrayList<Object>  getMentorByMailId(@RequestParam String email)
	{
	return	this.mentorRepo.getExistingMentor(email);
	}
	
	
	@PostMapping("/saveNewMentor")
	public int saveNewMentor(@RequestParam String internEmail, @RequestParam String newMentor) {
		try
		{
	User user = this.userRepository.findByEmail(internEmail);
	user.setMentorEmail(newMentor);	
	userRepository.save(user);
	MentorHistory mh= new MentorHistory();
	
	MentorHistoryKey mhk=new MentorHistoryKey();
	mhk.setStudentId(user.getId());
	mhk.setMentorEmail(newMentor);
	mhk.setFromDate(new Date());
	mh.setId(mhk);
	
	mentorHistoryRepo.save(mh);
	return 1;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
	}

	
	
	@PostMapping("/updateExistingMentor")
	public int updateExistingMentor(@RequestParam String internEmail,@RequestParam String existingMentor ,@RequestParam String newMentor) {
		try
		{
	User user = this.userRepository.findByEmail(internEmail);
	user.setMentorEmail(newMentor);	
	userRepository.save(user);
	MentorHistory mhOld=mentorHistoryRepo.findMentor(user.getId(), existingMentor);
	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
	
	if(sdf.format(mhOld.getId().getFromDate()).equals(sdf.format(new Date())))
	{
		mentorHistoryRepo.delete(mhOld);
		
	}
	else
	{
		Date today = new Date();
		Date yesterday = new Date(today.getTime() - (1000 * 60 * 60 * 24));
		mhOld.setToDate(yesterday);
		mentorHistoryRepo.save(mhOld);	
	}
	
	
	
	
	
	MentorHistory mh= new MentorHistory();
	
	MentorHistoryKey mhk=new MentorHistoryKey();
	mhk.setStudentId(user.getId());
	mhk.setMentorEmail(newMentor);
	mhk.setFromDate(new Date());
	mh.setId(mhk);
	
	mentorHistoryRepo.save(mh);
	return 1;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
	}

	
	
	//for editing mentor details
	@PostMapping("/updateMentorDetails")
	public int editMentorDetails(@RequestParam("newEmail") String newEmail, @RequestParam("oldEmail") String oldEmail, @RequestParam("name") String name, @RequestParam("designation") String designation, @RequestParam("currentUser") String currentUser, @RequestParam("entryTime") String entryTime)
	{
		try 
		{
			Date entryDateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entryTime);
			if(mentorRepo.findById(oldEmail).isPresent())
			{
				Mentor m=new Mentor(name,newEmail,designation,1,currentUser,entryDateTime);
				this.mentorRepo.deleteMentorForUpdate(oldEmail);
	        	this.mentorRepo.save(m);
				this.userRepository.updateMentorDetails(oldEmail,newEmail);
				this.userRepository.updateUserMentor(oldEmail,newEmail);
				this.mentorHistoryRepo.updateMentorEmail(oldEmail,newEmail);
				return 1;
			}
			else
			{
				System.out.println("Inside null value field:>   "+oldEmail);
				return 0;
			}
		} 
		catch (Exception ex) 
		{
			System.out.println(ex);
			return 0;
		}
	}
	
	
	//for deleting mentor details
	@PostMapping("/deleteMentorDetails")
	public int deleteMentorDetails(@RequestParam String oldEmail,@RequestParam("currentUser") String currentUser, @RequestParam("entryTime") String entryTime)
	{
		try
		{
			Date entryDateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entryTime);
			if(mentorRepo.findById(oldEmail).isPresent())
			{
				this.userRepository.updateMentorEmail(oldEmail,false);
				this.mentorRepo.updateMentorFlag(oldEmail,0,currentUser,entryDateTime);
				return 1;
			}
			else
			{
				System.out.println("Inside null value field:>   ");
				return 0;
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex);
			return 0;
		}
	}
	
}
