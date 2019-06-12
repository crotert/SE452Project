package edu.depaul.cdm.se452.project.model.view;

import java.util.ArrayList;
import java.util.Arrays;

import edu.depaul.cdm.se452.project.model.Course;
import edu.depaul.cdm.se452.project.model.OptionSet;
import edu.depaul.cdm.se452.project.model.Question;
import edu.depaul.cdm.se452.project.model.Question.AnswerType;
import edu.depaul.cdm.se452.project.model.Screener;
import lombok.Data;

@Data
public class QuestionForm {

	public enum Status { UNSTARTED, STARTED, PUBLISHED }
	
	private Screener screener;
	private int courseId;
	private String courseNickname;
	private Status status;
	private ArrayList<Question> questions;
	private ArrayList<OptionSet> optionSets;
	private ArrayList<OptionSet> optionSetsForDeletion;
	private ArrayList<AnswerType> formats;
	
	public QuestionForm() {}
	
	public QuestionForm(Course course, Screener screener) {
		optionSets = new ArrayList<>();
		optionSetsForDeletion = new ArrayList<>();
		
		formats = new ArrayList<AnswerType>(Arrays.asList(Question.AnswerType.values()));
		courseId = course.getId();
		courseNickname = course.getCourseCode() + course.getCourseNumber() + " " + course.getCourseDescription();
		
		if (screener != null) {
			this.screener = screener;
			status = screener.isPublished() ? Status.PUBLISHED : Status.STARTED;
			this.questions = new ArrayList<>(screener.getQuestions());
		} else {
			this.screener = new Screener();
			status = Status.UNSTARTED;
			questions = new ArrayList<>();
		}
	}
	
	public void addQuestion() {
		Question question = new Question(screener);
		questions.add(question);
		optionSets.add(new OptionSet());	
	}
	
	public void removeQuestion(int index) {
		questions.remove(index);
		OptionSet removedSet = optionSets.remove(index);
		if (removedSet.getQuestionId() > 0) {
			optionSetsForDeletion.add(removedSet);
		}
	}
}
