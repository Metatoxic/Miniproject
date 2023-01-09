package com.miniproject.repository;



import org.springframework.data.jpa.repository.JpaRepository;


import com.miniproject.entity.Student;
import com.miniproject.entity.University;

public interface StudentRepo extends JpaRepository<Student, Long >{
	
	public abstract Student findByPassportNo(String passportNo);
	
	public abstract boolean existsByPassportNo(String passportNo);
	
	public abstract Student findByEmailId(String emailId);

}
