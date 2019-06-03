package edu.depaul.cdm.se452.project.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "optionsets")
public class OptionSet {
	private long questionId;
	private ArrayList<String> options;
}
