package com.miniproject.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniproject.entity.Course;
import com.miniproject.entity.DegreeType;
import com.miniproject.entity.Enroll;
import com.miniproject.entity.Degree;
import com.miniproject.entity.Gender;
import com.miniproject.entity.Student;
import com.miniproject.entity.University;
import com.miniproject.exception.CustomException;
import com.miniproject.repository.CourseRepo;
import com.miniproject.repository.EnrollRepo;
import com.miniproject.repository.StudentRepo;
import com.miniproject.repository.UniversityRepo;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepo studentRepo;

	@Autowired
	private UniversityRepo universityRepo;

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private EnrollRepo enrollRepo;

	@Override
	public String createStudent(Student student) throws CustomException {

		if (!(student.getFirstName().matches("[A-Za-z]{3,50}"))) {
			throw new CustomException("Invalid first name !");
		}

		if (!(student.getLastName().matches("[A-Za-z]{3,50}"))) {
			throw new CustomException("Invalid last name !");
		}

		if (!(String.valueOf(student.getAadhaarNo()).length() == 12)) {
			throw new CustomException("Aadhaar number must be 12 digits only !");
		}

		if (!(student.getAge() > 0 && student.getAge() < 200)) {
			throw new CustomException("Age shouldn't be zero or negative");
		}

		if (!(student.getCgpa() > 0 && student.getCgpa() <= 10)) {
			throw new CustomException("Cgpa shouldn't be negative or above 10");
		}

		if (!(student.getEmailId()
				.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"))) {
			throw new CustomException("Invalid email id format !");
		}

		if (!(student.getPassword().matches("[A-Za-z0-9]{3,50}"))) {
			throw new CustomException("Password length must be above 3 & one Ucase & Lcase");
		}

		if (!(student.getGender().equals(Gender.MALE) || student.getGender().equals(Gender.FEMALE))) {
			throw new CustomException("Invalid gender type!");
		}

		if (!(student.getPassportNo().matches("[A-Z0-9]{6}"))) {
			throw new CustomException("Passport must be alphanumeric");
		}

		if (!(student.getDegree().equals(Degree.BE) || student.getDegree().equals(Degree.BTECH)
				|| student.getDegree().equals(Degree.BSC))) {
			throw new CustomException("Invalid Degree Type!");
		}

		if (!(student.getGreScore() >= 0 && student.getGreScore() <= 360)) {
			throw new CustomException("Gre cannot be negative or above 360 !");
		}

		if (!(student.getIeltsScore() > 0 && student.getIeltsScore() <= 9)) {
			throw new CustomException("Ielts cannot be negative or above 9 !");
		}
		
		if (!(student.getSpecialization().matches("[a-zA-z_ ]{1,50}"))) {
			throw new CustomException("Invalid Specialization!");
		}

		if(studentRepo.existsByPassportNo(student.getPassportNo()) == true) {
			throw new CustomException("Passport Number Already Used By Other User!");
			
		}
		
		if (studentRepo.existsById(student.getAadhaarNo())) {
			throw new CustomException("Student already exists !");
		} else {
			studentRepo.save(student);
			return "\"Student created successfully !\"";
		}
	}

	@Override
	public List<University> readAllUniversities() throws CustomException {
		List<University> universities = universityRepo.findAll();
		if (universities != null) {
			return universities;
		} else {
			throw new CustomException("No Universities exist in the List");
		}
	}

	@Override
	public List<Course> readAllCourses() throws CustomException {
		List<Course> courses = courseRepo.findAll();

		if (courses != null) {
			return courses;
		} else {
			throw new CustomException("No Courses exist in the List");
		}
	}

	@Override
	public Student readStudent(long aadhaarNo) throws CustomException {

		if (!(aadhaarNo >= 0)) {
			throw new CustomException("Student AadhaarNo cannot be zero or negative");
		}
		if (!(String.valueOf(aadhaarNo).length() == 12)) {
			throw new CustomException("AadhaarNo must be 12 digits!");
		}

		Student student = studentRepo.findById(aadhaarNo).get();

		if (student != null) {
			return student;
		} else {
			throw new CustomException("Student doesn't exist in the List");
		}

	}

	@Override
	public University readUniversityByCode(int instituteCode) throws CustomException {
		if (!(String.valueOf(instituteCode).length() == 4)) {
			throw new CustomException(" Institute code must be four digits only !");
		}
		University university = null;
		if (instituteCode > 0) {
			if (universityRepo.existsById(instituteCode)) {
				Optional<University> optional = universityRepo.findById(instituteCode);

				university = optional.get();
			}
		}
		return university;
	}

	@Override
	public University readUniversityByName(String universityName) throws CustomException {

		University result = universityRepo.findByUniversityName(universityName);

		if (result != null) {
			return result;

		} else {
			throw new CustomException("University doesn't exist !");
		}

	}

	@Override
	public String applyUniversity(int instituteCode, int courseCode, long aadhaarNo) throws CustomException {

		if (!(String.valueOf(instituteCode).length() == 4)) {
			throw new CustomException(" Institute code must be four digits only !");
		}
		if (!(String.valueOf(courseCode).length() == 3)) {
			throw new CustomException("Course code must be three digits only !");
		}
		if (!(String.valueOf(aadhaarNo).length() == 12)) {
			throw new CustomException("Aadhaar no must be 12 digits only ! ");
		}

		if (!(universityRepo.existsById(instituteCode))) {
			throw new CustomException("No University exist with the institute code !");
		}

		List<University> result = new ArrayList<>();

		University university = universityRepo.findById(instituteCode).get();

		result.add(university);

		if (!(courseRepo.existsById(courseCode))) {
			throw new CustomException("No Course exist with the course code !");
		}

		if (!(studentRepo.existsById(aadhaarNo))) {
			throw new CustomException("No Student exist with the Aadhaarno !");
		}

		Student student = studentRepo.findById(aadhaarNo).get();

		if (!(student.getPassportNo() != null)) {
			throw new CustomException("You need a passport to get enrolled !");
		}
		
		for (Student res2 : university.getStudents()) {
			if (res2.getAadhaarNo() == aadhaarNo) {
				throw new CustomException("student already enrolled");
			}

			if (!(studentRepo.findByPassportNo(res2.getPassportNo()) != null)) {
				throw new CustomException("You need a passport to get enrolled !");
			}

		}

		if (!(student.getCgpa() >= university.getMinimumCgpa())) {
			throw new CustomException("Cgpa does't meet the requirement !");
		}
		if (!(student.getGreScore() >= university.getMinimumGreScore())) {
			throw new CustomException("Gre score does't meet the requirement !");
		}

		if (!(student.getIeltsScore() >= university.getMinimumIeltsScore())) {
			throw new CustomException("Ielts score does't meet the requirement !");
		}
		Course course = courseRepo.findById(courseCode).get();
		
		if(student.getDegree().equals(Degree.BSC)&& course.getDegreeType().equals(DegreeType.MASTERS) ) {
			throw new CustomException("You cannot apply to Masters!");
			
		}
//		if(student.getDegree().equals(Degree.BSC)&& course.getDegreeType().equals(DegreeType.MASTERS) ) {
//			throw new CustomException("Canot apply");
//			
//		}
		
		Enroll enroll = new Enroll();
		enroll.setBookedCourseCode(courseCode);
		enroll.setCourseName(course.getCourseName());
		enroll.setEnrollDate(LocalDate.now());
		enroll.setInstituteCode(instituteCode);
		enroll.setPassportNo(student.getPassportNo());
		enroll.setStudentName(student.getFirstName());
		enroll.setUniversityName(university.getUniversityName());
		enroll.setCourse(course);
		enroll.setStudent(student);
		// Course core = new Course();
//		for (Course course : university.getCourses()) {
//			if(course.getCourseCode() == courseCode) {
//				core = course;
//			}
//		}

		// universityRepo.save(university);

		// List<Course> coreList = new ArrayList<Course>();
		// coreList.add(core);
		// univ = university;
		// univ.getCourses().clear();
//univ.setCourses(coreList);
//univ.setInstituteCode(0000);
//univ.setInstituteCode(univ.getInstituteCode());
		course.setSeatsAvailable(course.getSeatsAvailable() - 1);
		courseRepo.save(course);
		enrollRepo.save(enroll);
		university.getStudents().add(student);

		student.getUniversities().add(university);
		// university.setSeatsAvailable(university.getSeatsAvailable() - 1);
		studentRepo.save(student);
//		universityRepo.save(univ);
		return "Congrats ! You got enrolled to the course";

	}

//	@Override
//	public List<Course> filterUniversityByCourseName(String courseName) throws CustomException {
//
//		if (courseRepo.findByCourseName(courseName).size() > 0) {
//
//			return courseRepo.findByCourseName(courseName);
//		} else {
//			throw new CustomException("No course exist");
//		}
//	}

	@Override
	public String updateCgpa(long aadhaarNo, String password, float cgpa) throws CustomException {

		if (!(aadhaarNo > 0)) {
			throw new CustomException("AadhaarNo cannot be zero or negative !");
		}
		if (!(String.valueOf(aadhaarNo).length() == 12)) {
			throw new CustomException("AadhaarNo must be 12 digits only !");
		}
		if (!(studentRepo.existsById(aadhaarNo))) {
			throw new CustomException("No Student exists with the AadhaarNo : " + aadhaarNo);
		}

		Optional<Student> optional = studentRepo.findById(aadhaarNo);
		if (!(optional.isPresent())) {
			throw new CustomException("no Student found");
		}

		if (!(optional.get().getPassword().equals(password))) {
			throw new CustomException("Wrong password !");
		}
		optional.get().setCgpa(cgpa);
		Student student = optional.get();
		studentRepo.save(student);
		return "CGPA Updated successfully";
	}

	@Override
	public List<University> filterUniversityByMinimumCgpa(float cgpa) throws CustomException {

		if (!(cgpa > 0)) {
			throw new CustomException("Cgpa cannot be zero or negative !");
		}
		if (!(cgpa <= 10)) {
			throw new CustomException("Cgpa cannot be above 10");
		}

		List<University> universities = universityRepo.findByMinimumCgpaLessThanEqual(cgpa);

		if (universities.isEmpty()) {
			throw new CustomException("No Universities found in the List !");
		}

		return universities;
	}

	@Override
	public List<University> filterUniversityByCountry(String country) throws CustomException {

		if (!(country.matches("[a-zA-Z]{0,50}"))) {
			throw new CustomException("Country name length exceeded limit 50 !");
		}
		List<University> universities = universityRepo.findBycountry(country);

		if (universities.isEmpty()) {
			throw new CustomException("No Universities found in the List !");
		}
		return universities;
	}

	@Override
	public String updateGreScore(long aadhaarNo, String password, int greScore) throws CustomException {
		if (!(aadhaarNo > 0)) {
			throw new CustomException("AadhaarNo cannot be zero or negative !");
		}
		if (!(String.valueOf(aadhaarNo).length() == 12)) {
			throw new CustomException("AadhaarNo must be 12 digits only !");
		}
		if (!(studentRepo.existsById(aadhaarNo))) {
			throw new CustomException("No Student exists with the AadhaarNo : " + aadhaarNo);
		}

		if (!(greScore > 0)) {
			throw new CustomException("Gre Score cannot be zero or negative");
		}
		if (!(greScore <= 360)) {
			throw new CustomException("Maximum limit of Gre Score is 360 only !");
		}

		Optional<Student> optional = studentRepo.findById(aadhaarNo);
		if (!(optional.isPresent())) {
			throw new CustomException("no Student found");
		}

		if (!(optional.get().getPassword().equals(password))) {
			throw new CustomException("Wrong password !");
		}
		optional.get().setGreScore(greScore);
		;
		Student student = optional.get();
		studentRepo.save(student);
		return "Gre Score Updated successfully";
	}

	@Override
	public String updateIeltsScore(long aadhaarNo, String password, float ieltsScore) throws CustomException {
		if (!(aadhaarNo > 0)) {
			throw new CustomException("AadhaarNo cannot be zero or negative !");
		}
		if (!(String.valueOf(aadhaarNo).length() == 12)) {
			throw new CustomException("AadhaarNo must be 12 digits only !");
		}
		if (!(studentRepo.existsById(aadhaarNo))) {
			throw new CustomException("No Student exists with the AadhaarNo : " + aadhaarNo);
		}

		if (!(ieltsScore > 0)) {
			throw new CustomException("Ielts Score cannot be zero or negative !");
		}
		if (!(ieltsScore <= 9)) {
			throw new CustomException("Maximum limit of Ielts Score is 9 !");
		}

		Optional<Student> optional = studentRepo.findById(aadhaarNo);
		if (!(optional.isPresent())) {
			throw new CustomException("no Student found");
		}

		if (!(optional.get().getPassword().equals(password))) {
			throw new CustomException("Wrong password !");
		}
		optional.get().setIeltsScore(ieltsScore);
		Student student = optional.get();
		studentRepo.save(student);
		return "Ielts Score Updated successfully";
	}

	@Override
	public List<University> filterUniversityByDegreeType(DegreeType degreeType) throws CustomException {
		List<University> temp = new ArrayList<>();
		List<University> universities = universityRepo.findAll();

		for (University university : universities) {
			boolean bool = false;
			for (Course course : university.getCourses()) {
				if (course.getDegreeType().equals(degreeType)) {
					bool = true;

				}

			}
			if (bool == true) {
				temp.add(university);

				// to show only University without courses
				// university.getCourses().clear();

			}

		}
		if (temp.isEmpty()) {
			throw new CustomException(degreeType + " is not available in any Universities !");
		} else {
			return temp;
		}

	}

	@Override
	public Student studentLogin(String emailId) throws CustomException {

		Student student1 = new Student();
		Student student = studentRepo.findByEmailId(emailId);
		if (student.getEmailId().equals(emailId)) {
			student1 = student;
		} else {
			throw new CustomException("Invalid Email");
		}
		return student1;
	}

	@Override
	public Student studentProfile(long aadhaarNo) throws CustomException {

		Student student = studentRepo.findById(aadhaarNo).get();

		if (student == null) {
			throw new CustomException("No Student Found");
		}
		return student;
	}

	@Override
	public List<Course> readUniversityCourses(int instituteCode) throws CustomException {
		University university = universityRepo.findById(instituteCode).get();
		if (university == null) {
			throw new CustomException("University Doesn't exists!");
		}
		List<Course> temp = university.getCourses();

		if (temp.isEmpty()) {
			throw new CustomException(" No Courses available in " + university.getUniversityName() + "!");
		} else {
			return temp;
		}

	}

	@Override
	public String canceEnroll(int enrollId, long aadhaarNo) throws CustomException {
		String msg = "Course Successfully Denied !";
		Enroll enroll = enrollRepo.findById(enrollId).get();

		Student student = studentRepo.findById(aadhaarNo).get();
		student.getUniversities().removeIf(u -> u.getInstituteCode() == enroll.getInstituteCode());
		Course course = courseRepo.findById(enroll.getBookedCourseCode()).get();
		course.setSeatsAvailable(course.getSeatsAvailable() + 1);

		courseRepo.save(course);
//		for (University university : student.getUniversities()) {
//			if(university.getInstituteCode() == enroll.getInstituteCode()) {
//				
//			}
		studentRepo.save(student);

		enrollRepo.delete(enroll);

		return msg;

	}

	@Override
	public List<Course> filterCourseByDegreeType(int instituteCode, DegreeType degreeType) throws CustomException {
		University university = universityRepo.findById(instituteCode).get();
		List<Course> courses = new ArrayList<Course>();

		for (Course course : university.getCourses()) {
			if (course.getDegreeType().equals(degreeType)) {
				courses.add(course);
			}
		}
		if (courses.isEmpty()) {
			throw new CustomException("No Courses available with degree type " + degreeType + "!");
		} else {
			return courses;
		}

	}

	@Override
	public List<Enroll> readAllEnrolls(long aadhaarNo) throws CustomException {

		Student student = studentRepo.findById(aadhaarNo).get();

		if (student == null) {
			throw new CustomException("Student Doesn't Exists !");
		}
		List<Enroll> enrolls = new ArrayList<Enroll>();

		for (Enroll enroll : student.getEnrolls()) {
			enrolls.add(enroll);
		}
		if (enrolls.isEmpty()) {
			throw new CustomException("Student don't have any enroll!");
		} else {
			return enrolls;
		}
	}

	@Override
	public String updateProfile(long aadhaarNo, Student student) throws CustomException {
		Student student2 = studentRepo.findById(aadhaarNo).get();

		String message = "";

		if (student2 == null) {
			throw new CustomException("Student not found");
		} else {

//			if (!(student.getFirstName().length() == 0)) {
//				student2.setFirstName(student.getFirstName());
//			}
//
//			if (!(student.getLastName().length() == 0)) {
//				student2.setLastName(student.getLastName());
//			}

			if (!(student.getCgpa() == 0)) {
				student2.setCgpa(student.getCgpa());
			}

			if (!(student.getGreScore() == 0)) {
				student2.setGreScore(student.getGreScore());
			}

			if (!(student.getIeltsScore() == 0)) {
				student2.setIeltsScore(student.getIeltsScore());
			}

//			if (student2.getPassportNo().isEmpty()) {
//				student2.setPassportNo(student.getPassportNo());
//			} 
			if(student2.getPassportNo().length() == 0) {
				if(!(student.getPassportNo().length() == 0)) {
					student2.setPassportNo(student.getPassportNo());
				}
//				student2.setPassportNo(student.getPassportNo());
			}
			
			studentRepo.save(student2);
			
			message = "Updated Successfully";

		}

		return message;

	}

	@Override
	public List<University> starSearch(long aadhaarNo) throws CustomException {
		Student student=studentRepo.findById(aadhaarNo).get();
		
		List<University> Universities=universityRepo.findAll();
		List<University> uniTemp = new ArrayList<University>();
		
		for (University university : Universities) {
//			if(!(university.getMinimumCgpa() <= student.getCgpa())) {
//				throw new CustomException("No universities accept the cgpa!");
//			}
//			if(!(university.getMinimumIeltsScore() <= student.getIeltsScore())) {
//				throw new CustomException("No universities accept the Ielts Score!");
//			}
//			if(!(university.getMinimumGreScore() <= student.getGreScore())) {
//				throw new CustomException("No universities accept the GreScore!");
//			}
			
			if(university.getMinimumCgpa() <= student.getCgpa() && university.getMinimumIeltsScore() <= student.getIeltsScore() && university.getMinimumGreScore() <= student.getGreScore()) {
				uniTemp.add(university);
			}
			
//			uniTemp.add(university);
		}
		
		if(uniTemp.isEmpty()) {
			throw new CustomException("No Universities found with Star Match!!!");
		}else {
			return uniTemp;
		}
		
		
	}
}
