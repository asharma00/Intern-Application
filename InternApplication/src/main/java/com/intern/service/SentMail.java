package com.intern.service;

import java.util.List;
import java.util.Properties;


import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class SentMail {

	String host="pcpctrlaymailsrvr.cloud.cris.in";   
	String port="25";
		final String sender="internapplicationcris@gmail.com";//change accordingly  
		final String password="interncris14";//change accordingly  
		final String cc_receipient="nkgupta.mmmec@gmail.com,cris.manoj.05@gmail.com ";
		
		 public void sentBulkMail(List<String> receipient, String subject, String content)
		  {
			 Properties props = new Properties();  
				
			     props.put("mail.smtp.host",host);
				  //props.put("mail.smtp.auth", "true");
				  //props.put("mail.smtp.starttls.enable", "true");   //for tsl only
				   props.put("mail.smtp.port", port);   //465  for ssl
				
		          try{
				   Session sessions = Session.getInstance(props,  
							new javax.mail.Authenticator() {  
						protected PasswordAuthentication getPasswordAuthentication() {  
							return new PasswordAuthentication(sender,password);  
						}  
					}); 
				   
		   
				   
				MimeMessage message = new MimeMessage(sessions);  
				message.setFrom(new InternetAddress(sender)); 
				int i=0;
				InternetAddress[] receipientAddress=new InternetAddress[receipient.size()];
				for(String receipientAsString:receipient)
				{
					receipientAddress[i]= new InternetAddress(receipientAsString.trim());
					i++;
				}
				message.setRecipients(Message.RecipientType.TO, receipientAddress);
				message.addRecipients(Message.RecipientType.CC,InternetAddress.parse(cc_receipient)); 
				message.setSubject(subject);//Set Subjects

				message.setContent(content,"text/html");

				//send the message  
				Transport.send(message);
		          }
		          catch(Exception e)
		          {
		        	  e.printStackTrace();  
		          }
				  
			      
		  }
		 
		 
		 public void sentMail(String receipient, String subject, String content)
		  {
			 Properties props = new Properties();  
				
			     props.put("mail.smtp.host",host);
				  //props.put("mail.smtp.auth", "true");
				  //props.put("mail.smtp.starttls.enable", "true");   //for tsl only
				   props.put("mail.smtp.port", port);   //465  for ssl
				
		          try{
				   Session sessions = Session.getInstance(props,  
							new javax.mail.Authenticator() {  
						protected PasswordAuthentication getPasswordAuthentication() {  
							return new PasswordAuthentication(sender,password);  
						}  
					}); 
				   
		   
				   
				MimeMessage message = new MimeMessage(sessions);  
				message.setFrom(new InternetAddress(sender)); 
				int i=0;
				
				message.setRecipients(Message.RecipientType.TO, receipient);
				message.addRecipients(Message.RecipientType.CC,InternetAddress.parse(cc_receipient)); 
				message.setSubject(subject);//Set Subjects

				message.setContent(content,"text/html");

				//send the message  
				Transport.send(message);
		          }
		          catch(Exception e)
		          {
		        	  e.printStackTrace();  
		          }
				  
			      
		  }
	
}
