package com.ssafy.withssafy.dto.board;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BoardDto {
    private Long id;
    private Long userId;
    private Long typeId;
    private String content;
    private String photo_path;
    private String title;
    private String write_dt;
}
