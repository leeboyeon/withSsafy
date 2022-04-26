package com.ssafy.withssafy.dto.schedule;

import com.ssafy.withssafy.entity.ClassRoom;
import com.ssafy.withssafy.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ScheduleDto {
    private Long id;
    private Long userId;
    private Long classRoomId;
    @ApiModelProperty(example = "yyyy-mm-dd hh:mm:ss")
    private String end_date;
    private String title;
    private String memo;
    @ApiModelProperty(example = "yyyy-mm-dd hh:mm:ss")
    private String start_date;
}
