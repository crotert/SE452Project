package edu.depaul.cdm.se452.project.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface OptionSetRepository extends MongoRepository<OptionSet, String> {
	OptionSet findByQuestionId(Long questionId);
}
