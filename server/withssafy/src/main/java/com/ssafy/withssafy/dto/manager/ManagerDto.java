package com.ssafy.withssafy.dto.manager;

import com.ssafy.withssafy.dto.user.UserDto;
import com.ssafy.withssafy.entity.User;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
public class ManagerDto {
    private Long id;
    private int auth;
    Long userId;
}
