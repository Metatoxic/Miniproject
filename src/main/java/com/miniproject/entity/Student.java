package com.miniproject.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
/**
 *  @author madeshv
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student {

	@Id
	@Column(name = "aadhaar_no", nullable = false, unique = true)
	private long aadhaarNo;
	
	@Column(name = "first_name", length = 30)
	private String firstName;
	
	@Column(name = "last_name", length = 30)
	private String lastName;
	
	@Column(name = "email_id", length = 50, unique = true)
	private String emailId;
	
	
	@Column(length = 50)
	private String password;
	
	private int age;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column(nullable = false)
	private float cgpa;
	
	@Enumerated(EnumType.STRING)
	private Degree degree;
	
	@Column(name = "passport_no", length = 10, unique = true)
	private String passportNo;
	
	@Column(name = "gre_score")
	private int greScore;
	
	@Column(name = "ielts_score")
	private float ieltsScore;
	
	@Column(nullable = false)
	private String specialization;

//	@JsonBackReference
//	@ManyToMany(mappedBy = "students", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "university_students", joinColumns = {
	@JoinColumn(name = "aadhaar_no", referencedColumnName = "aadhaar_no") }, inverseJoinColumns = {
	@JoinColumn(name = "institute_code", referencedColumnName = "institute_code") })
	private List<University> universities = new ArrayList<University>();
	
	
	@JsonIgnore
	@JsonManagedReference(value = "enroll_student")
	@OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
	private List<Enroll> enrolls;

}
