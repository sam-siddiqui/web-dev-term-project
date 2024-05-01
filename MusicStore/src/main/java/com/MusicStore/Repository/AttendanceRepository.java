package com.MusicStore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.MusicStore.Entity.Attendance;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
	@Query("SELECT a FROM Attendance a WHERE a.student_id = ?1")
	public List<Attendance> attendence(String Studentid);
}
