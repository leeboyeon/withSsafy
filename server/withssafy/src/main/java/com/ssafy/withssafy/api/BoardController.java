package com.ssafy.withssafy.api;

import com.ssafy.withssafy.dto.board.BoardRequest;
import com.ssafy.withssafy.dto.board.BoardResponse;
import com.ssafy.withssafy.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping()
    public ResponseEntity<List<BoardResponse>> getBoards() {
        return new ResponseEntity<>(boardService.getBoards(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoardById(@PathVariable("id") Long id) {
        return new ResponseEntity<BoardResponse>(boardService.getBoardById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> addBoard(@RequestBody BoardRequest boardRequest) {
        boardService.addBoard(boardRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyBoardById(@RequestBody BoardRequest boardRequest, @PathVariable("id") Long id) {
        boardService.modifyBoardById(boardRequest, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeBoardById(@PathVariable("id") Long id) {
        boardService.removeBoardById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
