package edu.depaul.cdm.se452.project.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import edu.depaul.cdm.se452.project.model.Course;
import edu.depaul.cdm.se452.project.model.CourseRepository;
import edu.depaul.cdm.se452.project.model.Screener;
import edu.depaul.cdm.se452.project.model.view.QuestionForm;

@Controller
@RequestMapping("/student")
public class StudentController implements WebMvcConfigurer {
	
	@Autowired
	private CourseRepository courseRepo;
	
	public StudentController(CourseRepository repo)
	{
		this.courseRepo = repo;
	}
	
	@GetMapping("/addCourses")
	public String showAllCourses(Model model)
	{
		model.addAttribute("course", new Course());
		model.addAttribute("courses", courseRepo.findAll());
		return "student/addCourses";
	}
	
	@GetMapping("/courseDesc")
	public String showCourse(Model model)
	{
		model.addAttribute("course", new Course());
		model.addAttribute("courses", courseRepo.findByCourseNumber(452));	
		return "student/courseDesc";
	}
	

	@PostMapping("/courseSearch")
	public String showCourses( @Valid Course course, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			return "student/addCourses";
		}
		
		return "student/addCourses";
	}
}
