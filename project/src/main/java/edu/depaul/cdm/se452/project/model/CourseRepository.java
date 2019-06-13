package edu.depaul.cdm.se452.project.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

//repository class to help perform CRUD operations on the courses table
public interface CourseRepository extends CrudRepository<Course, Integer> {
	//find a course by passing a course number
	List<Course> findByCourseNumber(int num);
	
	@Override
	//returns all courses available
	List<Course> findAll();
}
