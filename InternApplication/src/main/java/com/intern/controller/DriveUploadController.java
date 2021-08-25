package com.intern.controller;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.intern.model.DatabaseFile;
import com.intern.repository.ApplicationRepository;
import com.intern.repository.UserRepository;
import com.intern.service.DatabaseFileService;

@RestController
@RequestMapping("/api/v2")
public class DriveUploadController {

	@Autowired
	private DatabaseFileService fileStorageService;
	@Autowired
	private ApplicationRepository applicationRepository;
	@Autowired
	private UserRepository userRepository;
	//public static String uploadDirectory =  "c:/intern_uploads/uploads";

	@PostMapping("/upload")
	
	
	public void upload(@RequestParam String id, @RequestParam("file") MultipartFile file,
			@RequestParam("type") int type) throws IOException {
		Long id1=Long.valueOf(id);
		Long userid=this.applicationRepository.findid1(id1);
       id=userid.toString();
       this.userRepository.delfile(userid);
       
        String os=System.getProperty("os.name");
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

	@GetMapping("/delete")
	public static void deleteFile(File element) throws Exception {
		if (element.isDirectory()) {
			for (File sub : element.listFiles()) {
				deleteFile(sub);
			}
		}
		element.delete();
	}
//web server for chrome
	/*@GetMapping("/getFile")
	public HashMap<Integer, String> uploadFile(@RequestParam Long id) {
		HashMap<Integer, String> filesList = new HashMap<Integer, String>();
		List<DatabaseFile> fileNames = fileStorageService.getFiles(id);
		for (int i = 0; i < fileNames.size(); i++) {
			filesList.put(fileNames.get(i).getType(), "uploads/" + id.toString() + "/"
					+ fileNames.get(i).getType() + "/" + fileNames.get(i).getFileName());
		}
		return filesList;
	}*/

	
	
	 	@GetMapping("/getFile")
	public HashMap<Integer, String> uploadFile(@RequestParam Long id) {
		HashMap<Integer, String> filesList = new HashMap<Integer, String>();
		List<DatabaseFile> fileNames = fileStorageService.getFiles(id);
		
		String baseAddress = "http://203.176.112.83/uploads/";
				//baseAddress= "http://127.0.0.1:8887/uploads/";
		for (int i = 0; i < fileNames.size(); i++) {
			filesList.put(fileNames.get(i).getType(), baseAddress +id.toString() + "/"
										+ fileNames.get(i).getType() + "/" + fileNames.get(i).getFileName());
		}
		return filesList;
	}
	 	
	 	
	 	@GetMapping("/getCerti")
		public String  getCerti(@RequestParam String id) {
		try {	String filenames=null;
			Long id1=Long.valueOf(id);
			 filenames = fileStorageService.getFilesc(id1);
			
			return filenames;
		}
		catch(Exception e){
			System.out.println(e);
			return null;
		}
			
		}
	 
	 
}
