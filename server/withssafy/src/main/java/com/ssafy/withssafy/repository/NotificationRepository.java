package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE tbl_notification SET content=:content WHERE id=:id", nativeQuery = true)
    void update(Long id, String content);

//    @Query(value = "SELECT * FROM tbl_notification WHERE user_id=:userId", nativeQuery = true)
    List<Notification> findByUserId(@Param("userId") Long userId);

    List<Notification> findByUserIdAndType(Long userId, Integer type);
}
