package com.miniproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.entity.Student;
import com.miniproject.entity.University;
import com.miniproject.exception.CustomException;
import com.miniproject.service.AdminService;

@RestController
@RequestMapping(value = "admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	// DELETE university by id //
	@DeleteMapping(value = "/delete/universitybyid/{instituteCode}")
	public ResponseEntity<String> deleteUniversityById(@PathVariable int instituteCode) throws CustomException {

		return new ResponseEntity<String>(adminService.deleteUniversityById(instituteCode), HttpStatus.OK);

	}

	// READ all Student
	@GetMapping(value = "/read/allstudents")
	public ResponseEntity<List<Student>> readAllStudent() throws CustomException {

		return new ResponseEntity<List<Student>>(adminService.readAllStudents(), HttpStatus.ACCEPTED);

	}

	// READ ALL university by ADMIN //
	@GetMapping(value = "/read/alluniversities")
	public ResponseEntity<List<University>> readAllUniversities() throws CustomException {

		return new ResponseEntity<List<University>>(adminService.readAllUniversities(), HttpStatus.CREATED);
	}

	// DELETE Student by id //
	@DeleteMapping(value = "/delete/studentbyid/{aadhaarNo}")
	public ResponseEntity<String> deleteStudentById(@PathVariable long aadhaarNo) throws CustomException {

		return new ResponseEntity<String>(adminService.deleteStudentById(aadhaarNo), HttpStatus.OK);

	}
	

}
