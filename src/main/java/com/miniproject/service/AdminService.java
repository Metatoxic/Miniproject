package com.miniproject.service;

import java.util.List;

import com.miniproject.entity.Student;
import com.miniproject.entity.University;
import com.miniproject.exception.CustomException;

public interface AdminService {

	// Delete by university Id
	public abstract String deleteUniversityById(int instituteCode) throws CustomException;

	// Delete by university Id
	public abstract String deleteStudentById(long aadhaarNo) throws CustomException;

	// Read all students
	public List<Student> readAllStudents() throws CustomException;

	// read all courses
	public abstract List<University> readAllUniversities() throws CustomException;

}
