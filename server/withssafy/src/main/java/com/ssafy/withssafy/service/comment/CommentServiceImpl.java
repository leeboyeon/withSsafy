package com.ssafy.withssafy.service.comment;

import com.ssafy.withssafy.dto.comment.CommentDto;
import com.ssafy.withssafy.entity.Comment;
import com.ssafy.withssafy.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<CommentDto> findAll() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(comment -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> findByBoardId(Long boardId) {
        List<Comment> comments = commentRepository.findByBoardId(boardId);
        return comments.stream().map(comment -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> findByUserId(Long userId) {
        List<Comment> comments = commentRepository.findByUserId(userId);
        return comments.stream().map(comment -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
    }

    @Override
    public CommentDto insert(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        Comment result = commentRepository.save(comment);
        return modelMapper.map(result, CommentDto.class);
    }

    @Override
    @Transactional
    public CommentDto update(CommentDto commentDto) {
        commentRepository.update(commentDto.getId(), commentDto.getContent());
        CommentDto result = modelMapper.map(commentRepository.findById(commentDto.getId()), CommentDto.class);
        return result;
    }

    @Override
    public boolean delete(Long id) {
        if(!commentRepository.findById(id).isPresent()) return false;
        commentRepository.deleteById(id);
        return commentRepository.findById(id).isPresent();
    }
}
