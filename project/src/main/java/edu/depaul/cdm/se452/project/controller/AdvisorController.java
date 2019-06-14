package edu.depaul.cdm.se452.project.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import edu.depaul.cdm.se452.project.model.Advisors_To_Students;
import edu.depaul.cdm.se452.project.model.AdvisorToStudentsRepository;
import edu.depaul.cdm.se452.project.model.Course;
import edu.depaul.cdm.se452.project.model.CourseRepository;
import edu.depaul.cdm.se452.project.model.Student;
import edu.depaul.cdm.se452.project.model.StudentRepository;

@Controller
@RequestMapping(value="/Advisor")

public class AdvisorController implements WebMvcConfigurer {
	
	
	@Autowired
	private AdvisorToStudentsRepository advisorRepo;
	@Autowired
	private CourseRepository courseRepo;
	@Autowired
	private StudentRepository StudentRepo;
	
	
	public AdvisorController (AdvisorToStudentsRepository Arepo,CourseRepository Crepo,StudentRepository Srepo) {
		
		this.advisorRepo = Arepo;
		this.courseRepo=Crepo;
		this.StudentRepo=Srepo;
	}
	

	
	@GetMapping(value="/showStudents")
	public String showAllCourses(Model model)
	{
		model.addAttribute("Advisor_To_Students", new Advisors_To_Students());
		model.addAttribute("Advisors_To_Students", advisorRepo.findAll());
		model.addAttribute("course",new Course());
		model.addAttribute("courses", courseRepo.findAll());
		model.addAttribute("student",new Student());
		model.addAttribute("students",StudentRepo.findAll());
		
		return ("Advisor/showStudents");
	}
	
	@PostMapping("/courseSearch")
	public String showCourses( @Valid Advisors_To_Students advisor, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			return "Advisor/showStudents";
		}
		
		return "Advisor/showStudents";
	}
	
	@RequestMapping(value="/foundStudents", method=RequestMethod.POST)
	public String searchcourses(Model model) {
		
		model.addAttribute("student",new Student());
		model.addAttribute("students",StudentRepo.findAll());
		
		
		return ("Advisor/foundStudents");
	}
	

}
