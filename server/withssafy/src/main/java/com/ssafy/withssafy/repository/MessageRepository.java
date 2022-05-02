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

//    @Query(value = "select m1.id, m1.content, m1.send_dt, m1.u_from_id, m1.u_to_id\n" +
//            "from tbl_message m1\n" +
//            "inner join\n" +
//            "(select u_from_id, max(send_dt) send_dt\n" +
//            "from tbl_message m where u_to_id =:id\n" +
//            "group by u_from_id) m2\n" +
//            "on m1.u_from_id = m2.u_from_id and m1.send_dt = m2.send_dt\n" +
//            "order by send_dt desc",nativeQuery = true)
//    List<Message> findMyMessageList(Long id);

    @Query(value = "select * from tbl_message m\n" +
            "where m.u_from_id = :id or m.u_to_id = :id\n" +
            "order by send_dt desc;",nativeQuery = true)
    List<Message> findMyMessageList(Long id);

    @Query(value = "select *\n" +
            "from tbl_message m\n" +
            "where( m.u_from_id =:fromId and m.u_to_id =:toId)\n" +
            "or( m.u_from_id = :toId and m.u_to_id = :fromId)\n" +
            "order by send_dt desc", nativeQuery = true)
    List<Message> findChatList(Long toId,Long fromId);
}
