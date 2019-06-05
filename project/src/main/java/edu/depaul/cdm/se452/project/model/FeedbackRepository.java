package edu.depaul.cdm.se452.project.model;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedbackRepository extends MongoRepository<Feedback, String> {

}