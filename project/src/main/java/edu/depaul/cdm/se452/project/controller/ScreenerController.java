package edu.depaul.cdm.se452.project.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import edu.depaul.cdm.se452.project.model.AnswerSet;
import edu.depaul.cdm.se452.project.model.AnswerSetRepository;
import edu.depaul.cdm.se452.project.model.Course;
import edu.depaul.cdm.se452.project.model.CourseRepository;
import edu.depaul.cdm.se452.project.model.OptionSet;
import edu.depaul.cdm.se452.project.model.OptionSetRepository;
import edu.depaul.cdm.se452.project.model.Question;
import edu.depaul.cdm.se452.project.model.Question.AnswerType;
import edu.depaul.cdm.se452.project.model.QuestionRepository;
import edu.depaul.cdm.se452.project.model.Screener;
import edu.depaul.cdm.se452.project.model.ScreenerRepository;
import edu.depaul.cdm.se452.project.model.view.Answer;
import edu.depaul.cdm.se452.project.model.view.AnswerForm;
import edu.depaul.cdm.se452.project.model.view.QuestionDetails;
import edu.depaul.cdm.se452.project.model.view.QuestionForm;

@Controller
@RequestMapping("/screener")
public class ScreenerController implements WebMvcConfigurer {

	@Autowired
	private ScreenerRepository repo;

	@Autowired
	private CourseRepository courseRepo;

	@Autowired
	private QuestionRepository questionRepo;

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
		return "screener/student/all";
	}

	@GetMapping("admin")
	public String showAllAdmin(Model model) {
		Iterable<Course> courses = courseRepo.findAll();
		ArrayList<QuestionForm> questionForms = new ArrayList<>();
		for (Course course : courses) {
			Screener screener = repo.findByCourseId(course.getId());
			QuestionForm questionForm = buildQuestionForm(course, screener);
			questionForms.add(questionForm);
		}
		model.addAttribute("questionForms", questionForms);

		return "screener/admin/all";
	}

	@GetMapping(value = "admin", params = "edit")
	public String edit(@RequestParam String courseId, Model model) {
		Course course = courseRepo.findById(Integer.parseInt(courseId))
				.orElseThrow(() -> new IllegalArgumentException("Invalid course id: " + courseId));
		Screener screener = repo.findByCourseId(Integer.parseInt(courseId));
		QuestionForm questionForm = buildQuestionForm(course, screener, true);
		if (screener == null) {
			saveQuestionsFromForm(questionForm, false);
		}
		model.addAttribute("questionForm", questionForm);
		return "screener/admin/edit";
	}

	@GetMapping(value = "admin", params = "addQuestion")
	public String addQuestion(@RequestParam long screenerId, Model model) {
		Screener screener = repo.findById(screenerId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid screener id: " + screenerId));
		Question question = questionRepo.save(new Question(screener));
		return "redirect:admin?editQuestion&questionId=" + question.getId();
	}

	@GetMapping(value = "admin", params = "editQuestion")
	public String editQuestion(@RequestParam long questionId, Model model) {
		Question question = questionRepo.findById(questionId)
					.orElseThrow(() -> new IllegalArgumentException("Invalid question id: " + questionId));
		QuestionDetails questionDetails = new QuestionDetails(question);
		if (question.getAnswerType().equals(Question.AnswerType.MULTISELECT) || question.getAnswerType().equals(Question.AnswerType.SINGLESELECT)) {
			OptionSet optionSet = optionsRepo.findByQuestionId(questionId);
			questionDetails.setOptionSet(optionSet);
		}
		model.addAttribute("questionDetails", questionDetails);
		return "screener/admin/question";
	}
	
	@PostMapping(value = "admin", params = "reloadQuestion")
	public String reloadQuestion(@ModelAttribute QuestionDetails questionDetails, Model model) {
		return "screener/admin/question";
	}
	
	@PostMapping(value = "admin", params = "newAnswerType")
	public String setAnswerType(@RequestParam AnswerType newAnswerType, @ModelAttribute QuestionDetails questionDetails, Model model) {
		questionDetails.setAnswerType(newAnswerType);
		return "screener/admin/question";
	}
	
	@PostMapping(value = "admin", params = "addOption")
	public String addOption(@ModelAttribute QuestionDetails questionDetails, Model model) {
		questionDetails.addOption();
		return "screener/admin/question";
	}
	
	@PostMapping(value = "admin", params = "removeOption")
	public String removeOption(@RequestParam(name="removeOption") int optionIndex, @ModelAttribute QuestionDetails questionDetails, Model model) {
		questionDetails.removeOption(optionIndex);
		return "screener/admin/question";
	}
	
	@PostMapping(value = "admin", params = "saveQuestion")
	public String saveQuestion(@ModelAttribute QuestionDetails questionDetails, Model model) {
		Question question = questionRepo.save(questionDetails.getQuestion());
		handleOptionSet(questionDetails.getOptionSet(), question.getAnswerType());
		return "redirect:admin?editQuestion&questionId=" + question.getId();
	}

	@GetMapping(value = "admin", params="deleteQuestion")
	public String deleteQuestion(@RequestParam long questionId, Model model) {
		Question question = questionRepo.findById(questionId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid question id: " + questionId));
		int courseId = question.getScreener().getCourse().getId();
		questionRepo.delete(question);
		optionsRepo.deleteByQuestionId(questionId);
		return "redirect:admin?edit&courseId=" + courseId;
	}

	@PostMapping(value = "admin", params = "test")
	public String testQuestion(@ModelAttribute QuestionForm questionForm, Model model) {
		return "redirect:admin?edit&courseId=" + questionForm.getCourseId();
	}

	@PostMapping(value = "admin", params = "action=save")
	public String save(@ModelAttribute QuestionForm questionForm, Model model) {
		saveQuestionsFromForm(questionForm, false);
		return "redirect:admin?edit&courseId=" + questionForm.getCourseId();
	}

	@PostMapping(value = "admin", params = "action=publish")
	public String publish(@ModelAttribute QuestionForm questionForm, Model model) {
		saveQuestionsFromForm(questionForm, true);
		return "redirect:/screener/admin";
	}

	@GetMapping("{screenerId}/answer")
	public String answer(@PathVariable("screenerId") long screenerId, Model model) {
		Optional<Screener> optionalScreener = repo.findById(screenerId);
		if (optionalScreener.isPresent()) {
			Screener screener = optionalScreener.get();
			// TODO HARDCODED TO USE STUDENT ID 1
			AnswerSet answerSet = answersRepo.findByScreenerIdAndStudentId(screenerId, 1);
			model.addAttribute("answerForm", buildAnswerForm(screener, answerSet));
			return "screener/student/answer";
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

	private QuestionForm buildQuestionForm(Course course, Screener screener) {
		return buildQuestionForm(course, screener, false);
	}

	private QuestionForm buildQuestionForm(Course course, Screener screener, boolean includeOptionSets) {
		QuestionForm questionForm = new QuestionForm(course, screener);
		if (includeOptionSets) {
			for (Question question : questionForm.getQuestions()) {
				OptionSet optionSet = optionsRepo.findByQuestionId(question.getId());
				questionForm.getOptionSets().add((optionSet != null) ? optionSet : new OptionSet());
			}
		}
		return questionForm;
	}

	private AnswerForm buildAnswerForm(Screener screener, AnswerSet answerSet) {
		return buildAnswerForm(screener, answerSet, true);
	}

	private AnswerForm buildAnswerForm(Screener screener, AnswerSet answerSet, boolean includeAnswers) {
		AnswerForm answerForm = new AnswerForm();
		// TODO parameterize student
		answerForm.setStudentId(1);
		answerForm.setScreenerId(screener.getId());
		answerForm.setCourseNickname(screener.getCourse().getCourseCode() + screener.getCourse().getCourseNumber() + " "
				+ screener.getCourse().getCourseDescription());
		answerForm.setDescription(screener.getDescription());

		if (answerSet == null) {
			answerForm.setStatus(AnswerForm.Status.UNSTARTED);
		} else {
			answerForm.setStatus(answerSet.isSubmitted() ? AnswerForm.Status.SUBMITTED : AnswerForm.Status.STARTED);
		}

		Map<Long, String> answers = (answerSet != null) ? answerSet.getAnswers() : null;
		if (includeAnswers) {
			setAnswersOnForm(screener, answers, answerForm);
		}
		return answerForm;
	}

	private void setAnswersOnForm(Screener screener, Map<Long, String> answers, AnswerForm answerForm) {
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

	private Screener saveQuestionsFromForm(QuestionForm questionForm, boolean publish) {
		Screener screener = questionForm.getScreener();
		if (publish) {
			screener.setPublished(true);
		}
		return repo.save(screener);
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
				answerMap.put(formAnswer.getQuestionId(), String.join(",", formAnswer.getValues()));
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
	
	private void handleOptionSet(OptionSet optionSet, Question.AnswerType answerType) {
		if (optionSet == null || optionSet.getQuestionId() < 1) {
			return;
		}
		
		if (answerType.equals(Question.AnswerType.MULTISELECT) || answerType.equals(Question.AnswerType.SINGLESELECT)) {
			optionsRepo.save(optionSet);
		} else if (optionSet.getId() != null) {
			optionsRepo.delete(optionSet);
		}
	}
}
