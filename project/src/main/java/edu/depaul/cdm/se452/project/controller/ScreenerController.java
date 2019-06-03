package edu.depaul.cdm.se452.project.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import edu.depaul.cdm.se452.project.model.OptionSet;
import edu.depaul.cdm.se452.project.model.OptionSetRepository;
import edu.depaul.cdm.se452.project.model.Question;
import edu.depaul.cdm.se452.project.model.Question.AnswerType;
import edu.depaul.cdm.se452.project.model.Screener;
import edu.depaul.cdm.se452.project.model.ScreenerRepository;

@Controller
@RequestMapping("/screener")
public class ScreenerController implements WebMvcConfigurer {
	
	@Autowired
	private ScreenerRepository repo;
	
	@Autowired
	private OptionSetRepository optionsRepo;
	public ScreenerController() {}
	
	@GetMapping("answer")
	public String answerScreener(Model model) {
		// HARDCODED TO READ FIRST SCREENER
		Optional<Screener> optionalScreener = repo.findById(1L);
		if (optionalScreener.isPresent()) {
			Screener screener = optionalScreener.get();
			System.out.println(screener.getCourse());
			Collection<Question> questions = screener.getQuestions();
			HashMap<Long, ArrayList<String>> optionSets = new HashMap<>();
			
			for(Question question: questions) {
				AnswerType type = question.getAnswerType();
				if (type.equals(AnswerType.MULTISELECT) || type.equals(AnswerType.SINGLESELECT)) {
					Long questionId = question.getId();
					ArrayList<String> options = optionsRepo.findByQuestionId(questionId).getOptions();
					optionSets.put(questionId, options);
				}
			}
			
			model.addAttribute("screener", screener);
			model.addAttribute("questions", questions);
			model.addAttribute("optionSets", optionSets);
			
			return "screener/answer-screener";			
		} else {
			return "screener/not-found";
		}
	}
}
