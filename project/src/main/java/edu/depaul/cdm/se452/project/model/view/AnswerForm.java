package edu.depaul.cdm.se452.project.model.view;

import java.util.ArrayList;
import lombok.Data;

@Data
public class AnswerForm {
	
	public enum Status { UNSTARTED, STARTED, SUBMITTED }
	
	private Long screenerId;
	private int studentId;
	private String courseNickname;
	private String description;
	private ArrayList<Answer> answers;
	private Status status;
}
