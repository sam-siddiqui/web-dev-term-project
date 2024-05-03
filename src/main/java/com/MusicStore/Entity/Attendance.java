package com.MusicStore.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "attendance")
@Entity
public class Attendance {
	public Attendance() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int attendance_id;
	private String student_id;
	private int schedule_id;
	private String date;
	private String status;
	private List<Long> student_ids;
    private List<String> statuses;
	public int getAttendance_id() {
		return attendance_id;
	}
	public void setAttendance_id(int attendance_id) {
		this.attendance_id = attendance_id;
	}
	public String getStudent_id() {
		return student_id;
	}
	public void setStudent_id(String studentId) {
		this.student_id = studentId;
	}
	public int getSchedule_id() {
		return schedule_id;
	}
	public void setSchedule_id(int schedule_id) {
		this.schedule_id = schedule_id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Long> getStudent_ids() {
		return student_ids;
	}
	public void setStudent_ids(List<Long> student_ids) {
		this.student_ids = student_ids;
	}
	public List<String> getStatuses() {
		return statuses;
	}
	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}
	public Attendance(int attendance_id, String student_id, int schedule_id, String date, String status,
			List<Long> student_ids, List<String> statuses) {
		super();
		this.attendance_id = attendance_id;
		this.student_id = student_id;
		this.schedule_id = schedule_id;
		this.date = date;
		this.status = status;
		this.student_ids = student_ids;
		this.statuses = statuses;
	}

	

}
