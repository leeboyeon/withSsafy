package com.ssafy.withssafy.dto.report;

import com.ssafy.withssafy.entity.User;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ReportReqDto {
    private Long id;
    private Long board;
    private Long comment;
    private Long user;
    private String content;
}
