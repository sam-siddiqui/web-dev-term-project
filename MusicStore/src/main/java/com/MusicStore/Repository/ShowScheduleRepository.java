package com.MusicStore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MusicStore.Entity.Show_Schedule;

@Repository
public interface ShowScheduleRepository extends JpaRepository<Show_Schedule, Integer> {

}
