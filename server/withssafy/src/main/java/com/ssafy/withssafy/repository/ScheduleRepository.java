package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    public List<Schedule> findAllByClassRoomIdAndStartDateBetween(Long classRoomId, LocalDateTime fromDate, LocalDateTime toDate);
}
