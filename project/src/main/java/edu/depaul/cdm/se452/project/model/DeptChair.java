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

import lombok.Data;

@Data
@Entity
@Table(name = "DeptChairs")
public class DeptChair implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	//@JoinColumn(nullable = false)
	private int employeeId;
	
	//@Column(nullable = false)
	private String Dept;
	
}