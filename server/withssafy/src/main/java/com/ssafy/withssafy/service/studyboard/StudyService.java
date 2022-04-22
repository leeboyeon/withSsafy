package com.ssafy.withssafy.service.studyboard;

import com.ssafy.withssafy.dto.studyboard.StudyBoardRequest;
import com.ssafy.withssafy.dto.studyboard.StudyBoardResponse;
import com.ssafy.withssafy.entity.StudyBoard;
import com.ssafy.withssafy.errorcode.ErrorCode;
import com.ssafy.withssafy.exception.InvalidRequestException;
import com.ssafy.withssafy.repository.StudyBoardRepository;
import com.ssafy.withssafy.util.FileManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyBoardRepository studyBoardRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public void addStudyBoard(StudyBoardRequest studyDto) {
        StudyBoard studyBoard = modelMapper.map(studyDto, StudyBoard.class);

        studyBoardRepository.save(studyBoard);
    }

    @Transactional
    public void modifyStudyBoard(StudyBoardRequest studyBoardRequest, Long id) {
        Optional<StudyBoard> studyBoard = studyBoardRepository.findById(id);
        if (studyBoard.isPresent()) {
            studyBoard.get().updateStudyBoard(studyBoardRequest);
        } else {
            throw new InvalidRequestException(ErrorCode.INVALID_REQUEST);
        }
    }

    public List<StudyBoardResponse> getStudyBoards() {
        List<StudyBoard> studyBoards = studyBoardRepository.findAll();

        List<StudyBoardResponse> studyBoardResponses = studyBoards.stream().map(studyBoard -> modelMapper.map(studyBoard, StudyBoardResponse.class))
                .collect(Collectors.toList());

        for (StudyBoardResponse studyBoardResponse : studyBoardResponses) {
            FileSystemResource file = FileManager.getFile(studyBoardResponse.getPhotoPath());
            studyBoardResponse.setPhotoFile(file);
        }

        return studyBoardResponses;
    }

    public StudyBoardResponse getStudyBoardById(Long id) {
        Optional<StudyBoard> studyBoard = studyBoardRepository.findById(id);

        if (studyBoard.isPresent()) {
            StudyBoardResponse studyBoardResponse = modelMapper.map(studyBoard.get(), StudyBoardResponse.class);

            FileSystemResource file = FileManager.getFile(studyBoardResponse.getPhotoPath());
            studyBoardResponse.setPhotoFile(file);

            return studyBoardResponse;
        } else {
            return null;
        }

    }

    @Transactional
    public void removeStudyBoardById(Long id) {
        studyBoardRepository.deleteById(id);
    }
}
