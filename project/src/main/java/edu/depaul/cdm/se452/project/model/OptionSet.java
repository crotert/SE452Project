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
}
