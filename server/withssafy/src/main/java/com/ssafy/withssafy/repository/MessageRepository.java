package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository  extends JpaRepository<Message,Long> {

    @Query(value = "SELECT * FROM tbl_message WHERE u_to_id=:id", nativeQuery = true)
    List<Message> findReceiveMessageByUid(Long id);

    @Query(value = "SELECT * FROM tbl_message WHERE u_from_id=:id", nativeQuery = true)
    List<Message> findSendMessageByUid(Long id);
}
