package edu.depaul.cdm.se452.project.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.mongodb.repository.MongoRepository;

//repository class to help perform CRUD operations on the courses table
public interface AdvisorToStudentsRepository extends CrudRepository<Advisors_To_Students, Long> {
	
	List<Advisors_To_Students> findByEmployeeid(int num);
	
	List<Advisors_To_Students> findByStudentid(int num);
	@Override
	//returns all advisors available
	List<Advisors_To_Students> findAll();
	
	
	

}
