package com.MusicStore.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.MusicStore.Entity.Student;
import com.MusicStore.Repository.StudentRepository;

@Service
public class StudentService {
	@Autowired
	private StudentRepository studRepo;

	public void save(@NonNull Student s) {
		studRepo.save(s);
	}

	public List<Student> getAllStudent() {
		return studRepo.findAll();

	}

	public Student getStudentbyId(int id) {
		return studRepo.findById(id).get();
	}

	public void deleteById(int id) {
		studRepo.deleteById(id);
	}

	public List<Student> listAll(String keyword) {
		if (keyword != null) {
			return studRepo.search(keyword);
		}
		return studRepo.findAll();
	}
	public List<Student> listAllStudents(String scheduleID) {
		if (scheduleID != null) {
			return studRepo.attendence(scheduleID);
		}
		return studRepo.findAll();
	}

	public List<Student> count() {
		// TODO Auto-generated method stub
		return studRepo.counting();
	}
	

}
