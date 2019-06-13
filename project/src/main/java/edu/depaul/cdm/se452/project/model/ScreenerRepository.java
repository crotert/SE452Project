package edu.depaul.cdm.se452.project.model;

import org.springframework.data.repository.CrudRepository;

public interface ScreenerRepository extends CrudRepository<Screener, Long> {
	Screener findByCourseId(int courseId);
	
}
