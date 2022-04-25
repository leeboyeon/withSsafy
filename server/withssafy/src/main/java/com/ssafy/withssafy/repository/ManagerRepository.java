package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.Like;
import com.ssafy.withssafy.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager,Long> {
}
