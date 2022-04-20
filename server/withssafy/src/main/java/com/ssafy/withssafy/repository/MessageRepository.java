package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository  extends JpaRepository<Message,Long> {
}
