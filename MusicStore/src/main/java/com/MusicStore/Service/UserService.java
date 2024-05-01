package com.MusicStore.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.MusicStore.Entity.User;
import com.MusicStore.Repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userrRepo;

	public void save(@NonNull User u) {
		userrRepo.save(u);
	}

	public List<User> getAllUser() {
		return userrRepo.findAll();

	}

	public User getUserbyId(int id) {
		return userrRepo.findById((long) id).get();
	}

	public void deleteById(int id) {
		userrRepo.deleteById((long) id);
	}

}
