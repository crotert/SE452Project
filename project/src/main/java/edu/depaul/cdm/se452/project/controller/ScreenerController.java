package edu.depaul.cdm.se452.project.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import edu.depaul.cdm.se452.project.model.AnswerSet;
import edu.depaul.cdm.se452.project.model.AnswerSetRepository;
import edu.depaul.cdm.se452.project.model.OptionSet;
import edu.depaul.cdm.se452.project.model.OptionSetRepository;
import edu.depaul.cdm.se452.project.model.Question;
import edu.depaul.cdm.se452.project.model.Question.AnswerType;
import edu.depaul.cdm.se452.project.model.Screener;
import edu.depaul.cdm.se452.project.model.ScreenerRepository;
import edu.depaul.cdm.se452.project.model.view.Answer;
import edu.depaul.cdm.se452.project.model.view.AnswerForm;
import lombok.Data;
import lombok.NoArgsConstructor;

@Controller
@RequestMapping("/screener")
public class ScreenerController implements WebMvcConfigurer {

	@Autowired
	private ScreenerRepository repo;

	@Autowired
	private OptionSetRepository optionsRepo;

	@Autowired
	private AnswerSetRepository answersRepo;

	@GetMapping("all")
	public String listScreeners(Model model) {
		Iterable<Screener> screeners = repo.findAll();
		ArrayList<AnswerForm> answerForms = new ArrayList<>();
		for (Screener screener : screeners) {
			// TODO HARDCODED TO USE STUDENT ID 1
			AnswerSet answerSet = answersRepo.findByScreenerIdAndStudentId(screener.getId(), 1);
			AnswerForm answerForm = buildAnswerForm(screener, answerSet, false);
			answerForms.add(answerForm);
		}
		model.addAttribute("answerForms", answerForms);
		return "screener/all";
	}

	@GetMapping("{screenerId}/answer")
	public String answerScreener(@PathVariable("screenerId") long screenerId, Model model) {
		Optional<Screener> optionalScreener = repo.findById(screenerId);
		if (optionalScreener.isPresent()) {
			Screener screener = optionalScreener.get();
			// TODO HARDCODED TO USE STUDENT ID 1
			AnswerSet answerSet = answersRepo.findByScreenerIdAndStudentId(screenerId, 1);
			model.addAttribute("answerForm", buildAnswerForm(screener, answerSet));
			return "screener/answer";
		} else {
			return "screener/not-found";
		}
	}

	@PostMapping(value = "{screenerId}/answer", params = "action=save")
	public String saveAnswers(@ModelAttribute AnswerForm answerForm, Model model) {
		saveAnswersFromForm(answerForm, false);
		return "redirect:answer";
	}

	@PostMapping(value = "{screenerId}/answer", params = "action=submit")
	public String submitAnswers(@ModelAttribute AnswerForm answerForm, Model model) {
		saveAnswersFromForm(answerForm, true);
		return "redirect:/screener/all";
	}
	
	private AnswerForm buildAnswerForm(Screener screener, AnswerSet answerSet) {
		return buildAnswerForm(screener, answerSet, true);
	}

	private AnswerForm buildAnswerForm(Screener screener, AnswerSet answerSet, boolean setAnswers) {
		AnswerForm answerForm = new AnswerForm();
		// TODO parameterize student
		answerForm.setStudentId(1);
		answerForm.setScreenerId(screener.getId());
		answerForm.setCourseNickname(
				screener.getCourse().getCourseCode() + 
				screener.getCourse().getCourseNumber() + 
				" " +
				screener.getCourse().getCourseDescription());
		answerForm.setDescription(screener.getDescription());
		
		if (answerSet == null) {
			answerForm.setStatus(AnswerForm.Status.UNSTARTED);
		} else {
			answerForm.setStatus(answerSet.isSubmitted() ? AnswerForm.Status.SUBMITTED : AnswerForm.Status.STARTED);
		}
		
		Map<Long, String> answers = (answerSet != null) ? answerSet.getAnswers() : null;
		if (setAnswers) {
			setAnswers(screener, answers,answerForm);
		}
		return answerForm;
	}
	
	private void setAnswers(Screener screener, Map<Long,String> answers, AnswerForm answerForm) {
		ArrayList<Answer> formAnswers = new ArrayList<>();

		for (Question question : screener.getQuestions()) {
			AnswerType answerType = question.getAnswerType();

			Answer formAnswer = new Answer();
			formAnswer.setQuestionId(question.getId());
			formAnswer.setType(answerType.toString());
			formAnswer.setPrompt(question.getPrompt());
			if (answerType.equals(Question.AnswerType.SINGLESELECT)
					|| answerType.equals(Question.AnswerType.MULTISELECT)) {
				formAnswer.setOptions(optionsRepo.findByQuestionId(question.getId()).getOptions());
			}
			
			if (answers != null) {
				String value = answers.get(question.getId());
				if (value != null) {
					if (answerType.equals(Question.AnswerType.MULTISELECT)) {
						ArrayList<String> values = new ArrayList<>(Arrays.asList(value.split(",")));
						formAnswer.setValues(values);
					} else {
						formAnswer.setValue(value);
					}
				}
			}
			formAnswers.add(formAnswer);
		}
		
		answerForm.setAnswers(formAnswers);
	}

	private AnswerSet saveAnswersFromForm(AnswerForm answerForm, boolean submit) {
		Long screenerId = answerForm.getScreenerId();
		int studentId = answerForm.getStudentId();
		AnswerSet answerSet = answersRepo.findByScreenerIdAndStudentId(screenerId, studentId);
		if (answerSet == null) {
			answerSet = new AnswerSet();
			answerSet.setScreenerId(screenerId);
			answerSet.setStudentId(studentId);
		}

		Map<Long, String> answerMap = new HashMap<>();
		for (Answer formAnswer : answerForm.getAnswers()) {
			if (formAnswer.getType().equals(Question.AnswerType.MULTISELECT.toString())) {
				answerMap.put(formAnswer.getQuestionId(), String.join(",",formAnswer.getValues()));
			} else {
				answerMap.put(formAnswer.getQuestionId(), formAnswer.getValue());	
			}
		}
		
		if (submit) {
			answerSet.setSubmitted(true);
		}
		
		answerSet.setAnswers(answerMap);

		return answersRepo.save(answerSet);
	}
}
