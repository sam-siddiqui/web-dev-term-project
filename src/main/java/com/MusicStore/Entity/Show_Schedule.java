package com.MusicStore.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.*;

@Table(name = "teacher_schedule")
@Entity
public class Show_Schedule {
	public Show_Schedule() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int schedule_id;
	@Column(name = "teacher_id", insertable = false, updatable = false)
	private int teacher_id;

	@Column(name = "course_id", insertable = false, updatable = false)
	private int course_id;
	@ManyToOne
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;
	@ManyToOne
	@JoinColumn(name = "course_id")

	private Course course;
	private String schedule_name;
	private String class_day;
	private String start_time;
	private String end_time;

	public int getSchedule_id() {
		return schedule_id;
	}

	public void setSchedule_id(int schedule_id) {
		this.schedule_id = schedule_id;
	}

	public int getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}

	public int getCourse_id() {
		return course_id;
	}

	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getSchedule_name() {
		return schedule_name;
	}

	public void setSchedule_name(String schedule_name) {
		this.schedule_name = schedule_name;
	}

	public String getClass_day() {
		return class_day;
	}

	public void setClass_day(String class_day) {
		this.class_day = class_day;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}



	public Show_Schedule(int schedule_id, int teacher_id, int course_id, Teacher teacher, Course course,
			String schedule_name, String class_day, String start_time, String end_time) {
		super();
		this.schedule_id = schedule_id;
		this.teacher_id = teacher_id;
		this.course_id = course_id;
		this.teacher = teacher;
		this.course = course;
		this.schedule_name = schedule_name;
		this.class_day = class_day;
		this.start_time = start_time;
		this.end_time = end_time;
	}

}