package com.ssafy.withssafy.service.user;

import com.ssafy.withssafy.dto.user.LoginDto;
import com.ssafy.withssafy.dto.user.UserDto;
import com.ssafy.withssafy.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto insertUser(UserDto UserDto);
    Boolean deleteByUid(Long id);
    UserDto updatePasswordByUid(Long id, String password);
    UserDto findById(Long id);
    UserDto findByUid(String u_id);
    List<UserDto> findAll();
    LoginDto login(String id, String password);
    UserDto updateClassById(Long id, Long classId);
    LoginDto insertManager(UserDto userDto, int status);
    UserDto updateState(Long id, int state);
    UserDto updateDeviceToken(Long id, String token);
    List<UserDto> findStateZero();
}
