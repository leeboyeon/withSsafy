package com.ssafy.withssafy.api;

import com.ssafy.withssafy.dto.schedule.ScheduleDto;
import com.ssafy.withssafy.entity.Schedule;
import com.ssafy.withssafy.service.schedule.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Example;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
@Api(tags = "스케줄 API")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    @ApiOperation(value = "일정 추가")
    public ResponseEntity<?> insertSchedule(@RequestBody ScheduleDto scheduleDto){
        scheduleService.saveSchedule(scheduleDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PutMapping
    @ApiOperation(value = "일정 수정")
    public ResponseEntity<?> updateSchedule(@RequestBody ScheduleDto scheduleDto){
        scheduleService.saveSchedule(scheduleDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "일정 삭제")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id){
        scheduleService.deleteSchedule(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "일정 조회")
    public ResponseEntity<ScheduleDto> getSchedule(@PathVariable Long id){
        ScheduleDto scheduleDto = scheduleService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(scheduleDto);
    }

    @GetMapping("/myclass/{id}")
    @ApiOperation(value = "우리반 일정 조회")
    public ResponseEntity<List<ScheduleDto>> getMyClassSchedule(@ApiParam("반 id") @PathVariable Long id,
                                                                @ApiParam("해당 주차의 월요일 날짜(yyyy-mm-dd)") @RequestParam String startDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(startDate, formatter);
        List<ScheduleDto> scheduleDtoList = scheduleService.findMySchedule(id, dateTime.atTime(0,0));
        return ResponseEntity.status(HttpStatus.OK).body(scheduleDtoList);
    }
}
