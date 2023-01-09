package com.miniproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miniproject.entity.Enroll;
import com.miniproject.entity.Student;

public interface EnrollRepo extends JpaRepository<Enroll, Integer >{

}
