package com.ssafy.withssafy.dto.recruit;

import com.ssafy.withssafy.entity.Recruit;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RecruitDto {

    private Long id;
    private String company;
    private String career;
    private String education;
    private String job;
    private String employType;
    private String salary;
    private String location;
    private String taskDescription;
    private String preferenceDescription;
    private String welfare;
    private String workingHours;
    private String startDate;
    private String endDate;

}
