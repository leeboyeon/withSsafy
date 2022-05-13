package com.ssafy.withssafy.dto.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CommentDto {
    @ApiModelProperty(example = "댓글 아이디")
    private Long id;
    @ApiModelProperty(example = "댓글 게시판 아이디")
    private Long boardId;
    @ApiModelProperty(example = "댓글 작성자 아이디")
    private Long userId;
    @ApiModelProperty(example = "댓글 깊이")
    private Long parentId;
    @ApiModelProperty(example = "댓글 내용")
    private String content;
    private String write_dt;

    public void setWriteDateTime(){
        this.write_dt = String.valueOf(System.currentTimeMillis());
    }
}
