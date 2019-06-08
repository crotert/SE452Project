package edu.depaul.cdm.se452.project.model;

import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "answersets")
public class AnswerSet {
	@Id
	private ObjectId id;
	private long screenerId;
	private int studentId;
	private Map<Long,String> answers;
	private boolean submitted = false;
}
