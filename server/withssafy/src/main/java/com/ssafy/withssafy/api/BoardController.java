package com.ssafy.withssafy.api;

import com.ssafy.withssafy.dto.board.BoardDto;
import com.ssafy.withssafy.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/boards")
public class BoardController {
    @Autowired
    BoardService boardService;

    @GetMapping()
    public ResponseEntity<List<BoardDto>> findAll(){
        return new ResponseEntity<List<BoardDto>>(boardService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDto> findById(@PathVariable("id") Long id){
        return new ResponseEntity<BoardDto>(boardService.findById(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Object> save(@RequestBody BoardDto boardSave){
        boardService.save(boardSave);
        return new ResponseEntity<Object>(null, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody BoardDto boardSave){
        boardService.save(boardSave);
        return new ResponseEntity<Object>(null, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id){
        boardService.deleteById(id);
        return new ResponseEntity<Object>(null, HttpStatus.OK);
    }
}
