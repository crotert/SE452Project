package edu.depaul.cdm.se452.project.model.view;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Answer {
	private Long questionId;
	private String prompt;
	private String type;
	private String value;
	
	// Only used for SingleSelect and MultiSelect
	private ArrayList<String> options;
	
	// Only used for MultiSelect
	private ArrayList<String> values;
}
