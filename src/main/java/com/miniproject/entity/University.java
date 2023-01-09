package com.miniproject.entity;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "university")
public class University {

	@Id
	@Column(name = "institute_code", unique = true, nullable = false)
	private int instituteCode;

	@Column(name = "university_name", nullable = false, length = 50)
	private String universityName;

	@Column(nullable = false, length = 50)
	private String country;

	@Column(nullable = false, length = 40)
	private String state;

	@Column(name = "minimum_cgpa", nullable = false)
	private float minimumCgpa;

	@Column(name = "minimum_ielts_score")
	private float minimumIeltsScore;

	@Column(name = "minimum_gre_score")
	private int minimumGreScore;

//	@Column(name = "seats_available")
////	private int seatsAvailable;

	@Column(nullable = false, name = " email_id", unique = true)
	private String emailId;

	@Column(name = "password", nullable = false)
	private String password;

	
	
	@JsonBackReference
	@ManyToMany(mappedBy = "universities", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private List<Student> students;

//	@JsonManagedReference
//	@OneToMany(mappedBy = "university", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//	private List<Course> courses;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "university_courses", joinColumns = {
	@JoinColumn(name = "institute_code", referencedColumnName = "institute_code") }, inverseJoinColumns = {
	@JoinColumn(name = "course_code", referencedColumnName = "course_code") })
	private List<Course> courses = new ArrayList<Course>();
}
