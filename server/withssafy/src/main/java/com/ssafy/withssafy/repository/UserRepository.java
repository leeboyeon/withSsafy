package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
