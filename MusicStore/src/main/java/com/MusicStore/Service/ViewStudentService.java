package com.MusicStore.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.MusicStore.Entity.ViewStudent;
import com.MusicStore.Repository.ViewStudentRepository;

@Service
public class ViewStudentService {
	@Autowired
	private ViewStudentRepository viewstudRepo;

	public void save(@NonNull ViewStudent vs) {
		viewstudRepo.save(vs);
	}

	public List<ViewStudent> getAllViewStudent() {
		return viewstudRepo.findAll();

	}

	public ViewStudent getViewStudentbyId(int id) {
		return viewstudRepo.findById(id).get();
	}

}
