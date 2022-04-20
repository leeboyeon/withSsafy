package com.ssafy.withssafy.service.user;

import com.ssafy.withssafy.entity.User;

import java.util.List;
import java.util.Optional;


public interface UserService {
    public User insertUser(User user);
    public Boolean deleteByUid(Long id);
    public Optional<User> updatePasswordByUid(Long id, String password);
    public Optional<User> findById(Long id);
    public Optional<User> findByUid(String u_id);
    public List<User> findAll();
    public Optional<User> login(String id, String password);
}
