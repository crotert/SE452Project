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

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "STUDENTS")
public class Student implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int student_id;
	
	//@Column
	private String major;
	
	//@Column
	private int gpa;
	
	//@Column
	private String first_name;
	
	//@Column
	private String last_name;
	
	//@Column
	private String email;
	
	public int getStudent_id() {
		return student_id;
	}
	
	public String getMajor() {
		return major;
	}
	public int getGpa() {
		return gpa;
	}
	public String getFirst_name() {
		return first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public String getEmail() {
		return email;
	}
		
	//@JoinColumn
	//@OneToOne(cascade = CascadeType.ALL)
	//private Course course;
	
	/*@OneToMany (
			mappedBy = "student",
			cascade =  CascadeType.ALL,
			fetch = FetchType.LAZY
			)
	@ToString.Exclude
	private List<Course> previousCourses;*/
}
