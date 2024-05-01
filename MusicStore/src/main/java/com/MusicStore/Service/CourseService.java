package com.MusicStore.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.MusicStore.Entity.Course;
import com.MusicStore.Repository.CourseRepository;

@Service
public class CourseService {
	@Autowired
	private CourseRepository cursRepo;

	public void save(@NonNull Course cu) {
		cursRepo.save(cu);
	}

	public List<Course> getAllCourse() {
		return cursRepo.findAll();

	}

	public Course getCoursebyId(int id) {
		return cursRepo.findById(id).get();
	}

	public void deleteById(int id) {
		cursRepo.deleteById(id);
	}
}
