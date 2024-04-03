package com.MusicStore.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "teachers")
@Entity
public class Teacher {
	public Teacher() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int teacher_id;
	private String name;
	private String email;
	private String phone_number;
	private String expertise;

	public int getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
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

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public Teacher(int teacher_id, String name, String email, String phone_number, String expertise) {
		super();
		this.teacher_id = teacher_id;
		this.name = name;
		this.email = email;
		this.phone_number = phone_number;
		this.expertise = expertise;
	}

}
