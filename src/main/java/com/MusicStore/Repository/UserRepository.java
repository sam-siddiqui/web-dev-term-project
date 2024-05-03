package com.MusicStore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.MusicStore.Entity.User;

import java.util.ArrayList;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmailAndPassword(String email, String password);

	@Query("""
			SELECT u FROM User u
			WHERE u.usercontrol = '0'
		""")
	ArrayList<User> usersNeedingApproval();
}
