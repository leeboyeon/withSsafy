package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
