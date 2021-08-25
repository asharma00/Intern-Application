package com.intern.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.intern.model.Application;
import com.intern.repository.ApplicationRepository;
import com.intern.repository.DatabaseFileRepository;
import com.intern.repository.UserRepository;
import com.intern.service.DatabaseFileService;


@Controller
public class FileController {
	
	@Autowired
	private DatabaseFileRepository dbr;
	
	@Autowired
	private DatabaseFileService fileStorageService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;

	
	 //File download
	  @RequestMapping(value = "/downloadFiles/{id}/{type}", method = RequestMethod.GET)
	  @ResponseBody
	  public String downloadFile(HttpServletResponse response, @PathVariable Long id, @PathVariable Integer type) throws IOException  {
		  	 
		    System.out.println("In download files");
		    String fileName = dbr.getFileName(id, type);
		    if(Objects.isNull(fileName))
		    	return("No file found");
		    String fileNameAndPath = null;
	        String os=System.getProperty("os.name");
	        if(type.equals(3)) {
	        	response.setContentType("image/jpeg");
	        }
	        else {
		        response.setContentType("application/pdf");
	        }
	        if(os.startsWith("Windows")){
			fileNameAndPath = "c:" + File.separator + "intern_uploads" + File.separator + "uploads" + File.separator + Long.toString(id) + File.separator + Integer.toString(type) + File.separator + fileName;
	        }
	        else if(os.startsWith("Unix"))
	        {
	        	fileNameAndPath = File.separator + "root" + File.separator + "intern_uploads" + File.separator + "uploads" + File.separator + Long.toString(id) + File.separator + Integer.toString(type) + File.separator + fileName;
	        }
	        else if(os.startsWith("Linux"))
	        {
	        	fileNameAndPath = File.separator + "var" + File.separator + "www" + File.separator + File.separator + "html" + File.separator + "intern_uploads" + File.separator + "uploads" + File.separator + Long.toString(id) + File.separator + Integer.toString(type) + File.separator + fileName;
	        }
	        else {
	        	return "The specified File Path could not be traced.";
	        }
	  
			try {
				
				ServletOutputStream out;
		     	out = response.getOutputStream();
		     	System.out.println(fileNameAndPath);
		     	FileInputStream fin = new FileInputStream(fileNameAndPath);
		     	BufferedInputStream bin = new BufferedInputStream(fin);
		     	BufferedOutputStream bout = new BufferedOutputStream(out);
		     	int ch =0; ;
		     	while((ch=bin.read())!=-1)
		     	{
		     	bout.write(ch);
		     	}
		     	
		     	bin.close();
		     	fin.close();
		     	bout.close();
		     	out.close();
		     	
		     	return "success";
		    	
			} catch(Exception e) {
	    		System.out.println("#####################IN EXCEPTION########################");
	    		System.out.println(e.getMessage());
	    		return e.getMessage();
	    	}
		}
	  
	  
	  @PostMapping("/uploadEdit")	
	  @ResponseBody
		public String uploadEdit(@RequestParam String id, @RequestParam("file") MultipartFile file,
				@RequestParam("type") int type) throws IOException {
		  
		    System.out.println("In the function upload");
			
		    System.out.println(id);
			
	        this.userRepository.delfileIndividual(Long.parseLong(id), type);
	       
	        String os=System.getProperty("os.name");
	        if(os.startsWith("Windows")){

			Path fileNameAndPath = Paths.get("c:/intern_uploads/uploads", id, Integer.toString(type));		
			
			try {
								
				FileUtils.deleteDirectory(new File(fileNameAndPath.toString()));
				
				Files.createDirectories(fileNameAndPath);
				Files.copy(file.getInputStream(), fileNameAndPath.resolve(file.getOriginalFilename()));
				fileStorageService.storeFile(Long.valueOf(id), file.getOriginalFilename(), type);
				
				
				String existingfileName0 = fileStorageService.getFileName(Long.parseLong(id), 0);
				String existingfileName2 = fileStorageService.getFileName(Long.parseLong(id), 2);
				String existingfileName3 = fileStorageService.getFileName(Long.parseLong(id), 3);
				
				if((!Objects.isNull(existingfileName0)) && (!Objects.isNull(existingfileName2)) && (!Objects.isNull(existingfileName3))) {
					Application a = this.applicationRepository.findById(Long.parseLong(id)).get();
					String status = a.getStatus();
					System.out.println("Status is " + status);
					if(status.equals("Started")) {
						System.out.println("Setting to Initiated");
						a.setStatus("Initiated");
						this.applicationRepository.save(a);
					}
				}
				
				return "1";
			} catch (Exception e) {
				
				System.out.println(e);
				return e.getMessage();
				
			}}
	        
	        

	        else if(os.startsWith("Unix"))
	        {
	        	Path fileNameAndPath = Paths.get("/root/intern_uploads/uploads", id, Integer.toString(type));
	        	try {
	        		FileUtils.deleteDirectory(new File(fileNameAndPath.toString()));
	    			Files.createDirectories(fileNameAndPath);
	    			Files.copy(file.getInputStream(), fileNameAndPath.resolve(file.getOriginalFilename()));
	    			fileStorageService.storeFile(Long.valueOf(id), file.getOriginalFilename(), type);
	    			
	    			String existingfileName0 = fileStorageService.getFileName(Long.parseLong(id), 0);
					String existingfileName2 = fileStorageService.getFileName(Long.parseLong(id), 2);
					String existingfileName3 = fileStorageService.getFileName(Long.parseLong(id), 3);
					
					if((!Objects.isNull(existingfileName0)) && (!Objects.isNull(existingfileName2)) && (!Objects.isNull(existingfileName3))) {
						Application a = this.applicationRepository.findById(Long.parseLong(id)).get();
						String status = a.getStatus();
						System.out.println("Status is " + status);
						if(status.equals("Started")) {
							System.out.println("Setting to Initiated");
							a.setStatus("Initiated");
							this.applicationRepository.save(a);
						}
					}
					
	    			return "1";
	    		} catch (Exception e) {
	    			System.out.println(e);
	    			return e.getMessage();
	    		}}
	       
	        
	        else if(os.startsWith("Linux"))
	        {
	        	Path fileNameAndPath = Paths.get("/var/www/html/intern_uploads/uploads", id, Integer.toString(type));
	        	try {
	        	
	        		FileUtils.deleteDirectory(new File(fileNameAndPath.toString()));
	    			Files.createDirectories(fileNameAndPath);
	    			Files.copy(file.getInputStream(), fileNameAndPath.resolve(file.getOriginalFilename()));
	    			fileStorageService.storeFile(Long.valueOf(id), file.getOriginalFilename(), type);
	    			
	    			String existingfileName0 = fileStorageService.getFileName(Long.parseLong(id), 0);
					String existingfileName2 = fileStorageService.getFileName(Long.parseLong(id), 2);
					String existingfileName3 = fileStorageService.getFileName(Long.parseLong(id), 3);
					
					if((!Objects.isNull(existingfileName0)) && (!Objects.isNull(existingfileName2)) && (!Objects.isNull(existingfileName3))) {
						Application a = this.applicationRepository.findById(Long.parseLong(id)).get();
						String status = a.getStatus();
						System.out.println("Status is " + status);
						if(status.equals("Started")) {
							System.out.println("Setting to Initiated");
							a.setStatus("Initiated");
							this.applicationRepository.save(a);
						}
					}
					
	    			return "1";
	    		} catch (Exception e) {
	    			System.out.println(e);
	    			return e.getMessage();
	    		}
	        }
	        
	        return "0";
	        
		}

	  
	  
	  
	public String getImageCode(Application application) {
			
			Long id = application.getId();
			String fileName = dbr.getFileName(id, 1);
		    if(Objects.isNull(fileName))
		    	return null;
		    
		    String fileNameAndPath = null;
	        String os=System.getProperty("os.name");
	        
	        if(os.startsWith("Windows")){
			fileNameAndPath = "c:" + File.separator + "intern_uploads" + File.separator + "uploads" + File.separator + Long.toString(id) + File.separator + Integer.toString(1) + File.separator + fileName;
	        }
	        else if(os.startsWith("Unix"))
	        {
	        	fileNameAndPath = File.separator + "root" + File.separator + "intern_uploads" + File.separator + "uploads" + File.separator + Long.toString(id) + File.separator + Integer.toString(1) + File.separator + fileName;
	        }
	        else if(os.startsWith("Linux"))
	        {
	        	fileNameAndPath = File.separator + "var" + File.separator + "www" + File.separator + File.separator + "html" + File.separator + "intern_uploads" + File.separator + "uploads" + File.separator + Long.toString(id) + File.separator + Integer.toString(1) + File.separator + fileName;
	        }
	        else {
	        	return "The specified File Path could not be traced.";
	        }
	       try {
	        	
			BufferedImage image = ImageIO.read(new File(fileNameAndPath));
			String[] photoArr = fileName.split("\\."); 
	        String type = photoArr[(photoArr.length)-1].toLowerCase();
	       
	        String imgstr = null;	        
	        String imageString = null;
	        
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();

	        
	            ImageIO.write(image, type, bos);
	            byte[] imageBytes = bos.toByteArray();
	            
	            Encoder encoder = Base64.getEncoder();
	            byte[] imageStringBytes = encoder.encode(imageBytes);
	            imageString = new String(imageStringBytes);

	            bos.close();
	            imgstr = "data:image/" + type + ";base64," + imageString;
	            System.out.println(imgstr);
	            return imgstr;
	     
	        }
	        
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return null;
		}
		

	}
			
}



