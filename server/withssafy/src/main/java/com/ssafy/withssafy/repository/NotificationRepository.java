package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE tbl_notification SET content=:content WHERE id=:id", nativeQuery = true)
    void update(Long id, String content);
}
