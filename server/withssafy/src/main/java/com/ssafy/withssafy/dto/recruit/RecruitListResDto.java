package com.ssafy.withssafy.dto.recruit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RecruitListResDto {

    private Long id;
    private String company;
    private String career;
    private String education;
    private String job;
    private String employType;
    private String salary;
    private String location;
    private String startDate;
    private String endDate;
}
