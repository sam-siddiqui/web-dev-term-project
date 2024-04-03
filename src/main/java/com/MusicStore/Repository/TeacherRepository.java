package com.MusicStore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.MusicStore.Entity.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
	@Query("SELECT s FROM Teacher s WHERE s.name LIKE %?1%" + " OR s.email LIKE %?1%" + " OR s.phone_number LIKE %?1%"
			+ " OR s.expertise LIKE %?1%")
	public List<Teacher> search(String keyword);
}
