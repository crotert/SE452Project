package edu.depaul.cdm.se452.project.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "sections")
public class Section implements Serializable {
	
	private static final long serialVersionUID = 2L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private int sectionNumber;
	
	//@Column
	//holds the day(s) of the week the class section meets
	//private Date meetingDay;
	
	//@Column
	//holds the meeting time for the course section
	//private Date meetingTime;
	
	//@Column
	//holds the meeting location for the course section
	//ONLINE for online courses
	private String meetingLocation;
	
	//@Column
	//year value for when the section is offered
	//private Date termOffered;
	
	@Column
	//number of possible seats to be filled for the course section
	private int seatCapacity;
	
	@Column
	//number of seats available for the section
	private int availableSeats;
	
	//@Column
	//number of seats to have for students waiting to enroll
	private int waitlistSeats;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
	@JoinColumn(name = "section_course", nullable = false)
	@JsonIgnore
	private Course sectionCourse;
	
	/*@OneToMany(
            mappedBy = "enrolled",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @ToString.Exclude
    private List<Student> enrolledStudents*/;
    
    /*@OneToMany(
            mappedBy = "waitList",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @ToString.Exclude
    private List<Student> waitListStudents*/
}
