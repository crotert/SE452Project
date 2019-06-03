package edu.depaul.cdm.se452.project.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "screeners")
public class Screener implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	// optional description for screener, allows for notes or instructions from professor
	private String description;
	
	@OneToMany(mappedBy="screener")
	private Collection<Question> questions;
	
	@OneToOne(mappedBy="screener")
	private Course course;
}
