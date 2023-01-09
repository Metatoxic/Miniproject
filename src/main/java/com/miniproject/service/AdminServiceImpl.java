package com.miniproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniproject.entity.Student;
import com.miniproject.entity.University;
import com.miniproject.exception.CustomException;

import com.miniproject.repository.StudentRepo;
import com.miniproject.repository.UniversityRepo;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UniversityRepo universityRepo;

	@Autowired
	private StudentRepo studentRepo;

	@Override
	public String deleteUniversityById(int instituteCode) throws CustomException {

		if (instituteCode > 0 && universityRepo.existsById(instituteCode)) {
			universityRepo.deleteById(instituteCode);
			return "University deleted successfully!";
		} else {
			throw new CustomException("Institute code doesn't exist!");
		}
	}

	@Override
	public List<Student> readAllStudents() throws CustomException {

		List<Student> students = studentRepo.findAll();

		if (students.isEmpty()) {
			throw new CustomException("No Students exist in the List !");
		}

		return students;
	}

	@Override
	public List<University> readAllUniversities() throws CustomException {
		List<University> universities = universityRepo.findAll();
		if (universities != null) {
			return universities;
		} else {
			throw new CustomException("No Universities Exist in the List !");
		}

	}

	@Override
	public String deleteStudentById(long aadhaarNo) throws CustomException {
		if (aadhaarNo > 0 && studentRepo.existsById(aadhaarNo)) {
			studentRepo.deleteById(aadhaarNo);
			return "Student deleted successfully!";
		} else {
			throw new CustomException("Aadhaar number doesn't exist!");
		}
	}

}
