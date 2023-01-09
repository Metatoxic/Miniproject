package com.miniproject.service;

import java.util.List;

import com.miniproject.entity.Course;
import com.miniproject.entity.DegreeType;
import com.miniproject.entity.Enroll;
import com.miniproject.entity.Student;
import com.miniproject.entity.University;
import com.miniproject.exception.CustomException;

public interface StudentService {

	/** ----------------- STUDENT ------------------- **/

	// Create Student
	public String createStudent(Student student) throws CustomException;

	// Read Student
	public Student readStudent(long aadhaarNo) throws CustomException;

	// Update Cgpa using aadhaar id
	public String updateCgpa(long aadhaarNo, String password, float cgpa) throws CustomException;

	// Update GreScore using aadhaar id
	public String updateGreScore(long aadhaarNo, String password, int greScore) throws CustomException;

	// Update ieltsScore using aadhaar id
	public String updateIeltsScore(long aadhaarNo, String password, float ieltsScore) throws CustomException;
	
	//update profile
	public String updateProfile(long aadhaarNo,Student student) throws CustomException;

	/** ----------------- UNIVERSITY ------------------- **/

	// ReadAll Universities
	public List<University> readAllUniversities() throws CustomException;

	// Read university by institute code
	public University readUniversityByCode(int instituteCode) throws CustomException;

	// Read University by university name
	public University readUniversityByName(String universityName) throws CustomException;

	// apply University by aadhaarno, coursename and institutecode
	public String applyUniversity(int instituteCode, int courseCode, long aadhaarNo) throws CustomException;

	// Display universities with specific cgpa
	public List<University> filterUniversityByMinimumCgpa(float cgpa) throws CustomException;

	// display universities in specific country
	public List<University> filterUniversityByCountry(String country) throws CustomException;

	// display university by degree
	public List<University> filterUniversityByDegreeType(DegreeType degreeType) throws CustomException;
	

	// display university by degree
	public List<Course> filterCourseByDegreeType(int instituteCode, DegreeType degreeType) throws CustomException;

	/** ----------------- COURSE ------------------- **/

	// Read all course
	public List<Course> readAllCourses() throws CustomException;

	// Read all university courses
	public List<Course> readUniversityCourses(int instituteCode) throws CustomException;

//	// Filter University by course name
//	public List<Course> filterUniversityByCourseName(String courseName) throws CustomException;

	/** ----------------- LOGIN ------------------- **/

	public abstract Student studentLogin(String emailId) throws CustomException;

	public abstract Student studentProfile(long aadhaarNo) throws CustomException;

	public abstract String canceEnroll(int enrollId, long aadhaarNo) throws CustomException;
	
	/** ----------------- ENROLL ------------------- **/
	public abstract List<Enroll> readAllEnrolls(long aadhaarNo) throws CustomException;
	
	/** ----------------- STAR SEARCH ------------------- **/

	public abstract List<University> starSearch(long aadhaarNo) throws CustomException;
}
