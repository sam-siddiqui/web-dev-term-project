package com.MusicStore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.MusicStore.Entity.Show_Schedule;

import java.util.List;

@Repository
public interface ShowScheduleRepository extends JpaRepository<Show_Schedule, Integer> {
    @Query("""
			SELECT t
			FROM Show_Schedule t
			WHERE t.teacher_id = ?1
			""")
    public List<Show_Schedule> getScheduleByTeacherID(String teacherID);
}
