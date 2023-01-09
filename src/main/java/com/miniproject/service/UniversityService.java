package com.miniproject.service;

import java.util.List;

import com.miniproject.entity.Course;
import com.miniproject.entity.Student;
import com.miniproject.entity.University;
import com.miniproject.exception.CustomException;

public interface UniversityService {

	// Create University
	public abstract String createUniversity(University university) throws CustomException;

	// Create course
	public abstract String createCourse(Course course,int instituteCode) throws CustomException;

	// read all courses
	public abstract List<Course> readAllCourse(int instituteCode) throws CustomException;

	// read university
	public abstract University readUniversity(int instituteCode) throws CustomException;

	// read all enrolled students
	public abstract List<Student> readAllEnrolledStudents(int instituteCode) throws CustomException;
	
	//update profile
	public abstract String updateProfile(int instituteCode,University university) throws CustomException;
	
	//for logging in
	public abstract University universityLogin(String emailId ) throws CustomException;
	
	//to fetch the profile
	public abstract University universityProfile(int instituteCode ) throws CustomException;
	
	//course count in the university
	public abstract int courseCount(int instituteCode) throws CustomException;
	
	//course count in the university
	public abstract int studentCount(int instituteCode) throws CustomException;
	
	
}
