package edu.depaul.cdm.se452.project.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "COURSES")
public class Course implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	//@Column
	private String subject;
	
	//@Column
	private int courseNumber;
	
	//@Column
	private String courseDescription;
	
	//@Column
	private double courseCredits;
	
	//@Column
	private int prereqCourseId;
	
	//@Column
	private int concurrentCourseId;
	
	private String courseCode;
	
	private String courseTerm;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn
	@ToString.Exclude
	private Screener screener;
	
	@OneToMany (
			mappedBy = "sectionCourse",
			cascade =  CascadeType.ALL,
			fetch = FetchType.LAZY, orphanRemoval = true
			)
	@ToString.Exclude
	private List<Section> courseSections;
}
