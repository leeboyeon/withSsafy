package com.ssafy.withssafy.api;

import com.ssafy.withssafy.dto.studyboard.StudyBoardRequest;
import com.ssafy.withssafy.dto.studyboard.StudyBoardResponse;
import com.ssafy.withssafy.dto.studyboard.StudyMemberRequest;
import com.ssafy.withssafy.service.studyboard.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> addStudyBoard(@RequestPart(value = "data") StudyBoardRequest studyBoardRequest, @RequestPart(value = "file") MultipartFile file) {
        studyService.addStudyBoard(studyBoardRequest, file);
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

    @PostMapping("/{id}/join")
    public ResponseEntity<?> joinStudy(@PathVariable("id") Long id, @RequestBody StudyMemberRequest studyMemberRequest) {
        studyService.joinStudy(id, studyMemberRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}/leave")
    public ResponseEntity<?> leaveStudy(@PathVariable("id") Long id, @RequestBody StudyMemberRequest studyMemberRequest) {
        studyService.leaveStudy(id, studyMemberRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
