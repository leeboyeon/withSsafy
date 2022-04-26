package com.ssafy.withssafy.service.schedule;

import com.ssafy.withssafy.dto.schedule.ScheduleDto;
import com.ssafy.withssafy.entity.Schedule;
import com.ssafy.withssafy.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public void saveSchedule(ScheduleDto scheduleDto){
        Schedule schedule = modelMapper.map(scheduleDto, Schedule.class);
        scheduleRepository.save(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id){
        scheduleRepository.deleteById(id);
    }

    public ScheduleDto findById(Long id) {
        Schedule schedule = scheduleRepository.findById(id).get();
        return modelMapper.map(schedule, ScheduleDto.class);
    }

    public List<ScheduleDto> findMySchedule(Long id, LocalDateTime startDate){
        List<Schedule> recruits = scheduleRepository.findAllByClassRoomIdAndStartDateBetween(id, startDate, startDate.plusDays(7));
        return recruits.stream().map(recruit -> modelMapper.map(recruit, ScheduleDto.class))
                .collect(Collectors.toList());
    }
}
