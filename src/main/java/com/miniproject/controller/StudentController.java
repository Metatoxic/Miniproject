package com.miniproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.entity.Course;
import com.miniproject.entity.DegreeType;
import com.miniproject.entity.Enroll;
import com.miniproject.entity.Student;
import com.miniproject.entity.University;
import com.miniproject.exception.CustomException;
import com.miniproject.service.StudentService;

@RestController
@RequestMapping(value = "student")
@CrossOrigin(origins = "*")
public class StudentController {

	@Autowired
	private StudentService studentService;

	// create student
	@PostMapping(value = "/create")
	public ResponseEntity<String> createStudent(@RequestBody Student student) throws CustomException {

		return new ResponseEntity<String>(studentService.createStudent(student), HttpStatus.OK);
	}

	// Read student
	@GetMapping(value = "/read/{aadhaarNo}")
	public ResponseEntity<Student> readAllCourses(@PathVariable long aadhaarNo) throws CustomException {
		Student student = studentService.readStudent(aadhaarNo);
		List<University> universities = student.getUniversities();

		for (University university : universities) {
			university.setCourses(null);
		}

		return new ResponseEntity<Student>(student, HttpStatus.OK);
	}

	// ReadAll Universities
	@GetMapping(value = "/read/alluniversities")
	public ResponseEntity<List<University>> readAllUniversities() throws CustomException {

		return new ResponseEntity<List<University>>(studentService.readAllUniversities(), HttpStatus.OK);
	}

	// ReadAll Courses
	@GetMapping(value = "/read/allcourses")
	public ResponseEntity<List<Course>> readAllCourses() throws CustomException {

		return new ResponseEntity<List<Course>>(studentService.readAllCourses(), HttpStatus.OK);
	}

	// ReadAll University Courses
	@GetMapping(value = "/read/universitycourses/{instituteCode}")
	public ResponseEntity<List<Course>> readUniversityCourses(@PathVariable int instituteCode) throws CustomException {

		return new ResponseEntity<List<Course>>(studentService.readUniversityCourses(instituteCode), HttpStatus.OK);
	}

	// Read university by code
	@GetMapping(value = "/read/university/{instituteCode}")
	public ResponseEntity<University> readUniversityByCode(@PathVariable int instituteCode) throws CustomException {

		return new ResponseEntity<University>(studentService.readUniversityByCode(instituteCode), HttpStatus.ACCEPTED);
	}

	// Read university by name
	@GetMapping(value = "/read/universityname/{universityName}")
	public ResponseEntity<University> readUniversityByName(@PathVariable String universityName) throws CustomException {

		return new ResponseEntity<University>(studentService.readUniversityByName(universityName), HttpStatus.ACCEPTED);
	}

	

	// apply university
	@PutMapping(value = "/apply/university/{instituteCode}/{courseCode}/{aadhaarNo}")
	public ResponseEntity<String> applyUniversity(@PathVariable int instituteCode, @PathVariable int courseCode,
			@PathVariable long aadhaarNo) throws CustomException {

		return new ResponseEntity<String>(studentService.applyUniversity(instituteCode, courseCode, aadhaarNo),
				HttpStatus.ACCEPTED);
	}

	// filter university by CGPA
	@GetMapping(value = "/filter/university/cgpa/{cgpa}")
	public ResponseEntity<List<University>> filterUniversityByCgpa(@PathVariable float cgpa) throws CustomException {

		return new ResponseEntity<List<University>>(studentService.filterUniversityByMinimumCgpa(cgpa),
				HttpStatus.ACCEPTED);
	}

	// filter university by Country
	@GetMapping(value = "/filter/university/country/{country}")
	public ResponseEntity<List<University>> filterUniversityByCountry(@PathVariable String country)
			throws CustomException {

		return new ResponseEntity<List<University>>(studentService.filterUniversityByCountry(country), HttpStatus.OK);
	}

	// filter university by degreeType
	@GetMapping(value = "/filter/university/{degreeType}")
	public ResponseEntity<List<University>> filterUniversityByDegreeType(@PathVariable DegreeType degreeType)
			throws CustomException {

		return new ResponseEntity<List<University>>(studentService.filterUniversityByDegreeType(degreeType),
				HttpStatus.OK);
	}

	// filter university by degreeType
	@GetMapping(value = "/filter/course/{instituteCode}/{degreeType}")
	public ResponseEntity<List<Course>> filterCourseByDegreeType(@PathVariable int instituteCode,@PathVariable DegreeType degreeType) throws CustomException {

		return new ResponseEntity<List<Course>>(studentService.filterCourseByDegreeType(instituteCode, degreeType),
				HttpStatus.OK);
	}

	// Update cgpa of a student
	@PutMapping(value = "/update/cgpa/{aadhaarNo}/{password}/{cgpa}")
	public ResponseEntity<String> updateCgpa(@PathVariable long aadhaarNo, @PathVariable String password,
			@PathVariable float cgpa) throws CustomException {

		return new ResponseEntity<String>(studentService.updateCgpa(aadhaarNo, password, cgpa), HttpStatus.OK);
	}

	// Update greScore of a student
	@PutMapping(value = "/update/grescore/{aadhaarNo}/{password}/{greScore}")
	public ResponseEntity<String> updateCgpa(@PathVariable long aadhaarNo, @PathVariable String password,
			@PathVariable int greScore) throws CustomException {

		return new ResponseEntity<String>(studentService.updateGreScore(aadhaarNo, password, greScore),
				HttpStatus.ACCEPTED);
	}

	// Update IeltsScore of a student
	@PutMapping(value = "/update/ieltsscore/{aadhaarNo}/{password}/{ieltsScore}")
	public ResponseEntity<String> updateIeltsScore(@PathVariable long aadhaarNo, @PathVariable String password,
			@PathVariable float ieltsScore) throws CustomException {

		return new ResponseEntity<String>(studentService.updateIeltsScore(aadhaarNo, password, ieltsScore),
				HttpStatus.ACCEPTED);
	}

	// login email
	@GetMapping(value = "/studentlogin/{email}")
	public ResponseEntity<Student> universityLogin(@PathVariable String email) throws CustomException {
		return new ResponseEntity<Student>(studentService.studentLogin(email), HttpStatus.OK);

	}

	// get profile by aadhaar
	@GetMapping(value = "/studentprofile/{aadhaarNo}")
	public ResponseEntity<Student> studentProfile(@PathVariable long aadhaarNo) throws CustomException {

		Student student = studentService.studentProfile(aadhaarNo);

		return new ResponseEntity<Student>(student, HttpStatus.OK);

	}

	// get enrolled details
	@GetMapping(value = "/read/enrolleddetails/{aadhaarNo}")
	public ResponseEntity<List<Enroll>> readAllEnrolls(@PathVariable long aadhaarNo) throws CustomException {

		List<Enroll> enrolls = studentService.readAllEnrolls(aadhaarNo);

		return new ResponseEntity<List<Enroll>>(enrolls, HttpStatus.OK);

	}
	
	// delete or cancel enrollment
	@DeleteMapping(value = "/cancel/university/{enrollId}/{aadhaarNo}")
	public ResponseEntity<String> cancelEnroll(@PathVariable int enrollId, @PathVariable long aadhaarNo)
			throws CustomException {
		return new ResponseEntity<String>(studentService.canceEnroll(enrollId, aadhaarNo), HttpStatus.OK);

	}
	
	// update full profile
	@PutMapping(value = "/update/profile/{aadhaarNo}")
	public ResponseEntity<String> updateProfile(@PathVariable long aadhaarNo,@RequestBody Student student) throws CustomException {

		return new ResponseEntity<String>(studentService.updateProfile(aadhaarNo, student),
				HttpStatus.OK);
	}
	
	// Star Match 
		@GetMapping(value = "/starsearch/{aadhaarNo}")
		public ResponseEntity<List<University>> starSearch(@PathVariable long aadhaarNo) throws CustomException {

			return new ResponseEntity<List<University>>(studentService.starSearch(aadhaarNo), HttpStatus.ACCEPTED);
		}


}
