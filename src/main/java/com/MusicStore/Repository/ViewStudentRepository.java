package com.MusicStore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MusicStore.Entity.ViewStudent;

@Repository
public interface ViewStudentRepository extends JpaRepository<ViewStudent, Integer> {

}
