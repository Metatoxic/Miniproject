package com.miniproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.entity.Course;
import com.miniproject.entity.Student;
import com.miniproject.entity.University;
import com.miniproject.exception.CustomException;
import com.miniproject.service.UniversityService;

@RestController
@RequestMapping(value = "university")
@CrossOrigin(origins = "*")
public class UniversityController {

	@Autowired
	private UniversityService universityService;

	/** ----------------- CREATE ------------------- **/

	@PostMapping(value = "/create")
	public ResponseEntity<String> createUniversity(@RequestBody University university) throws CustomException {

		String result = universityService.createUniversity(university);

		return new ResponseEntity<String>(result, HttpStatus.CREATED);

	}

	@PostMapping(value = "add/course/{instituteCode}")
	public ResponseEntity<String> createCourse(@RequestBody Course course, @PathVariable int instituteCode) throws CustomException {

		return new ResponseEntity<String>(universityService.createCourse(course , instituteCode), HttpStatus.OK);

	}

	/** ----------------- READ ------------------- **/

	@GetMapping(value = "read/allcourse/{instituteCode}")
	public ResponseEntity<List<Course>> readAllCourse(@PathVariable int instituteCode) throws CustomException {

		return new ResponseEntity<List<Course>>(universityService.readAllCourse(instituteCode), HttpStatus.CREATED);

	}

	@GetMapping(value = "/read/{instituteCode}")
	public ResponseEntity<University> readAllCourses(@PathVariable int instituteCode) throws CustomException {

		return new ResponseEntity<University>(universityService.readUniversity(instituteCode), HttpStatus.OK);
	}

	@GetMapping(value = "/read/enrolledlist/{instituteCode}")
	public ResponseEntity<List<Student>> readAllEnrolledStudents(@PathVariable int instituteCode) throws CustomException {

		return new ResponseEntity<List<Student>>(universityService.readAllEnrolledStudents(instituteCode), HttpStatus.OK);
	}

	
	@GetMapping(value ="universitylogin/{email}")
	public  ResponseEntity<University>  universityLogin(@PathVariable String email ) throws CustomException {
		return new ResponseEntity<University>(universityService.universityLogin(email), HttpStatus.OK);

		
	}
	
	@GetMapping(value = "universityprofile/{instituteCode}")
	public ResponseEntity<University> universityProfile(@PathVariable int instituteCode) throws CustomException{
		
		University university =  universityService.universityProfile(instituteCode);
		
		return new ResponseEntity<University>(university, HttpStatus.OK);
		
		
	}
	
	@PutMapping(value = "/update/profile/{instituteCode}")
	public ResponseEntity<String> updateUniversityProfile(@PathVariable int instituteCode,@RequestBody University university) throws CustomException {

		return new ResponseEntity<String>(universityService.updateProfile(instituteCode, university),HttpStatus.OK);
	}
	// course count
	@GetMapping(value = "coursecount/{instituteCode}")
	public ResponseEntity<Integer> courseCount(@PathVariable int instituteCode) throws CustomException{
		
		int num =  universityService.courseCount(instituteCode);
		
		return new ResponseEntity<Integer>(num, HttpStatus.OK);
		
		
	}
	//student count
	@GetMapping(value = "studentcount/{instituteCode}")
	public ResponseEntity<Integer> studentCount(@PathVariable int instituteCode) throws CustomException{
		
		int num =  universityService.studentCount(instituteCode);
		
		return new ResponseEntity<Integer>(num, HttpStatus.OK);
		
		
	}
	
}
