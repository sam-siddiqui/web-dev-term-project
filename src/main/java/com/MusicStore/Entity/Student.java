package com.MusicStore.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name = "students")
@Entity
public class Student {
	public Student() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int student_id;
	private String name;
	private String email;
	private String phone_number;
	private String address;
	private String instrument_preferences;
	private int schedule_id;
	private String class_day;
	private int course_id;
	private String end_time;
	private String start_time;
	private int teacher_id;
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Billing> bills;
	public int getStudent_id() {
		return student_id;
	}
	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getInstrument_preferences() {
		return instrument_preferences;
	}
	public void setInstrument_preferences(String instrument_preferences) {
		this.instrument_preferences = instrument_preferences;
	}
	public int getSchedule_id() {
		return schedule_id;
	}
	public void setSchedule_id(int schedule_id) {
		this.schedule_id = schedule_id;
	}
	public String getClass_day() {
		return class_day;
	}
	public void setClass_day(String class_day) {
		this.class_day = class_day;
	}
	public int getCourse_id() {
		return course_id;
	}
	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public int getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}
	public List<Billing> getBills() {
		return bills;
	}
	public void setBills(List<Billing> bills) {
		this.bills = bills;
	}
	public Student(int student_id, String name, String email, String phone_number, String address,
			String instrument_preferences, int schedule_id, String class_day, int course_id, String end_time,
			String start_time, int teacher_id, List<Billing> bills) {
		super();
		this.student_id = student_id;
		this.name = name;
		this.email = email;
		this.phone_number = phone_number;
		this.address = address;
		this.instrument_preferences = instrument_preferences;
		this.schedule_id = schedule_id;
		this.class_day = class_day;
		this.course_id = course_id;
		this.end_time = end_time;
		this.start_time = start_time;
		this.teacher_id = teacher_id;
		this.bills = bills;
	}
	
	

}
