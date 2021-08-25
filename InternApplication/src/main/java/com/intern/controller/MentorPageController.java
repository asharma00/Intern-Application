package com.intern.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.intern.exception.ResourceNotFoundException;
import com.intern.model.Application;
import com.intern.model.Flags;
import com.intern.model.Interview;
import com.intern.model.User;
import com.intern.repository.ApplicationRepository;
import com.intern.repository.Flagsrepository;
import com.intern.repository.InterviewRepository;
import com.intern.repository.QuestionRepository;
import com.intern.repository.UserRepository;

@Controller
public class MentorPageController 
{
	private QuestionRepository questionRepository;
	
	@Autowired
	private InterviewRepository interviewRepo;
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Autowired
	private FileController fc;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private Flagsrepository flagRepo;
	
	@Autowired
    public MentorPageController(QuestionRepository questionRepository)
    {
		this.questionRepository = questionRepository;
    }
	
	@GetMapping("/mentorPage")
	public String mentorpage()
	{
		return "mentorpage";
	}
	
	@GetMapping("/mentorEmailLeave")
	public String mentoremailleave()
	{
		
		return "mentorEmailLeave";
	}
	
	@GetMapping("/mentorEmailAttendance")
	public String mentoremailattendance()
	{
		
		return "mentorEmailAttendance";
	}

	@GetMapping("/mentorDateWiseAttendance")
	public String mentordatewiseattendance()
	{
		return "mentorDateWiseAttendance";
	}
	
	@GetMapping("/mentorLeaves")
	public String mentorleaves()
	{
		return "mentorLeaves";
	}
	
	@GetMapping("/mentorViewHolidays")
	public String mentorviewholidays()
	{
		return "mentorViewHolidays";
	}
	
	@GetMapping("/mentorViewQuestionBank")
	public String mentorviewquestionbank(Model model)
	{
		model.addAttribute("questions", questionRepository.findAllByOrderByIdAsc());
		return "mentorViewQuestionBank";
	}
	
	@GetMapping("/mentorViewApplicationStatus")
    public String mentorViewApplicationStatus(Model model) 
	{
        return "mentorViewApplicationStatus";
    }
	
	@PostMapping("/mentorCandidateApplication")
    public String getApplicationStatusForMentor(HttpServletRequest request, Model model)throws ResourceNotFoundException 
	{
		String email = request.getParameter("email").toLowerCase();
		System.out.println("emailId is: " + email);
		if(applicationRepository.existsByEmail(email)) 
		{
			List<Application> listApp = applicationRepository.findByEmail(email);
			System.out.println("List size is: " + listApp.size());
			Application application = listApp.get(listApp.size()-1);
			model.addAttribute("applicationFound", "Yes" );
			model.addAttribute("displayApplication",application);
			model.addAttribute("email",email );
			String imgCode = fc.getImageCode(application);
			if(Objects.nonNull(imgCode))
				model.addAttribute("image",imgCode);	
			User u=userRepo.getUserByEmail(email);
			if(u!=null)
			{
				model.addAttribute("user",u);
				Long uid=u.getId();
				Flags flagExam=flagRepo.findByUid(uid);
				if(flagExam!=null)
				{
					model.addAttribute("examMark",flagExam );
				}
			}
			Optional<Interview> inter=interviewRepo.findById(application.getId());
			if(inter.isPresent())
			{
				Interview interview =inter.get();
				model.addAttribute("interviewMark",interview.getMarks() );
			}
		}
		else 
		{
				model.addAttribute("applicationFound","No" );
				model.addAttribute("email", email );
		}
		return "mentorCandidateApplication";
	}
	
}
