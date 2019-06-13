package edu.depaul.cdm.se452.project.model;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "optionsets")
public class OptionSet {
	@Id
	private ObjectId id;
	private long questionId;
	private ArrayList<String> options;
	
	public OptionSet() {
		this(0);
	}
	
	public OptionSet(long questionId) {
		this.questionId = questionId;
		this.options = new ArrayList<>();
	}
	
	public void addOption() {
		options.add("");
	}
	
	public void removeOption(int index) {
		if (index >= 0 && index < options.size()) {
			options.remove(index);	
		}
	}
}
