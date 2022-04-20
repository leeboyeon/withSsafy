package com.ssafy.withssafy.service.user;

import com.ssafy.withssafy.dto.board.BoardDto;
import com.ssafy.withssafy.entity.User;
import com.ssafy.withssafy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface UserService {
    public User insertUser(User user);
    public Boolean deleteByUid(String u_id);
    public User updatePasswordByUid(String u_id, String password);
    public User findById(String id);
    public List<User> findAll();
}
