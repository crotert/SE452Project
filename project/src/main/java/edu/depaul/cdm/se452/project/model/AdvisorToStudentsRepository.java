package edu.depaul.cdm.se452.project.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

//repository class to help perform CRUD operations on the courses table
public interface AdvisorToStudentsRepository extends CrudRepository<AdvisorToStudents, Long> {

}