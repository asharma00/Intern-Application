package com.intern.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.intern.service.OtpService;
import com.intern.utility.ResponseForOtp;

@Controller
public class OtpController {

private final Logger logger = LoggerFactory.getLogger(this.getClass());

@Autowired
public OtpService otpService;

@Autowired
private JavaMailSender mailSender;

@Value("${spring.mail.username}")
private String senderMail;

@PostMapping("/otpForApplicationStatus")
public String generateOtp(HttpServletRequest request, Model model){

	String email = request.getParameter("email");

	int otp = otpService.generateOTP(email);
	
	logger.info("OTP : "+otp);
	
    try {
        sendEmail(email, String.valueOf(otp));
        model.addAttribute("message", "We have sent an your email. Please check.");
        model.addAttribute("email", email);
        return "validate-otp";
         
    } 
    catch (UnsupportedEncodingException | MessagingException e) {
        model.addAttribute("error", "Error while sending email");
        return "check-application-status";
    }
    catch (Exception ex) {
        model.addAttribute("error", ex.getMessage());
        return "check-application-status";
    }

}

@PostMapping("/validateOtp")
@ResponseBody
public ResponseForOtp validateOtp(@RequestParam("emailOtp") String email,@RequestParam("otp") String otp){

	Integer otpNum = null;
	try
	{
		otpNum = Integer.parseInt(otp);
	}
	catch(Exception e) {
		
	}
	final String SUCCESS = "Entered Otp is valid";
	final String FAIL = "Entered Otp is NOT valid. Please Retry!";
	
	logger.info(" Otp Number : "+otpNum);
	
	//Validate the Otp 
	if(otpNum >= 0){
	logger.info("Number is present");
	int serverOtp = otpService.getOtp(email);
	logger.info("Server email val : "+email);
	
	if(serverOtp > 0){
	logger.info("Server Otp Number : "+serverOtp);
	if(otpNum == serverOtp){
	otpService.clearOTP(email);
//	if(applicationRepository.existsByEmail(email)) {
//		
//		Application application = applicationRepository.findByEmail(email).get(0);
//		model.addAttribute("applicationFound", "Yes" );
//		model.addAttribute("application", application);
//		
//		}
//		else {
//			
//			model.addAttribute("applicationFound", "No" );
//		}
//		
		return new ResponseForOtp(SUCCESS, email);

	}
	else{
		return new ResponseForOtp(FAIL, email);
	}
	}
	else {
		return new ResponseForOtp(FAIL, email);
	}
	}
	else {
		return new ResponseForOtp(FAIL, email);
	}
}

public void sendEmail(String recipientEmail, String otp)
        throws MessagingException, UnsupportedEncodingException {
    MimeMessage message = mailSender.createMimeMessage();              
    MimeMessageHelper helper = new MimeMessageHelper(message);
     
    helper.setFrom(new InternetAddress(senderMail, "Intern Application"));
    helper.setTo(recipientEmail);
     
    String subject = "Here's the Otp to check your Application Status";
     
    String content = "<p>Hello,</p>"
            + "<p>You have requested an otp to check your application status.</p>"
            + "<p>The generated otp is:</p>"
            + "<p><b>" + otp + "</b></p>"
            +"<br>"
            +"Note that it will be valid for five minutes"
            + "<br>"
            + "<p>Ignore this email if you have not made the request.</p>";
     
    helper.setSubject(subject);
     
    helper.setText(content, true);
     
    mailSender.send(message);
} 

}