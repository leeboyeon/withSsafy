package com.ssafy.withssafy.dto.message;

import com.ssafy.withssafy.entity.User;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MessageDto {
    @ApiModelProperty(example = "메세지 아이디")
    public Long id;
    @ApiModelProperty(example = "받는 유저 아이디")
    public Long u_toId;
    @ApiModelProperty(example = "보내는 유저 아이디")
    public Long u_fromId;
    @ApiModelProperty(example = "내용")
    public String content;
    @ApiModelProperty(example = "보낸시간")
    public String send_dt;
}
