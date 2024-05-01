package com.MusicStore.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.MusicStore.Entity.Schedule;
import com.MusicStore.Repository.ScheduleRepository;


@Service
public class ScheduleService {
	@Autowired
	private ScheduleRepository scheRepo;
	
	public void save(@NonNull Schedule sch) {
		scheRepo.save(sch);
	}

	public List<Schedule> getAllSchedule() {
		return scheRepo.findAll();

	}

	public Schedule getSchedulebyId(int id) {
		return scheRepo.findById(id).get();
	}

	public void deleteById(int id) {
		scheRepo.deleteById(id);
	}
	

}
