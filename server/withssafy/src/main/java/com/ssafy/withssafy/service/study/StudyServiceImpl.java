package com.ssafy.withssafy.service.study;

import com.ssafy.withssafy.dto.study.StudyDto;
import com.ssafy.withssafy.entity.StudyBoard;
import com.ssafy.withssafy.repository.StudyBoardRepository;
import com.ssafy.withssafy.util.FileManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudyServiceImpl implements StudyService {

    @Autowired
    StudyBoardRepository studyBoardRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void save(StudyDto studyDto) {
        StudyBoard studyBoard = modelMapper.map(studyDto, StudyBoard.class);

        studyBoardRepository.save(studyBoard);
    }

    @Override
    public void save(StudyDto studyDto, Long id) {
        studyDto.setId(id);
        StudyBoard studyBoard = modelMapper.map(studyDto, StudyBoard.class);


        studyBoardRepository.save(studyBoard);
    }

    @Override
    public List<StudyDto> findAll() {
        List<StudyBoard> studyBoards = studyBoardRepository.findAll();

        List<StudyDto> studies = studyBoards.stream().map(studyBoard -> modelMapper.map(studyBoard, StudyDto.class))
                .collect(Collectors.toList());

        for (StudyDto studyDto : studies) {
            FileSystemResource file = FileManager.getFile(studyDto.getPhotoPath());
            studyDto.setPhotoFile(file);
        }

        return studies;
    }

    @Override
    public StudyDto findById(Long id) {
        StudyBoard studyBoard = studyBoardRepository.findById(id).get();
        StudyDto studyDto = modelMapper.map(studyBoard, StudyDto.class);

        FileSystemResource file = FileManager.getFile(studyDto.getPhotoPath());

        studyDto.setPhotoFile(file);

        return studyDto;
    }

    @Override
    public void deleteById(Long id) {
        studyBoardRepository.deleteById(id);
    }
}
