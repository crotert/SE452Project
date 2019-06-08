package edu.depaul.cdm.se452.project.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnswerSetRepository extends MongoRepository<AnswerSet, String>{
	AnswerSet findByScreenerIdAndStudentId(Long screenerId, int studentId);
}
