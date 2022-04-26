package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    public List<Schedule> findByClassRoomId(Long classRoomId);
}
