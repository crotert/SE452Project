package edu.depaul.cdm.se452.project.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CourseRepositoryTest {
	
	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CourseRepository repository;

	@Test
	public void testFindByCourseNumber() {
		int number = 452;
		assertEquals(1, repository.findByCourseNumber(number).size());	
	}
	
	/*@Test
	public void testFindAll() {
		assertEquals(2, repository.findAll().size());
	}*/
}
