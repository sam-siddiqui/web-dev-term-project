package com.MusicStore.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "courses")
@Entity
public class Course {
	public Course() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int course_id;
	private String course_name;
	private String description;
	private String duration;
	private String fees;

	public int getCourse_id() {
		return course_id;
	}

	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public Course(int course_id, String course_name, String description, String duration, String fees) {
		super();
		this.course_id = course_id;
		this.course_name = course_name;
		this.description = description;
		this.duration = duration;
		this.fees = fees;
	}

}
