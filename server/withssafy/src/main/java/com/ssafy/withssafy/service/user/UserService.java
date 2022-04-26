package com.ssafy.withssafy.service.user;

import com.ssafy.withssafy.dto.user.LoginDto;
import com.ssafy.withssafy.dto.user.UserDto;
import com.ssafy.withssafy.entity.User;

import java.util.List;
import java.util.Optional;


public interface UserService {
    public UserDto insertUser(UserDto UserDto);
    public Boolean deleteByUid(Long id);
    public UserDto updatePasswordByUid(Long id, String password);
    public UserDto findById(Long id);
    public UserDto findByUid(String u_id);
    public List<UserDto> findAll();
    public LoginDto login(String id, String password);
    UserDto updateClassById(Long id, Long classId);
}
