package com.intern.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.intern.common.RandomPasswordGenerator;
import com.intern.model.Application;
import com.intern.repository.ApplicationRepository;
import com.intern.repository.UserRepository;
import com.intern.service.DatabaseFileService;
import com.intern.service.MailService;

@RestController


public class IndexController {

	@Autowired
	private ApplicationRepository applicationRepository;
	@Autowired
	private DatabaseFileService fileStorageService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MailService mailService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// save application
	@PostMapping("api/v4/applications")
	public String createApplication(@RequestParam("fullName") String fullName,@RequestParam("age") String age,@RequestParam("email") String email,@RequestParam("parentName") String parentName,@RequestParam("address") String address,@RequestParam("schoolName") String schoolName,@RequestParam("contact") String contact,@RequestParam("schoolId") String schoolId,@RequestParam("score") String score,@RequestParam("status") String status,@RequestParam("area_of_interest") String area_of_interest) {
		try {SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
		        Date dob=formatter.parse(age);
			Application application=new Application (fullName,dob,email.toLowerCase(),parentName,address,schoolName,contact,schoolId,score,status,area_of_interest, new Date());
			this.applicationRepository.save(application);
			
			String s=Long.toString(application.getId());
			System.out.println("Value of Id>"+s);
			String subject="Apply for Internship";
			String message="Dear "+fullName+
					"You have successfully applied for Internship in CRIS.";
			mailService.sentApplyNotification(email,subject,message);
			return s;
		}
		catch(Exception ex) {
			String subject="Apply for Internship at CRIS Failed";
			String message="Dear "+fullName+
					"Your internship application at CRIS is failed. Kindly try again.";
			mailService.sentApplyNotification(email,subject,message);
			return "Email Already Exists";
		}
	}
		
		
		@GetMapping("api/v4/checkuser")
		public  ArrayList<Object> checkuser(@RequestParam("email") String email) 
		{
			 	
			try {
				
				//java.sql.Date x1=new java.sql.Date(Long.parseLong(h3));
				ArrayList<Object> check= this.userRepository.finduser(email,true);
				return check;
			
				
				
				}
			 catch (Exception ex) {
				System.out.println(ex);
				return null;
			}

		}
		
		@RequestMapping("api/v4/mailpass")
		public Integer sendNotification11(@RequestParam String email, @RequestParam String contact) {
			try 
			{
			
				String password =RandomPasswordGenerator.generatePassword(8);
			System.out.println(password);
				String s=passwordEncoder.encode(password);
				//System.out.println(s);	
				this.userRepository.savenewpass(email,s);
				String subject="Your password reset successfully";
				String message="Your new password is " +password;
				mailService.sentApplyNotification(email, subject, message);
				return 1;
				}
			 catch (Exception ex) {
				System.out.println(ex);
				return 0;
			}
		}
		
		
		
		
		@PostMapping("api/v4/upload")
		
		
		public void upload(@RequestParam String id, @RequestParam("file") MultipartFile file,
				@RequestParam("type") int type,@RequestParam("email") String email ) throws IOException {
			
			System.out.println("In method");			
			System.out.println(id);
			
			String os=System.getProperty("os.name");
			String existingfileName = fileStorageService.getFileName(Long.parseLong(id), type);
			if(!Objects.isNull(existingfileName)) {				
				System.out.println("File being replaced");				
				this.userRepository.delfileIndividual(Long.parseLong(id), type);			
				
				if(os.startsWith("Windows")){
					Path fileNameAndPath = Paths.get("c:/intern_uploads/uploads", id, Integer.toString(type));		
					try {
						FileUtils.deleteDirectory(new File(fileNameAndPath.toString()));
						System.out.println("Deleted");
					} catch (Exception e) {
						System.out.println(e.getMessage());			
					}}
			      
			      else if(os.startsWith("Unix"))
			        {
			        	Path fileNameAndPath = Paths.get("/root/intern_uploads/uploads", id, Integer.toString(type));
			        	try {
			        		FileUtils.deleteDirectory(new File(fileNameAndPath.toString()));
			    		} catch (Exception e) {
			    			System.out.println(e.getMessage());
			    		}
			        }
			       
			        
			        else if(os.startsWith("Linux"))
			        {
			        	Path fileNameAndPath = Paths.get("/var/www/html/intern_uploads/uploads", id, Integer.toString(type));
			        	try {
			        		FileUtils.deleteDirectory(new File(fileNameAndPath.toString()));
			    		} catch (Exception e) {
			    			System.out.println(e.getMessage());
			    		}
			        }				
			}
			
			else {			
				System.out.println("New File");
			}
			
	        if(os.startsWith("Windows")){

			Path fileNameAndPath = Paths.get("C:/intern_uploads/uploads", id, Integer.toString(type));
			try {
				Files.createDirectories(fileNameAndPath);
				Files.copy(file.getInputStream(), fileNameAndPath.resolve(file.getOriginalFilename()));
				fileStorageService.storeFile(Long.valueOf(id), file.getOriginalFilename(), type);
				
			} catch (Exception e) {
				if(type==0)
				{
					String subject="Document Upload Failed";
					String message="Your resume is not uploaded. Kindly mail your resume. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
					mailService.sentApplyNotification(email,subject,message);
				}
				if(type==1)
				{
					String subject="Photo Upload Failed";
					String message="Your Photo is not uploaded. Kindly mail your passport size photo. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
					mailService.sentApplyNotification(email,subject,message);
				}
				if(type==2)
				{
					String subject="Document Upload Failed";
					String message="Your Certificate is not uploaded. Kindly mail your Certificate. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
					mailService.sentApplyNotification(email,subject,message);
				}
				if(type==3)
				{
					String subject="Document Upload Failed";
					String message="Your ID Proof is not uploaded. Kindly mail your Id Proof. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
					mailService.sentApplyNotification(email,subject,message);
				}
				System.out.println(e);
			}
			}
	        
	        

	        else if(os.startsWith("Unix"))
	        {
	        	Path fileNameAndPath = Paths.get("/root/intern_uploads/uploads", id, Integer.toString(type));
	        	try {
	    			Files.createDirectories(fileNameAndPath);
	    			Files.copy(file.getInputStream(), fileNameAndPath.resolve(file.getOriginalFilename()));
	    			fileStorageService.storeFile(Long.valueOf(id), file.getOriginalFilename(), type);
	    		} catch (Exception e) {
	    			if(type==0)
					{
						String subject="Document Upload Failed";
						String message="Your resume is not uploaded. Kindly mail your resume. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
						mailService.sentApplyNotification(email,subject,message);
					}
					if(type==1)
					{
						String subject="Photo Upload Failed";
						String message="Your Photo is not uploaded. Kindly mail your passport size photo. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
						mailService.sentApplyNotification(email,subject,message);
					}
					if(type==2)
					{
						String subject="Document Upload Failed";
						String message="Your Certificate is not uploaded. Kindly mail your Certificate. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
						mailService.sentApplyNotification(email,subject,message);
					}
					if(type==3)
					{
						String subject="Document Upload Failed";
						String message="Your ID Proof is not uploaded. Kindly mail your Id Proof. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
						mailService.sentApplyNotification(email,subject,message);
					}

	    			
	    			System.out.println(e);
	    		}}
	       
	        
	        else if(os.startsWith("Linux"))
	        {
	        	Path fileNameAndPath = Paths.get("/var/www/html/intern_uploads/uploads", id, Integer.toString(type));
	        	try {
	    			Files.createDirectories(fileNameAndPath);
	    			Files.copy(file.getInputStream(), fileNameAndPath.resolve(file.getOriginalFilename()));
	    			fileStorageService.storeFile(Long.valueOf(id), file.getOriginalFilename(), type);
	    		} catch (Exception e) {
	    			if(type==0)
					{
						String subject="Document Upload Failed";
						String message="Your resume is not uploaded. Kindly mail your resume. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
						mailService.sentApplyNotification(email,subject,message);
					}
					if(type==1)
					{
						String subject="Photo Upload Failed";
						String message="Your Photo is not uploaded. Kindly mail your passport size photo. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
						mailService.sentApplyNotification(email,subject,message);
					}
					if(type==2)
					{
						String subject="Document Upload Failed";
						String message="Your Certificate is not uploaded. Kindly mail your Certificate. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
						mailService.sentApplyNotification(email,subject,message);
					}
					if(type==3)
					{
						String subject="Document Upload Failed";
						String message="Your ID Proof is not uploaded. Kindly mail your Id Proof. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
						mailService.sentApplyNotification(email,subject,message);
					}

	    			System.out.println(e);
	    		}
	        }
	        
		}
		
	        /*String os=System.getProperty("os.name");
	        if(os.startsWith("Windows")){

			Path fileNameAndPath = Paths.get("C:/intern_uploads/uploads", id, Integer.toString(type));
			try {
				Files.createDirectories(fileNameAndPath);
				Files.copy(file.getInputStream(), fileNameAndPath.resolve(file.getOriginalFilename()));
				fileStorageService.storeFile(Long.valueOf(id), file.getOriginalFilename(), type);
				
			} catch (Exception e) {
				if(type==0)
				{
					String subject="Document Upload Failed";
					String message="Your resume is not uploaded. Kindly mail your resume. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
					mailService.sentApplyNotification(email,subject,message);
				}
				if(type==1)
				{
					String subject="Photo Upload Failed";
					String message="Your Photo is not uploaded. Kindly mail your passport size photo. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
					mailService.sentApplyNotification(email,subject,message);
				}
				if(type==2)
				{
					String subject="Document Upload Failed";
					String message="Your Certificate is not uploaded. Kindly mail your Certificate. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
					mailService.sentApplyNotification(email,subject,message);
				}
				if(type==3)
				{
					String subject="Document Upload Failed";
					String message="Your ID Proof is not uploaded. Kindly mail your Id Proof. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
					mailService.sentApplyNotification(email,subject,message);
				}
				System.out.println(e);
			}
			}
	        
	        

	        else if(os.startsWith("Unix"))
	        {
	        	Path fileNameAndPath = Paths.get("/root/intern_uploads/uploads", id, Integer.toString(type));
	        	try {
	    			Files.createDirectories(fileNameAndPath);
	    			Files.copy(file.getInputStream(), fileNameAndPath.resolve(file.getOriginalFilename()));
	    			fileStorageService.storeFile(Long.valueOf(id), file.getOriginalFilename(), type);
	    		} catch (Exception e) {
	    			if(type==0)
					{
						String subject="Document Upload Failed";
						String message="Your resume is not uploaded. Kindly mail your resume. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
						mailService.sentApplyNotification(email,subject,message);
					}
					if(type==1)
					{
						String subject="Photo Upload Failed";
						String message="Your Photo is not uploaded. Kindly mail your passport size photo. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
						mailService.sentApplyNotification(email,subject,message);
					}
					if(type==2)
					{
						String subject="Document Upload Failed";
						String message="Your Certificate is not uploaded. Kindly mail your Certificate. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
						mailService.sentApplyNotification(email,subject,message);
					}
					if(type==3)
					{
						String subject="Document Upload Failed";
						String message="Your ID Proof is not uploaded. Kindly mail your Id Proof. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
						mailService.sentApplyNotification(email,subject,message);
					}

	    			
	    			System.out.println(e);
	    		}}
	       
	        
	        else if(os.startsWith("Linux"))
	        {
	        	Path fileNameAndPath = Paths.get("/var/www/html/intern_uploads/uploads", id, Integer.toString(type));
	        	try {
	    			Files.createDirectories(fileNameAndPath);
	    			Files.copy(file.getInputStream(), fileNameAndPath.resolve(file.getOriginalFilename()));
	    			fileStorageService.storeFile(Long.valueOf(id), file.getOriginalFilename(), type);
	    		} catch (Exception e) {
	    			if(type==0)
					{
						String subject="Document Upload Failed";
						String message="Your resume is not uploaded. Kindly mail your resume. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
						mailService.sentApplyNotification(email,subject,message);
					}
					if(type==1)
					{
						String subject="Photo Upload Failed";
						String message="Your Photo is not uploaded. Kindly mail your passport size photo. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
						mailService.sentApplyNotification(email,subject,message);
					}
					if(type==2)
					{
						String subject="Document Upload Failed";
						String message="Your Certificate is not uploaded. Kindly mail your Certificate. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
						mailService.sentApplyNotification(email,subject,message);
					}
					if(type==3)
					{
						String subject="Document Upload Failed";
						String message="Your ID Proof is not uploaded. Kindly mail your Id Proof. (mail id: cris.manoj05@gmail.com; nkgupta.mmmec@gmail.com)";
						mailService.sentApplyNotification(email,subject,message);
					}

	    			System.out.println(e);
	    		}
	        }*/
	        
		
		
		
		@PostMapping("api/v4/applications/Initiated")
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




@PostMapping("api/v4/uploadcert")


public void uploadcert(@RequestParam String id, @RequestParam("file") MultipartFile file,
		@RequestParam("type") int type) throws IOException {
    String os=System.getProperty("os.name");
    Long id1=Long.valueOf(id);
    this.userRepository.delfile4(id1);
    if(os.startsWith("Windows")){

	Path fileNameAndPath = Paths.get("c:/intern_uploads/uploads", id, Integer.toString(type));
	try {
		FileUtils.deleteDirectory(new File(fileNameAndPath.toString()));
		Files.createDirectories(fileNameAndPath);
		Files.copy(file.getInputStream(), fileNameAndPath.resolve(file.getOriginalFilename()));
		fileStorageService.storeFile(Long.valueOf(id), file.getOriginalFilename(), type);
	} catch (Exception e) {
		System.out.println(e);
	}}
    
    

    else if(os.startsWith("Unix"))
    {
    	Path fileNameAndPath = Paths.get("/root/intern_uploads/uploads", id, Integer.toString(type));
    	try {
    		FileUtils.deleteDirectory(new File(fileNameAndPath.toString()));
			Files.createDirectories(fileNameAndPath);
			Files.copy(file.getInputStream(), fileNameAndPath.resolve(file.getOriginalFilename()));
			fileStorageService.storeFile(Long.valueOf(id), file.getOriginalFilename(), type);
		} catch (Exception e) {
			System.out.println(e);
		}}
   
    
    else if(os.startsWith("Linux"))
    {
    	Path fileNameAndPath = Paths.get("/var/www/html/intern_uploads/uploads", id, Integer.toString(type));
    	try {
    		FileUtils.deleteDirectory(new File(fileNameAndPath.toString()));
			Files.createDirectories(fileNameAndPath);
			Files.copy(file.getInputStream(), fileNameAndPath.resolve(file.getOriginalFilename()));
			fileStorageService.storeFile(Long.valueOf(id), file.getOriginalFilename(), type);
		} catch (Exception e) {
			System.out.println(e);
		}
    }
}
}

    
   













