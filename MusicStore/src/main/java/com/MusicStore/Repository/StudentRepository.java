package com.MusicStore.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.MusicStore.Entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
	@Query("SELECT s FROM Student s WHERE s.name LIKE %?1%" + " OR s.name LIKE %?1%" + " OR s.email LIKE %?1%"
			+ " OR s.phone_number LIKE %?1%" + " OR s.address LIKE %?1%" + " OR s.instrument_preferences LIKE %?1%")
	public List<Student> search(String keyword);
	
	@Query("SELECT s FROM Student s WHERE s.schedule_id = ?1")
	public List<Student> attendence(String scheduleID);
	
	@Query("SELECT COUNT(s) FROM Student s")
	public List<Student> counting();

}
