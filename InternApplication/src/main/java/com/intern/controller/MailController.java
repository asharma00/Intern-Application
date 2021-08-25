package com.intern.controller;



import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.io.File;

import java.io.IOException;

import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.FileOutputStream;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;



import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import org.springframework.beans.factory.annotation.Autowired;


import com.intern.model.Application;
import com.intern.model.Mentor;
import com.intern.repository.ApplicationRepository;
import com.intern.repository.MentorRepo;
import com.intern.repository.RoleRepository;
import com.intern.repository.SetmasterRepository;
import com.intern.repository.UserRepository;
import com.intern.service.MailService;

@RestController
@RequestMapping("/api/v1")
public class MailController {

	@Autowired
	private MailService mailService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private SetmasterRepository setmasterRepository;

	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Autowired
	private MentorRepo mentorRepo; 
    
	@RequestMapping("/leaveMail1")
	public void sendNotification6(@RequestParam String email,@RequestParam String from,@RequestParam String to,@RequestParam String contact) {
		mailService.sendNotification6(email,from,to,contact);
	}


	@RequestMapping("/leaveMail2")
	public void sendNotification7(@RequestParam String email,@RequestParam String from,@RequestParam String to,@RequestParam String contact,@RequestParam String reason) {
		mailService.sendNotification7(email,from,to,contact,reason);
	}

	@RequestMapping("/mail1")
	public void sendNotification1(@RequestParam String userEmail, @RequestParam String fullName,@RequestParam String contact) {
		mailService.sendNotification1(userEmail, fullName,contact);
	}

	@RequestMapping("/mail1Reject")
	public void sendNotification1(@RequestParam Long id) {
		Application a = this.applicationRepository.findById(id).get();
		String fullName = a.getFullName();
		String userEmail = a.getEmail();
		String contact = a.getContact();
		mailService.sendNotification1(userEmail, fullName,contact);
	}

	@RequestMapping("/mail12")
	public void sendNotification12(@RequestParam String userEmail, @RequestParam String fullName,@RequestParam String contact) {
		mailService.sendAcceptNotification(userEmail, fullName,contact);
	}


	@RequestMapping("/mail2")
	public void sendNotification2(@RequestParam String userEmail, @RequestParam String fullName,
			@RequestParam String startDate, @RequestParam String contact, @RequestParam Long setId, @RequestParam String meetingLink) {
		Date examStartDate = new Date(Long.parseLong(startDate));
		Integer time = this.setmasterRepository.findById(setId).get().getSetTime();
		mailService.sendNotification2(userEmail, fullName, examStartDate,contact,time, meetingLink);
	}
	

/*	@RequestMapping("/mail6")
	public void sendNotification8(@RequestParam String userEmail, @RequestParam String fullName,
			@RequestParam String eDate, @RequestParam String contact, @RequestParam Long setId) {
		Date edate = new Date(Long.parseLong(eDate));
		if(Objects.isNull(setId))
			mailService.sendNotificationNew8(userEmail, fullName, edate,contact);
		else {
			Integer time = this.setmasterRepository.findById(setId).get().getSetTime();
			mailService.sendNotification8(userEmail, fullName, edate, contact, time);

		}

	}
*/
	@RequestMapping("/mail7")
	public void sendNotification9(@RequestParam String fullName,@RequestParam String userEmail,@RequestParam String contact) {
		mailService.sendNotification9(userEmail, fullName,contact);
	}


	@RequestMapping("/mail3")
	public void sendNotification3(@RequestParam Long applicationId, @RequestParam String startDate, @RequestParam String meetingLink) {
		Date interviewStartDate = new Date(Long.parseLong(startDate));
		Application a = this.applicationRepository.findById(applicationId).get();
		String fullName = a.getFullName();
		String userEmail = a.getEmail();
		String contact = a.getContact();
		mailService.sendNotification3(userEmail, fullName, interviewStartDate,contact,meetingLink );
	}

	//Updated Today
	@RequestMapping("/mail4")
	public void sendNotification4(@RequestParam String userEmail, @RequestParam String fullName,
			@RequestParam String startDate, @RequestParam String endDate,@RequestParam long id, @RequestParam String contact, @RequestParam String mentor, @RequestParam String project) throws ParseException {

		Long uid=this.userRepository.getUserid(id);
		Date jobStartDate = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
		Date jobEndDate = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
		if(Objects.isNull(uid)) {		
			System.out.println("UID CREATED");
			uid = this.mailService.sendNotification13(userEmail, fullName, jobStartDate, jobEndDate, contact, mentor, project);		
		}

		this.userRepository.savedate(jobStartDate,jobEndDate,uid, mentor, project);
		long rid=this.userRepository.getroleid(uid);
		this.roleRepository.changerole(rid);  
		mailService.sendNotification4(userEmail, fullName, jobStartDate, jobEndDate,contact);
	}

	
	//mail controller for notifying mentor
	@RequestMapping("/mailMentor")
	public void sendMailMentor(@RequestParam ("mentorFullName") String mentorFullName, @RequestParam ("mentorEmail") String mentorEmail)
	{
		Mentor m = this.mentorRepo.findById(mentorEmail).get();
		String fullName = m.getName();
		String email = m.getEmail();
		mailService.sendMailMentor(fullName,email);
		//mailService.sendMailMentor(mentorFullName, mentorEmail);
	}
	
	
	//java edit/make files pdf, doc, excel
	@RequestMapping("/mail5")
	public void sendNotification5(String id, String name, String collegeName, String duration, String startDate,
			String endDate, @RequestParam String contact) throws InvalidFormatException, IOException {

	//	XWPFDocument doc = new XWPFDocument(OPCPackage.open("src/main/resources/static/images/input.docx"));
		
		String filePath="";
		String os=System.getProperty("os.name");
		if(os.startsWith("Window"))
		{
			filePath="C:/intern_uploads/uploads/input.docx";
		}
		if(os.startsWith("Unix"))
		{
			filePath="/root/intern_uploads/upload/input.docx";
		}
		if(os.startsWith("Linux"))
		{
			filePath="/var/www/html/intern_uploads/uploads/input.docx";
		}
		
		
		XWPFDocument doc = new XWPFDocument(OPCPackage.open(filePath));
		
		for (XWPFParagraph p : doc.getParagraphs()) {
			List<XWPFRun> runs = p.getRuns();
			if (runs != null) {
				for (XWPFRun r : runs) {
					String text = r.getText(0);
					//DEAR NAMETAG, THANK YOU
					if (text != null && text.contains("NAMETAG")) {
						text = text.replace("NAMETAG", name);
						r.setText(text, 0);
					}

					if (text != null && text.contains("DURATIONTAG")) {
						text = text.replace("DURATIONTAG", duration);
						r.setText(text, 0);
					}
					if (text != null && text.contains("STARTDATETAG")) {
						text = text.replace("STARTDATETAG", startDate);
						r.setText(text, 0);
					}
					if (text != null && text.contains("ENDDATETAG")) {
						text = text.replace("ENDDATETAG", endDate);
						r.setText(text, 0);
					}
					if (text != null && text.contains("COLLEGETAG")) {
						text = text.replace("COLLEGETAG", collegeName);
						r.setText(text, 0);
					}
				}
			}
		}
		/*		for (XWPFTable tbl : doc.getTables()) {
		   for (XWPFTableRow row : tbl.getRows()) {
		      for (XWPFTableCell cell : row.getTableCells()) {
		         for (XWPFParagraph p : cell.getParagraphs()) {
		            for (XWPFRun r : p.getRuns()) {
		            	String text = r.getText(0);
			            if (text != null && text.contains("NAMETAG")) {
			                text = text.replace("NAMETAG", name);
			                r.setText(text, 0);
			            }

			            if (text != null && text.contains("DURATIONTAG")) {
			                text = text.replace("DURATIONTAG", duration);
			                r.setText(text, 0);
			            }
			            if (text != null && text.contains("STARTDATETAG")) {
			                text = text.replace("STARTDATETAG", startDate);
			                r.setText(text, 0);
			            }
			            if (text != null && text.contains("ENDDATETAG")) {
			                text = text.replace("ENDDATETAG", endDate);
			                r.setText(text, 0);
			            }
			            if (text != null && text.contains("COLLEGETAG")) {
			                text = text.replace("COLLEGETAG", collegeName);
			                r.setText(text, 0);
			            }
		            }
		         }
		      }
		   }
		}
		 */
		//doc.write(new FileOutputStream("src/main/resources/static/images/output.docx"));
		
		if(os.startsWith("Window"))
		{
			try
			{
				doc.write(new FileOutputStream("C:/intern_uploads/uploads/certificate.docx"));
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		if(os.startsWith("Unix"))
		{
			try
			{
				doc.write(new FileOutputStream("/root/intern_uploads/upload/certificate.docx"));
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		if(os.startsWith("Linux"))
		{
			try
			{
				doc.write(new FileOutputStream("/var/www/html/intern_uploads/uploads/certificate.docx"));
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}		

	

	@RequestMapping("/download")
	public ResponseEntity  downloadFile(HttpServletResponse response) throws IOException {
		System.out.println("Inside File Download");
		String filePath="";
		String os=System.getProperty("os.name");
		if(os.startsWith("Window"))
		{
			filePath="C:/intern_uploads/uploads/certificate.docx";
		}
		if(os.startsWith("Unix"))
		{
			filePath="/root/intern_uploads/upload/certificate.docx";
		}
		if(os.startsWith("Linux"))
		{
			filePath="/var/www/html/intern_uploads/uploads/certificate.docx";
		}
		File file = new File(filePath);
		byte[] bytes = Files.readAllBytes(file.toPath());
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "certificate_file.docx" + "\"")
				.body(bytes);		



	}

}
