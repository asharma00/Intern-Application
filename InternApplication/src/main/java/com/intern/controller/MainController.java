

package com.intern.controller;

import com.intern.model.Datewiseholiday;
import com.intern.model.Holidaymaster;
import com.intern.model.QuestionPaper;
import com.intern.model.SetMaster;
import com.intern.repository.DateHolidayRepository;
import com.intern.repository.HolidaymasterRepository;
import com.intern.repository.QuestionRepository;
import com.intern.repository.SetmasterRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



	
    @Controller
    public class MainController
    {
    	
    	private QuestionRepository questionRepository;
    	private HolidaymasterRepository holidaymasterRepository;
    	private DateHolidayRepository dateHolidayRepository;
    	
    	@Autowired
    	private SetmasterRepository smr;

    	  
        @Autowired
        public MainController(QuestionRepository questionRepository, HolidaymasterRepository holidaymasterRepository,DateHolidayRepository dateHolidayRepository) {
            this.questionRepository = questionRepository;
            this.holidaymasterRepository = holidaymasterRepository;
            this.dateHolidayRepository= dateHolidayRepository;
        }
    
    
        @GetMapping("/emailForApplicationStatus")
    	public String applicationStatusEmail() {
    		return "check-application-status";
    	}
    
	
	@GetMapping("/")
	public String homepage() {
		return "home";
	}
	

	@GetMapping("/toregister")
	public String toregister() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login()
	{
		
		return "login";  
	}
	
	@GetMapping("/setwisepaper")
	public String Setwisepaper()
	{
		
		return "setwisepaper";
	}
	
	
	@GetMapping("/instruction")
	public String login1()
	{
		
		return "instruction";
	}
	
	
	
	
	@GetMapping("/addquestions")
	public String AddQuestions() {
		return "question-index";
	}
	
	
	
	@GetMapping("/holidayyearwisedisplay")
	public String displaypageofyear()
	{
		
		return "view-holiday";
	}
	
	

	
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	
	@GetMapping("/user")
	public String user() {
		return "user";
	}
	
	@GetMapping("/mentor-activities")
	public String mentor()
	{
		return "mentor";
	}
	
	@GetMapping("/logout")  
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {  
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();  
        System.out.println(auth);
        if (auth != null){      
           new SecurityContextLogoutHandler().logout(request, response, auth);  
        }  
         return "redirect:/";  
     }  
	
	
	
	
	
	 
    @GetMapping("/questions/signup")
    public String showSignUpForm(Model model) 
    {
    	QuestionPaper question=new QuestionPaper();
		model.addAttribute("question",question);
		
		List<String> correctansList = Arrays.asList("Option 1","Option 2", "Option 3","Option 4");
		model.addAttribute("correctansList",correctansList);
        return "add-question";
    }

    @GetMapping("/questions/list")
    public String showUpdateForm(Model model) 
    {
        model.addAttribute("questions", questionRepository.findAllByOrderByIdAsc());
        return "question-index";
       
    }
    
 /*   @GetMapping(" /images/0.jpg")
    public String showimg() 
    {
        
        return "0.jpg";
       
    }*/

   
    
    @PostMapping("/questions/add")
    public String addQuestion( QuestionPaper question, BindingResult result, Model model) 
    {
        if (result.hasErrors())
        {
            return "add-question";
        }
        
        questionRepository.save(question);
        return "redirect:list";
       
    }
    
    
    

    @GetMapping("/questions/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model)
    {
    	QuestionPaper question = questionRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid question Id:" + id));
        model.addAttribute("question", question);
        List<String> correctansList = Arrays.asList("Option 1","Option 2", "Option 3","Option 4");
		model.addAttribute("correctansList",correctansList);
        return "update-question";
    }

    @PostMapping("/questions/update/{id}")
    public String updateQuestion(@PathVariable("id") Integer id,QuestionPaper question, BindingResult result,
        Model model) {
        if (result.hasErrors()) {
        	question.setId(id);
        	
            return "update-question";
        }

        questionRepository.save(question);
        model.addAttribute("questions", questionRepository.findAllByOrderByIdAsc());
        List<String> correctansList = Arrays.asList("Option 1","Option 2", "Option 3","Option 4");
		model.addAttribute("correctansList",correctansList);
        return "question-index";
        
    }

    @GetMapping("/questions/delete/{id}")
    public String deleteQuestion(@PathVariable("id") Integer id, Model model) {
    	QuestionPaper question = questionRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
    	questionRepository.delete(question);
    	model.addAttribute("questions",  questionRepository.findAllByOrderByIdAsc());
        return "question-index";
    }
    
    
    
    

	@GetMapping("/exam")
	public String home() {
		return "exam";
	}
	
	@GetMapping("/java") 
	public String java() {
	    return "java";
	}
	
	
	@GetMapping("/android")
	public String android() {
		return "android";
	}
	
	
	
	
	
	@GetMapping("/python")
	public String python() {
		return "python";
	}
	
	@GetMapping("/sql")
	public String sql() {
		return "sql";
	}
	
	@GetMapping("/examresult")
	public String result() {
		return "examresult";
	}
	
	
	
	
	
	@GetMapping("/cloud")
    public String cloud() {
		
		

      

        return "cloud";
    }
	
	@GetMapping("/adminmarks")
	public String Adminmarks() {
		return "admin-marks";
	}
	
	@GetMapping("/emailattendance")
	public String Emailattendance() {
		return "emailattendance";
	}
	@GetMapping("/emailleave")
	public String Emailleave() {
		return "emailLeave";
	}
	
	
	@GetMapping("/datewiseattendance")
	public String DateWiseattendance() {
		return "dateWiseattendance";
	}
	
	
	@GetMapping("/leaves")
	public String Leaves() {
		return "leaves";
	}
	
	@GetMapping("/setholidaymapp")
	public String Setholiday() {
		return "set-holiday";
	}
	
	 @GetMapping("/holidays/list")
	    public String showholidaylist(Model model) 
	    {
	        model.addAttribute("holidays", holidaymasterRepository.findAll());
	        return "holiday-master";
	       
	    }
	 
	 @GetMapping("/holidays/signup")
	    public String showSignUpForm1(Model model) 
	    {
	    	Holidaymaster holiday=new Holidaymaster();
			model.addAttribute("holiday",holiday);
			List<String> holidayList = Arrays.asList("Gazetted","National Holiday");
			model.addAttribute("holidayList",holidayList);
			
			
	        return "add-holiday";
	    }
	 
	   @PostMapping("/holidays/add")
	    public String addHoliday1( Holidaymaster holiday, BindingResult result, Model model) 
	    {
	        if (result.hasErrors())
	        {
	            return "add-holiday";
	        }
	        
	        holidaymasterRepository.save(holiday);
	        return "redirect:list";
	       
	    }
	   
	   @GetMapping("/holidays/edit/{id}")
	    public String showUpdateForm1(@PathVariable("id") long id, Model model) {
	    	Holidaymaster holiday= holidaymasterRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid holiday Id:" + id));
	        model.addAttribute("holiday", holiday);
	        List<String> holidayList = Arrays.asList("Gazetted","National Holiday");
			model.addAttribute("holidayList",holidayList);
			
			
	        return "update-holiday";
	    }

	    @PostMapping("/holidays/update/{id}")
	    public String updateHolidaymaster1(@PathVariable("id") long id,Holidaymaster holiday, BindingResult result,
	        Model model) {
	        if (result.hasErrors()) {
	        	holiday.setId(id);
	            return "update-holiday";
	        }

	        holidaymasterRepository.save(holiday);
	        model.addAttribute("holidays", holidaymasterRepository.findAll());
	        List<String> holidayList = Arrays.asList("Gazetted","National Holiday");
			model.addAttribute("holidayList",holidayList);
			
	        return "holiday-master";
	    }


	    
	    @GetMapping("/holidays/delete/{id}")
	    public String deleteQuestion1(@PathVariable("id") long id, Model model) {
	    	Holidaymaster holiday = holidaymasterRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid holiday Id:" + id));
	    	 holidaymasterRepository.delete(holiday);
	        model.addAttribute("holidays",  holidaymasterRepository.findAll());
	        return "holiday-master";
	    }
	    
	    
	
	    @GetMapping("/holidayss/list")
	    public String showholidaylist5(Model model) 
	    {
	        model.addAttribute("holidayss",  dateHolidayRepository.findAll());
	        return "holiday-add-date";
	       
	    }
	    
	    
	    
	    @GetMapping("/holidayss/signup")
	    public String showSignUpForm5(Model model) 
	    {
	    	Datewiseholiday holiday=new Datewiseholiday();
			model.addAttribute("holiday",holiday);
	
			//List<String> list = Arrays.asList();
			//model.addAttribute("list",list);
			
			
	        return "add-date-holiday";
	    }
	    
	    @PostMapping("/holidayss/add")
	    public String addHoliday5( Datewiseholiday holiday, BindingResult result, Model model) 
	    {
	        if (result.hasErrors())
	        {
	            return "add-date-holiday";
	        }
	        
	        dateHolidayRepository.save(holiday);
	        return "redirect:list";
	       
	    }
	    
	    
	 

	    
	    @PostMapping("/holidayss/update/{id}")
	    public String updateHolidaymaster5(@PathVariable("id") long id,Datewiseholiday holiday, BindingResult result,
	        Model model) {
	        if (result.hasErrors()) {
	        	holiday.setId(id);
	            return "update-date-holiday";
	        }

	        dateHolidayRepository.save(holiday);
	        model.addAttribute("holidayss", dateHolidayRepository.findAll()); 
	      
	        return "holiday-add-date";
	    }

	   


	   
	    
	    @GetMapping("/displayat")
		public String showForm8( Model model) //model is for View which is used to present the user with the look and feel of the application
		{
	    	Datewiseholiday dd=new Datewiseholiday();
			model.addAttribute("displayy",dd);
			
			
			
			return "displaydate";
		}
	    
	    @GetMapping("/searchApplication")
	    public String searchApplication(Model model) {
	   		
	        return "admin-application-search";
	    }
	    
	    @GetMapping("/viewSetWiseQuestionsList/{id}")
		public String setwisepaperList(@PathVariable Long id, Model model)
		{
			try
			{
				
				ArrayList<QuestionPaper> listofques=this.questionRepository.findexamsetwiseAll(id);
				SetMaster sm = smr.findById(id).get();
				model.addAttribute("questions", listofques);
				model.addAttribute("setName", sm.getSetname());
				return "set-wise-for-edit-exam";
			}
			catch(Exception ex)
			{
				System.out.println(ex);
				return null;
			}
			
		}
		
	    
	   
	    
	    
	    
	   
    
    
}
