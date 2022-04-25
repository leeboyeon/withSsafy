package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query(value = "SELECT * FROM tbl_comment WHERE boardId=:boardId", nativeQuery = true)
    List<Comment> findByBoardId(Long boardId);

    @Query(value = "SELECT * FROM tbl_comment WHERE userId=:userId", nativeQuery = true)
    List<Comment> findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE FROM tbl_comment SET content=:content WHERE id=:id", nativeQuery = true)
    void update(Long id, String content);
}
