package com.miniproject.repository;

import java.util.List;

/**
 *  @author madeshv
 */
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.miniproject.entity.Degree;
import com.miniproject.entity.University;

@Repository
public interface UniversityRepo extends JpaRepository<University, Integer> {
	
	public abstract University findByUniversityName(String universityName);

	public abstract List<University> findByMinimumCgpaLessThanEqual(float cgpa);
	
	public abstract List<University> findBycountry(String country);
	
	public abstract University findByEmailId(String emailId);
	
	
}
