package edu.depaul.cdm.se452.project.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "questions")
public class Question implements Serializable{
	
	public enum AnswerType { STRING, NUMBER, SINGLESELECT, MULTISELECT }

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ToString.Exclude
	// default column name "screener_id"; the screener to which the question belongs
	private Screener screener;
	
	@Column(nullable = false)
	// required question prompt
	private String prompt;
	
	@Column(nullable = false)
	// use string enum type for readability when viewing database table
	// this takes a bit more space but we do not expect large numbers of records for this project
	@Enumerated(EnumType.STRING)
	// answer type informs how the question should be presented in UI and how responses
	// should be interpreted when read from mongodb.
	private AnswerType answerType;
}
