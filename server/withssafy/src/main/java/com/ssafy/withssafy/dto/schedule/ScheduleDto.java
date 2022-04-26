package com.ssafy.withssafy.dto.schedule;

import com.ssafy.withssafy.entity.User;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ScheduleDto {
    private Long id;
    private Long userId;
    private int type;
    private String end_date;
    private String title;
    private String memo;
    private String start_date;
}
