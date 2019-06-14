package edu.depaul.cdm.se452.project.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

//repository class to help perform CRUD operations on the students table
public interface StudentRepository extends CrudRepository<Student, Long> {
	//find a student by passing an id
	List<Student> findByStudentId(int num);
	
	@Override
	//returns all students available
	List<Student> findAll();
}
