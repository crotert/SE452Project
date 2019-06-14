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



import edu.depaul.cdm.se452.project.model.Professor;
import edu.depaul.cdm.se452.project.model.ProfessorRepository;
import edu.depaul.cdm.se452.project.model.Feedback;
import edu.depaul.cdm.se452.project.model.FeedbackRepository;

@Controller
@RequestMapping("/feedback")
public class FeedbackController implements WebMvcConfigurer {
	
	@Autowired
	private FeedbackRepository feedRepo;

	
	public FeedbackController(FeedbackRepository repo)
	{
		this.feedRepo = repo;
	}
	
	@GetMapping("/leaveFeedback")
	public String addFeedback(Model model)
	{
		model.addAttribute("feedback", new Feedback());
		model.addAttribute("allFeedback", feedRepo.findAll());
		return "feedback/leaveFeedback";
	}
	
	@PostMapping
	public String saveFeedback(@ModelAttribute("feedback") Feedback feedback, BindingResult bindingResult)
	{
		if(bindingResult.hasErrors()) {
			return "feedback/leaveFeedback";
		}
		feedRepo.save(feedback);
		return "redirect:/feedback/leaveFeedback";
	}	
}
