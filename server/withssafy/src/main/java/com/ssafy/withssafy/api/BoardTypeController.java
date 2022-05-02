package com.ssafy.withssafy.api;

import com.ssafy.withssafy.service.board.BoardTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board-types")
public class BoardTypeController {

    private final BoardTypeService boardTypeService;

    @GetMapping()
    public ResponseEntity<?> getBoardTypes() {
        return new ResponseEntity<>(boardTypeService.getBoardTypes(), HttpStatus.OK);
    }
}
