package com.ssafy.withssafy.dto.board;

import lombok.Data;

@Data
public class BoardRequest {
    private Long id;
    private Long userId;
    private Long typeId;
    private String content;
    private String photoPath;
    private String title;
    private String writeDateTime;
}
