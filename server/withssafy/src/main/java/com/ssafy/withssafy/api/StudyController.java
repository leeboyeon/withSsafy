package com.ssafy.withssafy.api;

import com.ssafy.withssafy.dto.study.StudyDto;
import com.ssafy.withssafy.service.study.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studies")
public class StudyController {

    private final StudyService studyService;

    @GetMapping()
    public ResponseEntity<List<StudyDto>> findAll() {
        List<StudyDto> studies = studyService.findAll();
        return new ResponseEntity<List<StudyDto>>(studies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudyDto> findById(@PathVariable("id") Long id) {
        StudyDto study = studyService.findById(id);
        return new ResponseEntity<StudyDto>(study, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Object> save(@RequestBody StudyDto studyDto) {
        studyService.save(studyDto);
        return new ResponseEntity<Object>(null, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody StudyDto studyDto, @PathVariable("id") Long id) {
        studyService.save(studyDto, id);
        return new ResponseEntity<Object>(null, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") Long id) {
        studyService.deleteById(id);
        return new ResponseEntity<Object>(null, HttpStatus.OK);
    }

}
