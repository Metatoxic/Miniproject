package com.miniproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.miniproject.entity.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {

	public abstract boolean existsByCourseName(String CourseName);

}
