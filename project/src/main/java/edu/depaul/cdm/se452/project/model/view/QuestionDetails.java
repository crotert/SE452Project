package edu.depaul.cdm.se452.project.model.view;

import java.util.ArrayList;
import java.util.Arrays;

import edu.depaul.cdm.se452.project.model.OptionSet;
import edu.depaul.cdm.se452.project.model.Question;
import edu.depaul.cdm.se452.project.model.Question.AnswerType;
import lombok.Data;

@Data
public class QuestionDetails {
	private Question question;
	private OptionSet optionSet;
	private ArrayList<AnswerType> formats;
	
	public QuestionDetails() {
		this(null,null);
	}
	
	public QuestionDetails(Question question) {
		this(question,null);
	}
	
	public QuestionDetails(Question question, OptionSet optionSet) {
		formats = new ArrayList<AnswerType>(Arrays.asList(Question.AnswerType.values()));
		if (question != null) {
			this.question = question;
			if (optionSet != null) {
				this.optionSet = optionSet;
			} else {
				this.optionSet = new OptionSet(question.getId());
			}
		}
		System.out.println("instantiating questionDetails...");
		System.out.println("optionSet: " + this.optionSet);
		System.out.println("question: " + this.question);
	}
	
	public void addOption() {
		optionSet.addOption();
	}
	
	public void removeOption(int optionIndex) {
		optionSet.removeOption(optionIndex);
	}
	
	public void setAnswerType(AnswerType answerType) {
		question.setAnswerType(answerType);
	}
}
