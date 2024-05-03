package com.MusicStore.Repository;

import com.MusicStore.Entity.Student;
import com.MusicStore.Entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.MusicStore.Entity.Course;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("SELECT COUNT(c) FROM Course c")
    public List<Course> counting();
    @Query("""
            SELECT c
            FROM Course c
            INNER JOIN Schedule t
            ON c.course_id = t.course_id
            WHERE t.teacher_id = ?1""")
    public List<Course> getCoursesByTeacherID(String teacherID);

    @Query("""
            SELECT c
            FROM Course c
            INNER JOIN Schedule t
            ON c.course_id = t.course_id
            WHERE t.schedule_id = ?1""")
    public List<Course> getCoursesByStudentScheduleID(String scheduleID);

}
