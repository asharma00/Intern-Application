package com.intern.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import java.util.ArrayList;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.intern.repository.MentorRepo;
import com.intern.repository.UserRepository;
import com.intern.web.dto.UserRegistrationDto;

@Service
public class MailService {
	private JavaMailSender javaMailSender;
	
    @Autowired
    private UserRepository userRepository;
    
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	private MentorRepo mentorRepository;
	
	@Value("${spring.mail.username}")
	private String myMail;

	@Autowired
	public MailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	 
	
	public String generateRandomSpecialCharacters(int length) {
	    RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(48, 122)
	        .build();
	    return pwdGenerator.generate(length);
	}
	
	public void sendAcceptNotification(String userEmail, String fullName,String phone) throws MailException{
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(userEmail);
		mail.setFrom(myMail);
		mail.setSubject("CRIS application Accepted");
		mail.setText("Dear " + fullName + "\nThank you for taking the time to apply for an internship at CRIS."
				+ "Your Application at CRIS is accepted.");
		javaMailSender.send(mail);
		
		
		
		
		
		try
		{

		 // Send data
			String content="Dear " + fullName + "  Thank you for taking the time to apply for an internship at CRIS. Your application at CRIS is accepted by Administration.";
		content=content.replaceAll(" ", "%20");
		
		
		// HttpURLConnection conn = (HttpURLConnection) new URL("http://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=91"+phone+"&msg="+content+"&msg_type=TEXT&userid=2000193787&auth_scheme=plain&password=TtyzCsbb&v=1.1&format=text").openConnection();
	// conn.setDoOutput(true);
		// conn.setRequestMethod("GET");
		 
		// final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		// final StringBuffer stringBuffer = new StringBuffer();
		// String line;
		// while ((line = rd.readLine()) != null) {
		//     stringBuffer.append(line);
		// }
		// rd.close();
		// System.out.println("message sent successfully");
		
		} catch (Exception e) 
		{
		System.out.println(e.getMessage());
		}
	
		
		
		
		
		
		
		
	}

	
	//rejected mail
	public void sendNotification1(String userEmail, String fullName,String phone) throws MailException
	{
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(userEmail);
		mail.setFrom(myMail);
		mail.setSubject("CRIS application rejected");
		mail.setText("Dear " + fullName + "\nThank you for taking the time to apply for an internship at CRIS."
				+ "We really appreciate the effort you put into this. We recieved and have reviewed all of the applications\n"
				+ "After reviewing them thoroughly, we felt that other applicants were better suited for our company\n"
				+ "At this time, we have decided to move forward with your application.\n\nBest of luck with your Career.\n\nSincerely, CRIS.");
		javaMailSender.send(mail);
		
		
		
		
		
		try
		{

		 // Send data
			String content="Dear " + fullName + "  Thank you for taking the time to apply for an internship at CRIS. We really appreciate the effort you put into this. We recieved and have reviewed all of the applications. After reviewing them thoroughly, we felt that other applicants were better suited for our company. At this time, we have decided to move forward with your application. Best of luck with your Career. Sincerely, CRIS.";
		content=content.replaceAll(" ", "%20");
		
		// HttpURLConnection conn = (HttpURLConnection) new URL("http://122.176.77.205:8081/jinvani/SendMessegeServlet?uname=RCRBPY&passwd=rcrbpy@123&text="+content+"&msisdn="+phone+"&mode=Txt").openConnection();
		/*
		 * HttpURLConnection conn = (HttpURLConnection) new URL(
		 * "http://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=91"
		 * +phone+"&msg="+content+
		 * "&msg_type=TEXT&userid=2000193787&auth_scheme=plain&password=TtyzCsbb&v=1.1&format=text"
		 * ).openConnection(); conn.setDoOutput(true); conn.setRequestMethod("GET");
		 * 
		 * final BufferedReader rd = new BufferedReader(new
		 * InputStreamReader(conn.getInputStream())); final StringBuffer stringBuffer =
		 * new StringBuffer(); String line; while ((line = rd.readLine()) != null) {
		 * stringBuffer.append(line); } rd.close();
		 * System.out.println("message sent successfully");
		 */
		} catch (Exception e) 
		{
		System.out.println(e.getMessage());
		}
	}
	
	
	//exam required mail
	public void sentApplyNotification(String email, String subject, String message)
	{
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom(myMail);
		mail.setSubject(subject);
		mail.setText(message);
		javaMailSender.send(mail);
	}
	
	
	//mail required for mentor to be notified
	public Long sendMailMentor(String mentorFullName, String mentorEmail)
	{
		String password = generateRandomSpecialCharacters(20);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(mentorEmail);
		mail.setFrom(myMail);
		mail.setSubject("Project Mentorship");
		mail.setText("Dear Mr/Miss " + mentorFullName + ",\n"  
				+ "You have been assigned to be the mentor for the project" + ".\n" 
				+ "Login credentials for the same are given below. Link for internapp login is http://prodmcf.indianrailways.gov.in/internapp/login.\n" 
				+ "Username : " + mentorEmail + "\n"
				+ "Password : " + password);
		javaMailSender.send(mail);
		try
		{ } 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
		this.userServiceImpl.save2(new UserRegistrationDto(mentorFullName, mentorEmail, password));
		return userRepository.findByEmail(mentorEmail).getId();
	}
	
	
	
	
	public Long sendNotification2(String userEmail, String fullName, Date startDate, String phone, Integer time, String meetingLink) {
		String password = generateRandomSpecialCharacters(20);
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(userEmail);
		mail.setFrom(myMail);
		mail.setSubject("CRIS Exam Details");
		mail.setText("Dear " + fullName + ",\n "
				
				+ "As communicated earlier, we have decided to test you by conducting an examination\n"
				+ "It is going to start on " + startDate +" and will be of duration " + time + " minutes. \nYour username is: " + userEmail + "\nYour password is: " + password + "\n You can use these credentials to access the test, if online, at \nhttp://prodmcf.indianrailways.gov.in/internapp/login."
				+ "You can also connect to meeting link: "+meetingLink+"  during your exam period. \n\nSincerely, CRIS");
		javaMailSender.send(mail);
		
		
		
		try
		{

		
		//	String content="Dear " + fullName + "  As communicated earlier, we have decided to test you by conducting an examination. It is going to start on " + startDate +" Your username is: "  + userEmail + " Your password is " + password + " Sincerely, CRIS.";
		//content=content.replaceAll(" ", "%20");
		
		// HttpURLConnection conn = (HttpURLConnection) new URL("http://122.176.77.205:8081/jinvani/SendMessegeServlet?uname=RCRBPY&passwd=rcrbpy@123&text="+content+"&msisdn="+phone+"&mode=Txt").openConnection();
		// HttpURLConnection conn = (HttpURLConnection) new URL("http://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=91"+phone+"&msg="+content+"&msg_type=TEXT&userid=2000193787&auth_scheme=plain&password=TtyzCsbb&v=1.1&format=text").openConnection();
		// conn.setDoOutput(true);
		// conn.setRequestMethod("GET");
		 
		// final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		// final StringBuffer stringBuffer = new StringBuffer();
		// String line;
		// while ((line = rd.readLine()) != null) {
	//	     stringBuffer.append(line);
	//	 }
	//	 rd.close();
		// System.out.println("message sent successfully");
		 //return stringBuffer.toString();
		} catch (Exception e) 
		{
		System.out.println(e.getMessage());
		}
		
		this.userServiceImpl.save1(new UserRegistrationDto(fullName, userEmail, password));
		return userRepository.findByEmail(userEmail).getId();
	}
	
	
	//interview required mail
	public void sendNotification3(String userEmail, String fullName, Date startDate,String phone, String meetingLink) {
		SimpleMailMessage mail = new SimpleMailMessage();
		
		mail.setTo(userEmail);
		mail.setFrom(myMail);
		mail.setSubject("CRIS Interview Details");
		mail.setText("Dear " + fullName + "\nThank you for taking the time to apply for an internship at CRIS."
				+ "We really appreciate the effort you put into this. We recieved and have reviewed all of the applications\n"
				+ "After reviewing them thoroughly, we decided to test you by conducting an Interview\n"
				+ "It is going to start on " + startDate +  ".\nYou should join interview here "+meetingLink+".\n\nSincerely, CRIS");
		javaMailSender.send(mail);
		
		
		
		try
		{

		
		/*	String content="Dear " + fullName + "  Thank you for taking the time to apply for an internship at CRIS. We really appreciate the effort you put into this. We recieved and have reviewed all of the applications. After reviewing them thoroughly,we decided to test you by conducting an Interview. It is going to start on "+startDate +" Sincerely, CRIS.";
		content=content.replaceAll(" ", "%20");
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.77.40.15", 3128));
		// HttpURLConnection conn = (HttpURLConnection) new URL("http://122.176.77.205:8081/jinvani/SendMessegeServlet?uname=RCRBPY&passwd=rcrbpy@123&text="+content+"&msisdn="+phone+"&mode=Txt").openConnection();
		 HttpURLConnection conn = (HttpURLConnection) new URL("http://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=91"+phone+"&msg="+content+"&msg_type=TEXT&userid=2000193787&auth_scheme=plain&password=TtyzCsbb&v=1.1&format=text").openConnection(proxy);
		 conn.setDoOutput(true);
		 conn.setRequestMethod("GET");
		 
		 final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		 final StringBuffer stringBuffer = new StringBuffer();
		 String line;
		 while ((line = rd.readLine()) != null) {
		     stringBuffer.append(line);
		 }
		 rd.close();
		 System.out.println("message sent successfully");
		*/
		} catch (Exception e) 
		{
		System.out.println(e.getMessage());
		}
		
	}
	
	
	
	//join for training between these days
	public void sendNotification4(String userEmail, String fullName, Date startDate, Date endDate,String phone) {
		//String password = generateRandomSpecialCharacters(20);
		//this.userServiceImpl.save(new UserRegistrationDto(fullName, userEmail,  password));
		
		//this.userRepository.changeroletouser(userEmail);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(userEmail);
		mail.setFrom(myMail);
		mail.setSubject("CRIS Training accepted");
		mail.setText("Dear " + fullName + "\nThank you for taking the time to apply for an internship at CRIS."
				+ "We really appreciate the effort you put into this. We recieved and have reviewed all of the applications\n"
				+ "After reviewing them thoroughly, we decided to train you in our Company.\n"
				+ "It is going to be between " + startDate + " and " + endDate + ".\nYour User ID is: " + 
				userEmail + "\nYour password is: the same as your exam password \n\nSincerely, CRIS");
		javaMailSender.send(mail);
		
		
		
		try
		{

		
		//	String content="Dear " + fullName + "  Thank you for taking the time to apply for an internship at CRIS. We really appreciate the effort you put into this.  We recieved and have reviewed all of the applications. After reviewing them thoroughly, we decided to train you in our Company. It is going to be between " + startDate + " and " + endDate + "Your User ID is: " + 	userEmail + " Your password is: the same as your exam password. Sincerely, CRIS";
		//content=content.replaceAll(" ", "%20");
		//Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.77.40.15", 3128));
		
		// HttpURLConnection conn = (HttpURLConnection) new URL("http://122.176.77.205:8081/jinvani/SendMessegeServlet?uname=RCRBPY&passwd=rcrbpy@123&text="+content+"&msisdn="+phone+"&mode=Txt").openConnection();
		// HttpURLConnection conn = (HttpURLConnection) new URL("http://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=91"+phone+"&msg="+content+"&msg_type=TEXT&userid=2000193787&auth_scheme=plain&password=TtyzCsbb&v=1.1&format=text").openConnection(proxy);
		// conn.setDoOutput(true);
	//	 conn.setRequestMethod("GET");
		 
		// final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		// final StringBuffer stringBuffer = new StringBuffer();
		// String line;
		// while ((line = rd.readLine()) != null) {
		//     stringBuffer.append(line);
		// }
		// rd.close();
	//	 System.out.println("message sent successfully");
		
		} catch (Exception e) 
		{
	//	System.out.println(e.getMessage());
		}
		
	}
	
	//certificate after the user has been released
	public void sendNotification5(String userEmail, String fullName,String phone) throws MessagingException {
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(userEmail);
		helper.setFrom(myMail);
		String path = "src/main/resources/static/images/output.docx";
		helper.setText("Dear " + fullName + "\n Thank you for all your wonderful contributions during our recent work together.\n Here is"
				+ "a certificate stating the work you have done for our Organization.\n\nThank you.\nTEAM CRIS.");
		FileSystemResource file  = new FileSystemResource(new File(path));
		helper.addAttachment("testfile", file);
		helper.setSubject("CRIS Certificate");
		javaMailSender.send(message);
//		javax.mail.internet.MimeMessage message = mailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message,true);
//		mail.setTo(userEmail);
//		mail.setFrom(myMail);
//		mail.setSubject("CRIS Certificate Name");
//		mail.setText("Dear " + fullName + "\n Thank you for all your wonderful contributions during our recent work together.\n Here is"
//				+ "a certificate stating the work you have done for our Organization.\n\nThank you.\nTEAM CRIS.");
//		
//		javaMailSender.send(mail);
	}
	
	public void sentSMSNotification(String phoneNo, String Message) throws MailException
	{
		try
		{
			String content=Message.replaceAll(" ", "%20");
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.77.40.15", 3128));
			HttpURLConnection conn = (HttpURLConnection) new URL("http://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=91"+phoneNo+"&msg="+content+"&msg_type=TEXT&userid=2000193787&auth_scheme=plain&password=TtyzCsbb&v=1.1&format=text").openConnection(proxy);
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			 final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			 final StringBuffer stringBuffer = new StringBuffer();
			 String line;
			 while ((line = rd.readLine()) != null) {
			     stringBuffer.append(line);
			 }
			 rd.close();
			 System.out.println("message sent successfully");
			 //return stringBuffer.toString();
			} 
		catch (Exception e) 
			{
			System.out.println(e.getMessage());
			}
		}
	
	public void sendNotification6(String email,String from,String to,String phone) throws MailException{
		/*
		 * SimpleMailMessage mail = new SimpleMailMessage(); mail.setTo(email);
		 * mail.setFrom(myMail); mail.setSubject("Leave accepted");
		 * mail.setText("This is to inform you that the leave you applied for from  " +
		 * from +"  to  "+ to + "  has been accepted."); javaMailSender.send(mail);
		 */
		
		//new code
		String receipient=email;
		String subject="Leave accepted";
		String content="This is to inform you that the leave you applied for from  " +from +"  to  "+ to + "  has been accepted.";
		
		SentMail sm=new SentMail();
		sm.sentMail(receipient, subject, content);
		try
		{

		 // Send data
			
		content=content.replaceAll(" ", "%20");
	//Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.77.40.15", 3128));
		// HttpURLConnection conn = (HttpURLConnection) new URL("http://122.176.77.205:8081/jinvani/SendMessegeServlet?uname=RCRBPY&passwd=rcrbpy@123&text="+content+"&msisdn="+phone+"&mode=Txt").openConnection();
		 
		// Below code is uncomment when sms service is working properly   
		
	/*   HttpURLConnection conn = (HttpURLConnection) new URL("http://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=91"+phone+"&msg="+content+"&msg_type=TEXT&userid=2000193787&auth_scheme=plain&password=TtyzCsbb&v=1.1&format=text").openConnection(proxy);
		
		 conn.setDoOutput(true);
		 conn.setRequestMethod("GET");
		 
		 final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		 final StringBuffer stringBuffer = new StringBuffer();
		 String line;
		 while ((line = rd.readLine()) != null) {
		     stringBuffer.append(line);
		 }
		 rd.close();
		 System.out.println("message sent successfully");      */
	
		} 
		
		catch (Exception e) 
		{
		System.out.println(e.getMessage());  
		}
	}
	
	public void sendWarningNotification(Long id)
	{
		ArrayList<Object[]>  email_phone=userRepository.getEmailIdByUid(id);
		for (Object o[]: email_phone)
		{
			String email=o[0].toString();
			String phone=o[1].toString();
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(email);
			mail.setCc("mucrisitpi@gmail.com");
			mail.setSubject("Apply Leave beyond Permissible Limit");
			mail.setText("This is to inform you that you have already availed 12 days leave. If you taken more than permissible limit leave, your training will be extended");
			javaMailSender.send(mail);
			
			String content="This is to inform you that you have already availed 12 days leave. If you taken more than permissible limit leave, your training will be extended";
			try
			{
				content=content.replaceAll(" ", "%20");
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.77.40.15", 3128));
				
				// HttpURLConnection conn = (HttpURLConnection) new URL("http://122.176.77.205:8081/jinvani/SendMessegeServlet?uname=RCRBPY&passwd=rcrbpy@123&text="+content+"&msisdn="+phone+"&mode=Txt").openConnection();
				 HttpURLConnection conn = (HttpURLConnection) new URL("http://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=91"+phone+"&msg="+content+"&msg_type=TEXT&userid=2000193787&auth_scheme=plain&password=TtyzCsbb&v=1.1&format=text").openConnection(proxy);
				 conn.setDoOutput(true);
				 conn.setRequestMethod("GET");
				 final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				 final StringBuffer stringBuffer = new StringBuffer();
				 String line;
				 while ((line = rd.readLine()) != null) {
				     stringBuffer.append(line);
				 }
				 rd.close();
				 System.out.println("message sent successfully");
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				System.out.println(ex.getMessage());
			}
		}
	}
	public void sendWarningNotification2(Long id)
	{
		ArrayList<Object[]>  email_phone=userRepository.getEmailIdByUid(id);
		for (Object o[]: email_phone)
		{
			System.out.println("Hi System before email");
			String email=String.valueOf(o[0]);
			
			System.out.println("Hi System after email "+email);
			String phone=o[1].toString();
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(email);
			mail.setCc("mucrisitpi@gmail.com");
			mail.setSubject("Apply Leave beyond Permissible Limit");
			mail.setText("This is to inform you that you have applied leave beyond your permissible limit. Your training period will extended beyond your permissible limit");
			javaMailSender.send(mail);
			
			String content="This is to inform you that you have applied leave beyond your permissible limit. Your training period will extended beyond your permissible limit";
			try
			{
				content=content.replaceAll(" ", "%20");
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.77.40.15", 3128));
				
				// HttpURLConnection conn = (HttpURLConnection) new URL("http://122.176.77.205:8081/jinvani/SendMessegeServlet?uname=RCRBPY&passwd=rcrbpy@123&text="+content+"&msisdn="+phone+"&mode=Txt").openConnection();
				 HttpURLConnection conn = (HttpURLConnection) new URL("http://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=91"+phone+"&msg="+content+"&msg_type=TEXT&userid=2000193787&auth_scheme=plain&password=TtyzCsbb&v=1.1&format=text").openConnection(proxy);
				 conn.setDoOutput(true);
				 conn.setRequestMethod("GET");
				 final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				 final StringBuffer stringBuffer = new StringBuffer();
				 String line;
				 while ((line = rd.readLine()) != null) {
				     stringBuffer.append(line);
				 }
				 rd.close();
				 System.out.println("message sent successfully");
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				System.out.println(ex.getMessage());
			}
		}
	}
	
	public void sendNotification7(String email,String from,String to,String phone,String reason) throws MailException{
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom(myMail);
		mail.setSubject("Leave rejected");
		mail.setText("This is to inform you that the leave you applied for from  "
				+ from +"  to  "+ to + "  has been rejected beacause of the reason : "+reason);
		javaMailSender.send(mail);
		
		
		try
		{

		 // Send data
			String content="This is to inform you that the leave you applied for from  "+ from +"  to  "+ to + " has been rejected because of the reason: "+reason;
		content=content.replaceAll(" ", "%20");
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.77.40.15", 3128));
		
		// HttpURLConnection conn = (HttpURLConnection) new URL("http://122.176.77.205:8081/jinvani/SendMessegeServlet?uname=RCRBPY&passwd=rcrbpy@123&text="+content+"&msisdn="+phone+"&mode=Txt").openConnection();
		 HttpURLConnection conn = (HttpURLConnection) new URL("http://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=91"+phone+"&msg="+content+"&msg_type=TEXT&userid=2000193787&auth_scheme=plain&password=TtyzCsbb&v=1.1&format=text").openConnection(proxy);
		 conn.setDoOutput(true);
		 conn.setRequestMethod("GET");
		 
		 final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		 final StringBuffer stringBuffer = new StringBuffer();
		 String line;
		 while ((line = rd.readLine()) != null) {
		     stringBuffer.append(line);
		 }
		 rd.close();
		 System.out.println("message sent successfully");
		 //return stringBuffer.toString();
		} catch (Exception e) 
		{
		System.out.println(e.getMessage());
		}
	}
	
	
	
	public void sendNotification8(String userEmail, String fullName, Date eDate, String phone, Integer time, String meetingLink) {
		
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(userEmail);
		mail.setFrom(myMail);
		mail.setSubject("CRIS Exam date change");
		mail.setText("Dear " + fullName + ",\n This is to inform you that the exam schedule has been changed \n"
				
				+ "It is now going to start on " + eDate +" and will be for the duration of " + time + " minutes.\nYour username and password are the same as before. You can use these credentials to access the test, if online, at \nhttp://prodmcf.indianrailways.gov.in/internapp/login."
						+ "You can also conntect to "+meetingLink+"  meeting link during your exam period.\n\nSincerely, CRIS");
		javaMailSender.send(mail);
		
		
		
		try
		{

		
		//	String content="Dear " + fullName + ", This is to inform you that the exam schedule has been changed  . It is now going to start on " + eDate +" Your username and password are the same as before. Sincerely, CRIS.";
		//content=content.replaceAll(" ", "%20");
	//	Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.77.40.15", 3128));
		
		// HttpURLConnection conn = (HttpURLConnection) new URL("http://122.176.77.205:8081/jinvani/SendMessegeServlet?uname=RCRBPY&passwd=rcrbpy@123&text="+content+"&msisdn="+phone+"&mode=Txt").openConnection();
	//	 HttpURLConnection conn = (HttpURLConnection) new URL("http://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=91"+phone+"&msg="+content+"&msg_type=TEXT&userid=2000193787&auth_scheme=plain&password=TtyzCsbb&v=1.1&format=text").openConnection(proxy);
	//	 conn.setDoOutput(true);
	//	 conn.setRequestMethod("GET");
		 
	//	 final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	//	 final StringBuffer stringBuffer = new StringBuffer();
	//	 String line;
	//	 while ((line = rd.readLine()) != null) {
	//	     stringBuffer.append(line);
	//	 }
	//	 rd.close();
	//	 System.out.println("message sent successfully");
		 //return stringBuffer.toString();
		} catch (Exception e) 
		{
		System.out.println(e.getMessage());
		}
		
		//this.userServiceImpl.save1(new UserRegistrationDto(fullName, userEmail,  password));
	}
	
	
	
public void sendNotification9(String userEmail, String fullName, String phone) {
		
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(userEmail);
		mail.setFrom(myMail);
		mail.setSubject("Relieved from internship");
		mail.setText("Dear " + fullName + ", This is to inform you that you have completed your internship sucessfully and can come and collect your certificate from us \n"
				
				+ " \n\nSincerely, CRIS");
		javaMailSender.send(mail);
		
		
		
		try
		{

		 // Send data
	/*		String content="Dear " + fullName + ", This is to inform you that you have completed your internship sucessfully and can come and collect your certificate from us. Sincerely, CRIS.";
		content=content.replaceAll(" ", "%20");
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.77.40.15", 3128));
		
		// HttpURLConnection conn = (HttpURLConnection) new URL("http://122.176.77.205:8081/jinvani/SendMessegeServlet?uname=RCRBPY&passwd=rcrbpy@123&text="+content+"&msisdn="+phone+"&mode=Txt").openConnection();
		 HttpURLConnection conn = (HttpURLConnection) new URL("http://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=91"+phone+"&msg="+content+"&msg_type=TEXT&userid=2000193787&auth_scheme=plain&password=TtyzCsbb&v=1.1&format=text").openConnection(proxy);
		 conn.setDoOutput(true);
		 conn.setRequestMethod("GET");
		 
		 final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		 final StringBuffer stringBuffer = new StringBuffer();
		 String line;
		 while ((line = rd.readLine()) != null) {
		     stringBuffer.append(line);
		 }
		 rd.close();
		 System.out.println("message sent successfully");
		 */
		 //return stringBuffer.toString();
		} catch (Exception e) 
		{
		System.out.println(e.getMessage());
		}
		
		//this.userServiceImpl.save1(new UserRegistrationDto(fullName, userEmail,  password));
	}
	
	
	



public void sendNotification11(String email,String phone) {
	String password = generateRandomSpecialCharacters(20);
	//System.out.println(password);
	//passwordEncoder.encode(password);
	//System.out.println(passwordEncoder.encode(password));

	SimpleMailMessage mail = new SimpleMailMessage();
	mail.setTo(email);
	mail.setFrom(myMail);
	mail.setSubject("Password successfully reset");
	mail.setText("\n Your password has been successfully reset."
			+ "Your new password is " + password +"\n\nSincerely, CRIS");
	//javaMailSender.send(mail);
	
	
	
	try
	{

	 // Send data
		//String contact="7522090257"
		String content="Your password has been reset successfully .Your new password is:   "+password+"  Sincerely, CRIS";
	content=content.replaceAll(" ", "%20");
	//Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.16.1.61", 8080));
	Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.77.40.15", 3128));
	 HttpURLConnection conn = (HttpURLConnection) new URL("http://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=91"+phone+"&msg="+content+"&msg_type=TEXT&userid=2000193787&auth_scheme=plain&password=TtyzCsbb&v=1.1&format=text").openConnection(proxy);
	//HttpURLConnection conn = (HttpURLConnection) new URL("http://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=91"+phone+"&msg="+content+"&msg_type=TEXT&userid=2000193787&auth_scheme=plain&password=TtyzCsbb&v=1.1&format=text").openConnection();
	 conn.setDoOutput(true);
	 conn.setRequestMethod("GET");
	 
	 final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	 final StringBuffer stringBuffer = new StringBuffer();
	 String line;
	 while ((line = rd.readLine()) != null) {
	     stringBuffer.append(line);
	 }
	 rd.close();
	 System.out.println("message sent successfully");
	 //return stringBuffer.toString();
	} catch (Exception e) 
	{
	System.out.println(e.getMessage());
	}
	String s=passwordEncoder.encode(password);
	
	this.userRepository.savenewpass(email,s);
	//this.userServiceImpl.save1(new UserRegistrationDto(fullName, userEmail,  password));
}




public void sendNotification12(String userEmail, String fullName,String phone) throws MailException{
	SimpleMailMessage mail = new SimpleMailMessage();
	mail.setTo(userEmail);
	mail.setFrom(myMail);
	mail.setSubject("CRIS application accepted");
	mail.setText("Dear " + fullName + "\nThank you for taking the time to apply for an internship at CRIS."
			+ "We really appreciate the effort you put into this. We recieved and have reviewed all of the applications\n"
			+ "After reviewing them thoroughly, we have decided to evaluate you by taking an exam.\n"
			+ "You will be informed about the date and time of the exam shortly .\n\nSincerely, CRIS.");
	javaMailSender.send(mail);
	
	
	
	
	
	try
	{

	 // Send data
		String content="Dear " + fullName + "  Thank you for taking the time to apply for an internship at CRIS. We really appreciate the effort you put into this. We recieved and have reviewed all of the applications. After reviewing them thoroughly, we have decided to evaluate you by taking an exam. You will be informed about the date and time of the exam shortly . Sincerely, CRIS.";
	content=content.replaceAll(" ", "%20");
	
	// HttpURLConnection conn = (HttpURLConnection) new URL("http://122.176.77.205:8081/jinvani/SendMessegeServlet?uname=RCRBPY&passwd=rcrbpy@123&text="+content+"&msisdn="+phone+"&mode=Txt").openConnection();
	 HttpURLConnection conn = (HttpURLConnection) new URL("http://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=91"+phone+"&msg="+content+"&msg_type=TEXT&userid=2000193787&auth_scheme=plain&password=TtyzCsbb&v=1.1&format=text").openConnection();
	 conn.setDoOutput(true);
	 conn.setRequestMethod("GET");
	 
	 final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	 final StringBuffer stringBuffer = new StringBuffer();
	 String line;
	 while ((line = rd.readLine()) != null) {
	     stringBuffer.append(line);
	 }
	 rd.close();
	 System.out.println("message sent successfully");
	 //return stringBuffer.toString();
	} catch (Exception e) 
	{
	System.out.println(e.getMessage());
	}
	
	
	
	
	
	

}

public void sendNoExamMail(String email, String contact, String fullName) {
	// TODO Auto-generated method stub
	SimpleMailMessage mail = new SimpleMailMessage();
	mail.setTo(email);
	mail.setFrom(myMail);
	mail.setSubject("CRIS Exam Cancellation");
	mail.setText("Dear " + fullName + ",\n This is to inform you that the exam scheduled for your selection has been cancelled \n"
			
			+ "The selection procedure is  now going to proceed without any examination. Futher details will be made available as per the proceedings.\n\nSincerely, CRIS.");
	javaMailSender.send(mail);
	
	try
	{
	String content="Dear " + fullName + ", This is to inform you that the exam scheduled for your selection has been cancelled. The selection procedure is  now going to proceed without any examination. Futher details will be made available as per the proceedings. Sincerely, CRIS.";
	content=content.replaceAll(" ", "%20");
	Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.77.40.15", 3128));
	
	 HttpURLConnection conn = (HttpURLConnection) new URL("http://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=91"+contact+"&msg="+content+"&msg_type=TEXT&userid=2000193787&auth_scheme=plain&password=TtyzCsbb&v=1.1&format=text").openConnection(proxy);
	 conn.setDoOutput(true);
	 conn.setRequestMethod("GET");
	 
	 final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	 final StringBuffer stringBuffer = new StringBuffer();
	 String line;
	 while ((line = rd.readLine()) != null) {
	     stringBuffer.append(line);
	 }
	 rd.close();
	 System.out.println("message sent successfully");
	} catch (Exception e) 
	{
	System.out.println(e.getMessage());
	}
	
	
}


//Integrate
public void mailForBulkCancel(String[] mailArr, ArrayList<String> contacts, ArrayList<String> names) {
	// TODO Auto-generated method stub
	SimpleMailMessage mail = new SimpleMailMessage();
	
	mail.setTo(mailArr);
	//mail.setBcc(mailArr);
	mail.setFrom(myMail);
	mail.setSubject("CRIS Exam Cancellation");
	mail.setText("Dear candidate, \n This is to inform you that the exam scheduled for your selection has been cancelled \n"
			
			+ "The selection procedure is  now going to proceed without any examination. Futher details will be made available as per the proceedings.\n\nSincerely, CRIS.");
	javaMailSender.send(mail);
	
	try
	{
		int i = 0;
		/*
		 * for(String contact:contacts){ String fullName = names.get(i++); String
		 * content="Dear " + fullName +
		 * ", This is to inform you that the exam scheduled for your selection has been cancelled. The selection procedure is  now going to proceed without any examination. Futher details will be made available as per the proceedings. Sincerely, CRIS."
		 * ; content=content.replaceAll(" ", "%20"); Proxy proxy = new
		 * Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.77.40.15", 3128));
		 * 
		 * HttpURLConnection conn = (HttpURLConnection) new URL(
		 * "http://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=91"
		 * +contact+"&msg="+content+
		 * "&msg_type=TEXT&userid=2000193787&auth_scheme=plain&password=TtyzCsbb&v=1.1&format=text"
		 * ).openConnection(proxy); conn.setDoOutput(true);
		 * conn.setRequestMethod("GET");
		 * 
		 * final BufferedReader rd = new BufferedReader(new
		 * InputStreamReader(conn.getInputStream())); final StringBuffer stringBuffer =
		 * new StringBuffer(); String line; while ((line = rd.readLine()) != null) {
		 * stringBuffer.append(line); } rd.close();
		 * System.out.println("message sent successfully"); }
		 */
	}
	
	catch (Exception e) 
	{
	System.out.println(e.getMessage());
	}
	
	
}


public void mailForBulkChange(String[] mailArr, ArrayList<String> contacts, ArrayList<String> names, Date eDate) {
	// TODO Auto-generated method stub
	
	SimpleMailMessage mail = new SimpleMailMessage();
	
	mail.setTo(mailArr);
	//mail.setBcc(mailArr);
	mail.setFrom(myMail);
	mail.setSubject("CRIS Exam date change");
	mail.setText("Dear candidate,\n This is to inform you that the exam schedule has been changed \n"			
			+ "It is now going to start on " + eDate +"\nYour username and password are the same as before. You can use these credentials to access the test, if online, at \nhttp://prodmcf.indianrailways.gov.in/internapp/login\n\nSincerely, CRIS");
	javaMailSender.send(mail);
	try
	{
		int i = 0;
		for(String contact:contacts){
		String fullName = names.get(i++);
	    String content="Dear " + fullName + ", This is to inform you that the exam schedule has been changed  . It is now going to start on " + eDate +" Your username and password are the same as before. Sincerely, CRIS.";
		content=content.replaceAll(" ", "%20");
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.77.40.15", 3128));
		
		// HttpURLConnection conn = (HttpURLConnection) new URL("http://122.176.77.205:8081/jinvani/SendMessegeServlet?uname=RCRBPY&passwd=rcrbpy@123&text="+content+"&msisdn="+phone+"&mode=Txt").openConnection();
		 HttpURLConnection conn = (HttpURLConnection) new URL("http://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=91"+contact+"&msg="+content+"&msg_type=TEXT&userid=2000193787&auth_scheme=plain&password=TtyzCsbb&v=1.1&format=text").openConnection(proxy);
		 conn.setDoOutput(true);
		 conn.setRequestMethod("GET");
		 
		 final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		 final StringBuffer stringBuffer = new StringBuffer();
		 String line;
		 while ((line = rd.readLine()) != null) {
		     stringBuffer.append(line);
		 }
		 rd.close();
		 System.out.println("message sent successfully");
		 //return stringBuffer.toString();
		}
	}catch (Exception e) 
		{
		System.out.println(e.getMessage());
		}	
}


public void sendNotificationNew8(String userEmail, String fullName, Date eDate, String phone) {
	// TODO Auto-generated method stub
	SimpleMailMessage mail = new SimpleMailMessage();
	mail.setTo(userEmail);
	mail.setFrom(myMail);
	mail.setSubject("CRIS Exam date change");
	mail.setText("Dear " + fullName + ", This is to inform you that the exam schedule has been changed \n"
			
			+ "It is now going to start on " + eDate + "\nYour username and password are the same as before. You can use these credentials to access the test, if online, at \nhttp://prodmcf.indianrailways.gov.in/internapp/login\n\nSincerely, CRIS");
	javaMailSender.send(mail);
	
	
	
	try
	{

	 // Send data
		String content="Dear " + fullName + ", This is to inform you that the exam schedule has been changed  . It is now going to start on " + eDate +" Your username and password are the same as before. Sincerely, CRIS.";
	content=content.replaceAll(" ", "%20");
	Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.77.40.15", 3128));
	
	// HttpURLConnection conn = (HttpURLConnection) new URL("http://122.176.77.205:8081/jinvani/SendMessegeServlet?uname=RCRBPY&passwd=rcrbpy@123&text="+content+"&msisdn="+phone+"&mode=Txt").openConnection();
	 HttpURLConnection conn = (HttpURLConnection) new URL("http://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=91"+phone+"&msg="+content+"&msg_type=TEXT&userid=2000193787&auth_scheme=plain&password=TtyzCsbb&v=1.1&format=text").openConnection(proxy);
	 conn.setDoOutput(true);
	 conn.setRequestMethod("GET");
	 
	 final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	 final StringBuffer stringBuffer = new StringBuffer();
	 String line;
	 while ((line = rd.readLine()) != null) {
	     stringBuffer.append(line);
	 }
	 rd.close();
	 System.out.println("message sent successfully");
	 //return stringBuffer.toString();
	} catch (Exception e) 
	{
	System.out.println(e.getMessage());
	}
	
	//this.userServiceImpl.save1(new UserRegistrationDto(fullName, userEmail,  password));

	
}

//Updated Today
public Long sendNotification13(String userEmail, String fullName, Date jobStartDate, Date jobEndDate, String contact, String mentor, String project) {
	// TODO Auto-generated method stub
	
	System.out.println("CREATING NEW USER AFTER INTERVIEW");
	String password = generateRandomSpecialCharacters(20);
	
	String mentorName = this.mentorRepository.findById(mentor).get().getName();
	SimpleMailMessage mail = new SimpleMailMessage();
	mail.setTo(userEmail);
	mail.setFrom(myMail);
	mail.setSubject("CRIS Training accepted");
	mail.setText("Dear " + fullName + "\nThank you for taking the time to apply for an internship at CRIS."
			+ "We really appreciate the effort you put into this. We recieved and have reviewed all of the applications\n"
			+ "After reviewing them thoroughly, we decided to train you in our Company.\n"
			+".\nYou have cuurently been assigned to work under " + mentorName + "on the project" + project
			+ "It is going to be between " + jobStartDate + " and " + jobEndDate + ".\nYour User ID is: " + 
			userEmail + "\nYour password is: " + password + " \n\nSincerely, CRIS");
	javaMailSender.send(mail);
	
	try
	{

	 // Send data
		//String content="Dear " + fullName + "  Thank you for taking the time to apply for an internship at CRIS. We really appreciate the effort you put into this.  We recieved and have reviewed all of the applications. After reviewing them thoroughly, we decided to train you in our Company. It is going to be between " + jobStartDate + " and " + jobEndDate + "Your User ID is: " + 	userEmail + " Your password is: " + password + " Sincerely, CRIS";
	//content=content.replaceAll(" ", "%20");
	//Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.77.40.15", 3128));
	
	// HttpURLConnection conn = (HttpURLConnection) new URL("http://122.176.77.205:8081/jinvani/SendMessegeServlet?uname=RCRBPY&passwd=rcrbpy@123&text="+content+"&msisdn="+phone+"&mode=Txt").openConnection();
	// HttpURLConnection conn = (HttpURLConnection) new URL("http://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=91"+contact+"&msg="+content+"&msg_type=TEXT&userid=2000193787&auth_scheme=plain&password=TtyzCsbb&v=1.1&format=text").openConnection(proxy);
	// conn.setDoOutput(true);
	// conn.setRequestMethod("GET");
	 
	// final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	// final StringBuffer stringBuffer = new StringBuffer();
	// String line;
	// while ((line = rd.readLine()) != null) {
	//     stringBuffer.append(line);
	// }
	// rd.close();
	// System.out.println("message sent successfully");
	 //return stringBuffer.toString();
	} catch (Exception e) 
	{
	System.out.println(e.getMessage());
	}
	
	this.userServiceImpl.save1(new UserRegistrationDto(fullName, userEmail,  password));
	return userRepository.findByEmail(userEmail).getId();
	
}


public void sendNotificationJoined(String userEmail,String password, String fullName, Date jobStartDate, Date jobEndDate, String contact, String mentor, String project) {
	
	
	String mentorName = this.mentorRepository.findById(mentor).get().getName();
	SimpleMailMessage mail = new SimpleMailMessage();
	mail.setTo(userEmail);
	mail.setFrom(myMail);
	mail.setSubject("CRIS Training Joined");
	mail.setText("Dear " + fullName + "\nThank you for taking the time to apply for an internship at CRIS."
			+ "We really appreciate the effort you put into this. We recieved and have reviewed all of the applications\n"
			+ "After reviewing them thoroughly, we decided to train you in our Company.\n"
			+".\nYou have cuurently been assigned to work under " + mentorName + "on the project" + project
			+ "It is going to be between " + jobStartDate + " and " + jobEndDate + ".\nYour User ID is: " + 
			userEmail + "\nYour password is: " + password + " \n\nSincerely, CRIS");
	javaMailSender.send(mail);
	
	try
	{

	 
	} catch (Exception e) 
	{
	System.out.println(e.getMessage());
	}
	
	
	
}



public void sendResultMail(String email, String mailContent) throws MessagingException, UnsupportedEncodingException{
	// TODO Auto-generated method stub
		
	    MimeMessage message = javaMailSender.createMimeMessage();              
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom(new InternetAddress(myMail, "Intern Application"));
	    helper.setTo(email);
	    helper.setFrom(myMail);
		helper.setSubject("CRIS Internship Test Result");
	    helper.setText(mailContent, true);
	    javaMailSender.send(message);
	    
		System.out.println("Mail sent successfully");

	    
}

}
