package com.ssafy.withssafy.service.comment;

import com.ssafy.withssafy.dto.comment.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> findAll();
    List<CommentDto> findByBoardId(Long BoardId);
    List<CommentDto> findByUserId(Long UserId);
    CommentDto insert(CommentDto commentDto);
    CommentDto update(CommentDto commentDto);
    boolean delete(Long id);
    CommentDto findById(Long id);
}
