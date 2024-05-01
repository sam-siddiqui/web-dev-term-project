package com.MusicStore.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.MusicStore.Entity.Show_Schedule;
import com.MusicStore.Repository.ShowScheduleRepository;

@Service
public class ShowScheduleService {
	@Autowired
	private ShowScheduleRepository scheRepo;

	public void save(@NonNull Show_Schedule sch) {
		scheRepo.save(sch);
	}

	public List<Show_Schedule> getAllShowSchedule() {
		return scheRepo.findAll();

	}

	public Show_Schedule getShowSchedulebyId(int id) {
		return scheRepo.findById(id).get();
	}

	public void deleteById(int id) {
		scheRepo.deleteById(id);
	}
}
