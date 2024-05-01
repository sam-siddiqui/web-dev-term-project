package com.MusicStore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MusicStore.Entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

}
