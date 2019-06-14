package edu.depaul.cdm.se452.project.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;


import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Data
@Entity
@Table(name = "Advisors_To_Students")
//@Document(collection = "advisor")
public class AdvisorToStudents implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//@Id
	//@Column
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int employeeid;
	
	//@JoinColumn(nullable = false)
	//@Column
	@Id
	private int studentid;
	
	
	
}
