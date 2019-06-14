package edu.depaul.cdm.se452.project.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "feedback")
public class Feedback implements Serializable {
	
	private String professorFullName;
	
	private String studentFullName;
		
	private String positives;
	
	private String negatives;
	
	private Boolean likelyToRecommend;
}