package com.ssafy.withssafy.api;

import com.ssafy.withssafy.dto.studyboard.StudyBoardRequest;
import com.ssafy.withssafy.dto.studyboard.StudyBoardResponse;
import com.ssafy.withssafy.service.studyboard.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study-boards")
public class StudyController {

    private final StudyService studyService;

    @GetMapping()
    public ResponseEntity<List<StudyBoardResponse>> getStudyBoards() {
        List<StudyBoardResponse> studyBoardResponses = studyService.getStudyBoards();
        return new ResponseEntity<>(studyBoardResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudyBoardResponse> getStudyBoardById(@PathVariable("id") Long id) {
        StudyBoardResponse studyBoardResponse = studyService.getStudyBoardById(id);
        return new ResponseEntity<>(studyBoardResponse, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> addStudyBoard(@RequestBody StudyBoardRequest studyDto) {
        studyService.addStudyBoard(studyDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyStudyBoard(@RequestBody StudyBoardRequest studyBoardRequest, @PathVariable("id") Long id) {
        studyService.modifyStudyBoard(studyBoardRequest, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeStudyBoardById(@PathVariable("id") Long id) {
        studyService.removeStudyBoardById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
