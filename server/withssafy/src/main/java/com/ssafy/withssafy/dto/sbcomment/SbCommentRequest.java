package com.ssafy.withssafy.dto.sbcomment;

import com.ssafy.withssafy.dto.studyboard.StudyBoardResponse;
import com.ssafy.withssafy.dto.user.UserDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SbCommentRequest {
    private Long id;
    private Long studyBoardId;
    private Long userId;
    private Long parent;
    private String content;
    private String write_dt;
}
