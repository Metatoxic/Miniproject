package com.miniproject.entity;

import java.time.LocalDate;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "enroll")
public class Enroll {
	
	@Id
	@Column(nullable=false, name="enroll_id")
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private int enrollId;
	
	private int instituteCode;
	
	private String universityName;
	
	private  LocalDate enrollDate;
	
	private String studentName;
	
	private String passportNo;
	
	private int bookedCourseCode;
	
	private String courseName;

	
	
	@JsonBackReference(value = "enroll_student")
	@ManyToOne
	@JoinColumn(name = "aadhaar_no")
	private Student student;
	
	
	@JsonBackReference(value = "enroll_course")
	@ManyToOne
	@JoinColumn(name = "course_code")
	private Course course;
	

}
