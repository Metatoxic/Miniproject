package com.miniproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.miniproject.entity.Course;
import com.miniproject.entity.Student;
import com.miniproject.entity.University;
import com.miniproject.exception.CustomException;
import com.miniproject.repository.CourseRepo;
import com.miniproject.repository.UniversityRepo;

@Service
public class UniversityServiceImpl implements UniversityService {

	@Autowired
	private UniversityRepo universityRepo;

	@Autowired
	private CourseRepo courseRepo;

	@Override
	public String createUniversity(University university) throws CustomException {
		
		if(!(String.valueOf(university.getInstituteCode()).length() == 4)) {
			throw new CustomException("Institute code must be four digits only !");
		}
		
		if(!(university.getUniversityName().matches("[a-zA-z_ ]{3,50}"))) {
			throw new CustomException("Invalid University Name!");
		}
		
		if(!(university.getEmailId().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"))) {
			throw new CustomException("Invalid email id format !");
		}
		
		if(!(university.getCountry().matches("[a-zA-z_ ]{1,50}"))) {
			throw new CustomException("country name length must be above 1 !");
		}
		
		if(!(university.getPassword().matches("[A-Za-z0-9]{3,50}"))) {
			throw new CustomException("Password length must be above 3");
		}
		
		if(!(university.getMinimumCgpa() > 0 && university.getMinimumCgpa() <=10)) {
			throw new CustomException("Cgpa must be above zero or above 10! ");
		}
		
		if(!(university.getMinimumGreScore() >= 0 && university.getMinimumGreScore() <= 360)) {
			throw new CustomException("Gre cannot be negative or above 360 !");
		}
		
		if(!(university.getMinimumIeltsScore() >= 0  && university.getMinimumIeltsScore() <= 9 )) {
			throw new CustomException("Ielts cannot be negative or above 9 !");
		}
		
//		if(!(university.getSeatsAvailable() > 0)) {
//			throw new CustomException("seats cannot be negative or zero !");
//		}
//		
		
		
		
		
		
		
		if (universityRepo.existsById(university.getInstituteCode())) {
			throw new CustomException("University Already Exists !");
		} else {

			universityRepo.save(university);

			return "University Created Successfully !";
		}

	}

	@Override
	public String createCourse(Course course,int instituteCode) throws CustomException {
		

		University university = universityRepo.findById(instituteCode).get();
		
		if(!(String.valueOf(course.getCourseCode()).length() == 3)) {
			throw new CustomException("Course code must be three digits only !");
		}
		
		if(!(course.getCourseName().matches("[a-zA-z_ ]{1,50}"))) {
			throw new CustomException("Course name length must be above 1 !");
		}
		
		if(!(course.getSeatsAvailable() > 0 )) {
			throw new CustomException("Seats cannot be zero or negative !");
		}
		
		if(courseRepo.existsById(course.getCourseCode())) {
			throw new CustomException("Course Already Exixts !");
		}else {
			List<Course> courses = university.getCourses(); 
			courses.add(course);
			university.setCourses(courses);
			courseRepo.save(course);
			
			return "Course created successfully !";
		}
				
	}

	@Override
	public List<Course> readAllCourse(int instituteCode) throws CustomException {
		
		if (!(String.valueOf(instituteCode).length() == 4)) {
			throw new CustomException(" Institute code must be four digits only !");
		}

		List<University> universities = universityRepo.findAll();
		List<Course> courses = null;

		for (University res : universities) {
			if(res.getInstituteCode()==instituteCode) {
				courses= res.getCourses();
			}
		}
		return courses;
		

	}

	@Override
	public University readUniversity(int instituteCode) throws CustomException {

		if (!(instituteCode >= 0)) {
			throw new CustomException("University institute code cannot be zero or negative");
		}

		if (!(String.valueOf(instituteCode).length() == 4)) {
			throw new CustomException("Institute must be 4 digits!");
		}

		University university = universityRepo.findById(instituteCode).get();

		if (university != null) {
			return university;
		} else {
			throw new CustomException("University doesn't exist !");
		}

	}

	@Override
	public List<Student> readAllEnrolledStudents(int instituteCode) throws CustomException {
		University university=universityRepo.findById(instituteCode).get();
		
		if(university == null) {
			throw new CustomException("University Doesn't Exists !");
		}
		List<Student> students = new ArrayList<Student>();
		
		for (Student student : university.getStudents()) {
			
			students.add(student);
		}
		if(students.isEmpty()) {
			throw new CustomException("No Students found in "+university.getUniversityName());
		}
		
		return students;
	}

	@Override
	public University universityLogin(String emailId) throws CustomException {
		University university1 = new  University();
		University university = universityRepo.findByEmailId(emailId);
		if(university.getEmailId().equals(emailId)) {
			university1 = university;
		} else {
			throw new CustomException("Invalid Email");
		}
		return university1;
	}

	@Override
	public University universityProfile(int instituteCode) throws CustomException {
		
		
		University university =  universityRepo.findById(instituteCode).get();
		
		if(university==null) {
			throw new CustomException("No University Found");
		}
		return university;
	}

	@Override
	public String updateProfile(int instituteCode, University university) throws CustomException {
		
		University university2 = universityRepo.findById(instituteCode).get();

		String message = "";

		if (university2 == null) {
			throw new CustomException("University not found");
		} else {


			if (!(university.getMinimumCgpa() == 0)) {
				if(university.getMinimumCgpa() < 0) {
					throw new CustomException("Cgpa can't be negative!");
				}
				if(university.getMinimumCgpa() > 10) {
					throw new CustomException("Cgpa can't be above 10!");
				}
				university2.setMinimumCgpa(university.getMinimumCgpa());
			}

			if (!(university.getMinimumGreScore() == 0)) {
				
				if(university.getMinimumGreScore() < 0) {
					throw new CustomException("GRE can't be negative!");
				}
				if(university.getMinimumGreScore() > 360) {
					throw new CustomException("GRE can't be above 360!");
				}
				university2.setMinimumGreScore(university.getMinimumGreScore());
			}

			if (!(university.getMinimumIeltsScore() == 0)) {
				if(university.getMinimumIeltsScore() < 0) {
					throw new CustomException("IELTS can't be negative!");
				}
				if(university.getMinimumIeltsScore() > 9) {
					throw new CustomException("IELTS can't be above 9!");
				}
				university2.setMinimumIeltsScore(university.getMinimumIeltsScore());
			}


			universityRepo.save(university2);
			
			message = "Updated Successfully";

		}

		return message;
	}

	@Override
	public int courseCount(int instituteCode) throws CustomException {
		int num = 0;
		University university = universityRepo.findById(instituteCode).get();
		
		
		num = university.getCourses().size();
		
		if(num != 0) {
			return num;
		}else {
			throw new CustomException("No courses found!");
		}
	}

	@Override
	public int studentCount(int instituteCode) throws CustomException {
		int num = 0;
		University university = universityRepo.findById(instituteCode).get();
		
		num = university.getStudents().size();
		
		if(num != 0) {
			return num;
		}else {
			throw new CustomException("No Students found!");
		}
	}
	
	
}
