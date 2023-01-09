package com.miniproject.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course")
public class Course {

	@Id
	@Column(nullable=false, name="course_code")
	
	private int courseCode;

	@Column( name = "course_name", length = 60)
	private String courseName;

	@Enumerated(EnumType.STRING)
	@Column(name = "course_duration")
	private CourseYear courseDuration;

	@Enumerated(EnumType.STRING)
	@Column(name = "degree_type")
	private DegreeType degreeType;

	@Column(name = "seats_available")
private int seatsAvailable;
//	@JsonBackReference
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "institute_code")
//	private University university;
//	
	
	@JsonBackReference
	@ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<University> universities;

	@JsonIgnore
	@JsonManagedReference(value = "enroll_course")
	@OneToMany(mappedBy = "course")
	private List<Enroll> enrolls;
	
//	@JsonBackReference(value = "course_student")
//	@ManyToOne
//	@JoinColumn(name = "aadhaar_no")
//	private Student student;
}
