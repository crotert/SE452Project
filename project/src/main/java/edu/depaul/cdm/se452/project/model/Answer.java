package edu.depaul.cdm.se452.project.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "answers")
public class Answer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long studentId;
	private long questionId;
	private String answer;
	private Date answerDate;
}
