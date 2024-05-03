package com.MusicStore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.MusicStore.Entity.Schedule;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    @Query("""
			SELECT t
			FROM Schedule t
			WHERE t.teacher_id = ?1
			""")
    public List<Schedule> getScheduleByTeacherID(String teacherID);

	@Query("""
			SELECT s
			FROM Schedule s
			WHERE s.schedule_id = ?1
			""")
	public List<Schedule> getScheduleByStudentScheduleID(String schedule_id);
}
