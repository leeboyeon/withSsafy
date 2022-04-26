package com.ssafy.withssafy.api;

import com.ssafy.withssafy.dto.schedule.ScheduleDto;
import com.ssafy.withssafy.entity.Schedule;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
@Api(tags = "스케줄 API")
public class ScheduleController {

//    @PostMapping
//    public ResponseEntity<?> insertSchedule(@RequestBody ScheduleDto scheduleDto){
//
//        return null;
//    }
//
//    @PutMapping
//    public ResponseEntity<?> updateSchedule(@RequestBody ScheduleDto scheduleDto){
//
//        return null;
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteSchedule(@PathVariable Long id){
//
//        return null;
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Schedule> getSchedule(@PathVariable Long id){
//
//        return null;
//    }

}
