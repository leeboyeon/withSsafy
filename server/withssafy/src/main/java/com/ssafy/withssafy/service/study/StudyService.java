package com.ssafy.withssafy.service.study;

import com.ssafy.withssafy.dto.study.StudyDto;
import org.springframework.core.io.FileSystemResource;

import java.util.List;

public interface StudyService {
    public void save(StudyDto studyDto);
    public void save(StudyDto studyDto, Long id);
    public List<StudyDto> findAll();
    public StudyDto findById(Long id);
    public void deleteById(Long id);
}
