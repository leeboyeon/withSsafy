package com.ssafy.withssafy.service.sbcomment;

import com.ssafy.withssafy.dto.comment.CommentDto;
import com.ssafy.withssafy.dto.sbcomment.SbCommentDto;
import com.ssafy.withssafy.dto.sbcomment.SbCommentRequest;
import com.ssafy.withssafy.dto.studyboard.StudyBoardResponse;
import com.ssafy.withssafy.dto.user.UserDto;
import com.ssafy.withssafy.entity.SbComment;
import com.ssafy.withssafy.repository.SbCommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SbCommentServiceImpl implements SbCommentService{
    @Autowired
    SbCommentRepository sbCommentRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<SbCommentDto> findByUserId(Long userId) {
        List<SbComment> list = sbCommentRepository.findByUserId(userId);
        List<SbCommentDto> result = new ArrayList<>();

        for(SbComment comment : list) {
            SbCommentDto dto = new SbCommentDto();
            dto.setId(comment.getId());
            dto.setContent(comment.getContent());
            dto.setParent(comment.getParent());
            dto.setUser(modelMapper.map(comment.getUser(), UserDto.class));
            dto.setStudyBoard(modelMapper.map(comment.getStudyBoard(), StudyBoardResponse.class));
            result.add(dto);
        }

        return result;
    }

    @Override
    public List<SbCommentDto> findByBoardId(Long boardId) {
        List<SbComment> list = sbCommentRepository.findByBoardId(boardId);
        List<SbCommentDto> result = new ArrayList<>();

        for(SbComment comment : list) {
            SbCommentDto dto = new SbCommentDto();
            dto.setId(comment.getId());
            dto.setContent(comment.getContent());
            dto.setParent(comment.getParent());
            dto.setUser(modelMapper.map(comment.getUser(), UserDto.class));
            dto.setStudyBoard(modelMapper.map(comment.getStudyBoard(), StudyBoardResponse.class));
            result.add(dto);
        }

        return result;
    }

    @Override
    public List<SbCommentDto> findAll() {
        List<SbComment> list = sbCommentRepository.findAll();
        List<SbCommentDto> result = new ArrayList<>();

        for(SbComment comment : list) {
            SbCommentDto dto = new SbCommentDto();
            dto.setId(comment.getId());
            dto.setContent(comment.getContent());
            dto.setParent(comment.getParent());
            dto.setUser(modelMapper.map(comment.getUser(), UserDto.class));
            dto.setStudyBoard(modelMapper.map(comment.getStudyBoard(), StudyBoardResponse.class));
            result.add(dto);
        }

        return result;
    }

    @Override
    @Modifying(clearAutomatically = true)
    public boolean delete(Long id) {
        if (!sbCommentRepository.findById(id).isPresent()) return false;
        sbCommentRepository.deleteById(id);
        return !sbCommentRepository.findById(id).isPresent();
    }

    @Override
    @Transactional
    public SbCommentDto update(SbCommentRequest sbCommentRequest) {
        sbCommentRepository.update(sbCommentRequest.getId(), sbCommentRequest.getContent());
        SbComment sbComment = sbCommentRepository.findById(sbCommentRequest.getId()).get();
        SbCommentDto sbCommentDto = modelMapper.map(sbComment, SbCommentDto.class);
        return sbCommentDto;
    }

    @Override
    public SbCommentDto insert(SbCommentRequest sbCommentRequest) {
        SbComment sbComment = modelMapper.map(sbCommentRequest, SbComment.class);
        SbComment trans = sbCommentRepository.save(sbComment);
        SbCommentDto result = modelMapper.map(trans, SbCommentDto.class);
        result.setUser(modelMapper.map(trans.getUser(), UserDto.class));
        result.setStudyBoard(modelMapper.map(trans.getStudyBoard(), StudyBoardResponse.class));
        return result;
    }
}