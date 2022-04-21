package com.ssafy.withssafy.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDto {
    @ApiModelProperty(example = "유저 고유 아이디")
    private Long id;
    @ApiModelProperty(example = "유저 계정 아이디")
    private String u_id;
    @ApiModelProperty(example = "비밀번호")
    private String password;
    @ApiModelProperty(example = "계정 상태")
    private int state;
    @ApiModelProperty(example = "디바이스 토큰")
    private String device_token;
    @ApiModelProperty(example = "권한")
    private int auth;
    @ApiModelProperty(example = "학번")
    private int s_id;
    @ApiModelProperty(example = "지역")
    private String s_area;
    @ApiModelProperty(example = "기수")
    private int s_gen;
}
