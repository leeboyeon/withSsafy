package com.ssafy.withssafy.dto.user;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDto {
    private Long id;
    private String u_id;
    private String password;
    private int state;
    private String device_token;
    private int auth;
    private int s_id;
    private String s_area;
    private int s_gen;
}
