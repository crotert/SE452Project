package edu.depaul.cdm.se452.project.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OptionSetRepository extends MongoRepository<OptionSet, String> {
	OptionSet findByQuestionId(Long questionId);
	Long deleteByQuestionId(Long questionId);
}
