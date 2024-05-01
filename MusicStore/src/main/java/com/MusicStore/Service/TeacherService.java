package com.MusicStore.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.MusicStore.Entity.Teacher;
import com.MusicStore.Repository.TeacherRepository;

@Service
public class TeacherService {

	@Autowired
	private TeacherRepository teachRepo;

	public void save(@NonNull Teacher t) {
		teachRepo.save(t);
	}

	public List<Teacher> getAllTeacher() {
		return teachRepo.findAll();

	}

	public Teacher getTeacherbyId(int id) {
		return teachRepo.findById(id).get();
	}

	public void deleteById(int id) {
		teachRepo.deleteById(id);
	}

	public List<Teacher> listAll(String keyword) {
		if (keyword != null) {
			return teachRepo.search(keyword);
		}
		return teachRepo.findAll();
	}
}
