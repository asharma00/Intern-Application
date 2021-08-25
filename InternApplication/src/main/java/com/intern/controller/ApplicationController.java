package com.intern.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intern.exception.ResourceNotFoundException;
import com.intern.model.Application;
import com.intern.model.Flags;
import com.intern.model.MentorHistory;
import com.intern.model.MentorHistoryKey;
import com.intern.model.User;
import com.intern.repository.ApplicationRepository;
import com.intern.repository.Flagsrepository;
import com.intern.repository.MentorHistoryRepo;

import com.intern.repository.UserRepository;
import com.intern.service.MailService;
import com.intern.service.UserServiceImpl;
import com.intern.web.dto.ApplicationBulkEditDto;
import com.intern.web.dto.ApplicationInterviewCallDto;
//import com.intern.web.dto.ApplicationSelectionDto;
import com.intern.web.dto.UserRegistrationDto;

@RestController
@RequestMapping("/api/v1")
public class ApplicationController {
	@Autowired
	private ApplicationRepository applicationRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private Flagsrepository flagsrepository;
	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private MailService mailService;
	@Autowired
	private MentorHistoryRepo mentorHistoryRepo;
	// get application
	@GetMapping("applications")
	public List<Application> getAllApplications(String status) {
		System.out.println(status);
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(status.split(", ")));
		return this.applicationRepository.findApplication(list);
	}
	
	@GetMapping("/applicationsSpecificToInterview")
	public List<ApplicationInterviewCallDto> getAllApplicationsSpecificToInterview(String status) {
		System.out.println(status);
			return this.applicationRepository.getApplicationsToGiveInterviewMarks();		
	}
	
	@GetMapping("/applicationsSpecificToSelection")
	public List<ApplicationInterviewCallDto> getAllApplicationsSpecificToSelection(String status) {
		System.out.println(status);
			return this.applicationRepository.getApplicationsToSelectInterns();		
	}
	@GetMapping("/applicationsSelectedIntern")
	public List<ApplicationInterviewCallDto> getAllApplicationsSelected(String status) {
		System.out.println(status);
			return this.applicationRepository.getApplicationsOfSelectedIntern(status);		
	}
	@PostMapping("applications/approve")
	public int approveAnApplication(Long id) {
		int result = 0;
		try {
			this.applicationRepository.AcceptedApplication(id);
			result = 1;
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return result;
	}

	@PostMapping("applications/reject")
	public int rejectAnApplication(Long id) {
		int result = 0;
		try {
			this.applicationRepository.RejectedApplication(id);
			result = 1;
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return result;
	}
    
	@PostMapping("applications/rejectAfterInterview")
	public int rejectAfterApplication(@RequestParam("applicantId") Long applicantId) {
		System.out.println("Your applicagtion is is>" +applicantId);
		//Long id=Long.parseLong(applicantId);
		int result = 0;
		try {
			this.applicationRepository.RejectedApplicationAfterInterview(applicantId);
			result = 1;
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return result;
	}
	
	@PostMapping("applications/rejectAfterJoiningApproved")
	public int rejectAfterJoiningApproved(@RequestParam("applicantId") Long applicantId) {
		System.out.println("Your applicagtion is is>" +applicantId);
		//Long id=Long.parseLong(applicantId);
		int result = 0;
		try {
			this.applicationRepository.RejectedApplicationAfterJoining(applicantId);
			result = 1;
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return result;
	}
	// get application by id
	@GetMapping("/applications/{id}")
	public ResponseEntity<Application> getApplicationById(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException {
		Application application = applicationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Application not found for this email"));
		return ResponseEntity.ok().body(application);
	}

	

	@DeleteMapping("/applications/{id}")
	public Map<String, Boolean> deleteApplication(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException {
		Application application = applicationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Application not found for this id"));
		this.applicationRepository.delete(application);
		Map<String, Boolean> response = new HashMap<>();
		response.put("delete", Boolean.TRUE);
		return response;
	}

	@PostMapping("/applications/status")
	public String setStatusForApplication(@RequestParam("userId") Long userId, @RequestParam("status") String status) {
		String result = "";
		try {
			Application application = this.applicationRepository.findById(userId).get();
			application.setStatus(status);
			this.applicationRepository.save(application);
			result = status;
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return result;
	}
	@PostMapping("/applications/rejectAfterExam")
	public String setStatusForApplicationAfterReject(@RequestParam("applicationId") Long applicationId, @RequestParam("status") String status) {
		String result = "";
		try {
			Application application = this.applicationRepository.findById(applicationId).get();
			String email=application.getEmail();
			String name=application.getFullName();
			String phoneNo=application.getContact();
			application.setStatus(status);
			this.applicationRepository.save(application);
			this.mailService.sendNotification1(email,name, phoneNo);
			result = status;
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return result;
	}
	
	@PostMapping("/applications/statusForInterview")
	public String setStatusForApplicationInInterview(@RequestParam("applicationId") Long applicationId, @RequestParam("status") String status, @RequestParam("startDate") String startDate,@RequestParam("meetingLink") String meetingLink) {
		String result = "";
		try {
			Date interviewTime = new Date(Long.parseLong(startDate));
			Application application = this.applicationRepository.findById(applicationId).get();
			application.setStatus(status);
			this.applicationRepository.save(application);
			String fullName = application.getFullName();
			String userEmail = application.getEmail();
			String contact = application.getContact();
			Long userId= userRepository.getUserByEmail(userEmail).getId();
			result = status;
			Flags f=flagsrepository.findById(userId).get();
			f.setInterview_time(interviewTime);
			f.setInterview_meeting_link(meetingLink);
			flagsrepository.save(f);
			
			mailService.sendNotification3(userEmail, fullName, interviewTime,contact,meetingLink );
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return result;
	}
	
	@PostMapping("/applications/relieve")
	public String setStatusRelieved(@RequestParam("userId") Long userId,@RequestParam("relievingDate") String relievingDate,@RequestParam("email") String email) {
		String result = "";
		try {
			Application application = this.applicationRepository.findById(userId).get();
			String contactNo=application.getContact();
			String fullName=application.getFullName();
			application.setStatus("Relieved");
			this.applicationRepository.save(application);
			long id=this.userRepository.getUserid(userId);
			User u=this.userRepository.findByEmail(email);
			u.setRelieve_date(new SimpleDateFormat("dd-MM-yyyy").parse(relievingDate));
			u.setActiveFlagValue(false);
			this.userRepository.relieve(id);
			this.userRepository.save(u);
			mailService.sendNotification9(email, fullName,contactNo);
			result = "Relieved";
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return result;
	}
	
	@GetMapping("/applicationsForBulkEdit")
	public ArrayList<ApplicationBulkEditDto> getApplicationsForBulkEdit() {
		
		return this.applicationRepository.getBulkEditApplications();
	}
		
	@GetMapping("/getFlagForDates")
	public Date getFlagForDate(@RequestParam("applicationId") Long id) {

		Long userId = this.userRepository.getUserid(id);        
		if(!Objects.isNull(userId)) {

			System.out.println(userId);
			if(this.flagsrepository.existsById(userId)) {

				System.out.println("Flag exists");      
				Flags flag = this.flagsrepository.findById(userId).get();
				Date time = flag.getExamdate();
				if(!Objects.isNull(time)) {
					return time;
				}
			}
			else {
				return null;
			}
		}
		return null;
    }
	
	@GetMapping("/applicationsOffline")
	public ArrayList<Application> applicationsOffline(){
		return this.applicationRepository.getOfflineExamApplications();
	}
	
	@PostMapping("/saveOfflineDetailsInFlag")
	public Integer saveOfflineDetailsInFlag(@RequestParam("applicationId") Long id,
	        @RequestParam Integer javaOfflineObtained, @RequestParam Integer javaOfflineTotal,
			@RequestParam Integer pythonOfflineObtained, @RequestParam Integer pythonOfflineTotal,
			@RequestParam Integer androidOfflineObtained, @RequestParam Integer androidOfflineTotal,
			@RequestParam Integer sqlOfflineObtained, @RequestParam Integer sqlOfflineTotal,
			@RequestParam Integer cloudOfflineObtained, @RequestParam Integer cloudOfflineTotal,
			@RequestParam Long examStart, @RequestParam Long examEnd) {
		
		System.out.println("In save offline function");
		
		Application a  = this.applicationRepository.findById(id).get();
		a.setStatus("ExamOver");
		this.applicationRepository.save(a);
		
		Long userId = this.userRepository.getUserid(id);  
		if(!Objects.isNull(userId)) {
			
			System.out.println(userId);
			if(this.flagsrepository.existsById(userId)) {

				System.out.println("Flag exists");      
				Flags flag = this.flagsrepository.findById(userId).get();
				
				flag.setAndroid_marks(androidOfflineObtained);
				flag.setAndroid_total(androidOfflineTotal);
				flag.setCloud_marks(cloudOfflineObtained);
				flag.setCloud_total(cloudOfflineTotal);
				flag.setSql_marks(sqlOfflineObtained);
				flag.setSql_total(sqlOfflineTotal);
				flag.setJava_marks(javaOfflineObtained);
				flag.setJava_total(javaOfflineTotal);
				flag.setPython_marks(pythonOfflineObtained);
				flag.setPython_total(pythonOfflineTotal);
				flag.setStartdate(new Date(examStart));
				flag.setEnddate(new Date(examEnd));
				flag.setFlag(1);
				flag.setTotal_marks(androidOfflineObtained + cloudOfflineObtained + javaOfflineObtained + pythonOfflineObtained + sqlOfflineObtained);
				flag.setMarks_out_of(androidOfflineTotal + cloudOfflineTotal + javaOfflineTotal + pythonOfflineTotal + sqlOfflineTotal);
				this.flagsrepository.save(flag);
				
				return 1;
			}
			else {
				return 0;
			}
		}
		return 0;
	}
	
	//Latest Integrate
	@GetMapping("/applicationsForInterviewCall")
	public ArrayList<ApplicationInterviewCallDto> applicationsForInterviewCall() {
		//System.out.println(this.applicationRepository.applicationsForInterviewCall().size());
		return this.applicationRepository.applicationsForInterviewCall();
	}
	
	
	@RequestMapping("/setJoining")
	public void setJoining(@RequestParam String userEmail, @RequestParam String fullName,
			@RequestParam String startDate, @RequestParam String endDate,@RequestParam long id, @RequestParam String contact, @RequestParam String mentor, @RequestParam String project) throws ParseException {
		
		Long uid=this.userRepository.getUserid(id);
		Date jobStartDate = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
		Date jobEndDate = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
		if(Objects.isNull(uid)) {		
			System.out.println("UID CREATED");
			RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(48, 122)
			        .build();
			    String password= pwdGenerator.generate(20);
			    this.userServiceImpl.save1(new UserRegistrationDto(fullName, userEmail,  password));
			    Application a=applicationRepository.findByEmail(userEmail).get(0);
			    a.setStatus("Joined");
			    applicationRepository.save(a);
			    
			 this.mailService.sendNotificationJoined(userEmail,password, fullName, jobStartDate, jobEndDate, contact, mentor, project);		
		}
		
		this.userRepository.savedate(jobStartDate,jobEndDate,uid, mentor, project);
		 Application a=applicationRepository.findByEmail(userEmail).get(0);
		    a.setStatus("Joined");
		    applicationRepository.save(a); 
		    User u=userRepository.findByEmail(userEmail);
		    u.setJoin_date(jobStartDate);
		    u.setEnd_date(jobEndDate);
		    u.setMentorEmail(mentor);
		    u.setProject(project);
		    userRepository.save(u);
		    MentorHistoryKey mhk=new MentorHistoryKey(uid, mentor, jobStartDate);
		    MentorHistory mh=new MentorHistory();
		    mh.setId(mhk);
		    mh.setToDate(null);
		    mentorHistoryRepo.save(mh);
		mailService.sendNotification4(userEmail, fullName, jobStartDate, jobEndDate,contact);
	}
	
	@RequestMapping("/users/getUserByApplicationId")
	public User getUser(@RequestParam("applicantId") Long applicantId) {
		User u=null;
		try {
			Application application = this.applicationRepository.findById(applicantId).get();
			String email=application.getEmail();
		 u=userRepository.findByEmail(email);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return u;
	}
}

