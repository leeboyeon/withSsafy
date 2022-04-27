package com.ssafy.withssafy.api;

import com.ssafy.withssafy.dto.comment.CommentDto;
import com.ssafy.withssafy.service.comment.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Comment 관리 API
 * @author Jueun
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@Api(tags = "Board  댓글 API")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping
    @ApiOperation(value = "모든 메세지를 조회합니다.")
    public ResponseEntity<List<CommentDto>> findAll(){
        return new ResponseEntity<>(commentService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation(value = "해당 아이디를 가진 댓글을 삭제합니다.")
    public ResponseEntity<Boolean> delete(@RequestParam("댓글 ID")Long id){
        return new ResponseEntity<>(commentService.delete(id), HttpStatus.OK);
    }

    @PutMapping
    @ApiOperation(value = "댓글의 내용을 수정합니다. *(ID, Content 필수)")
    public ResponseEntity<CommentDto> update(@RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.update(commentDto),HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "댓글을 입력합니다.")
    public ResponseEntity<CommentDto> insert(@RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.insert(commentDto), HttpStatus.OK);
    }

    @GetMapping("{boardId}")
    @ApiOperation(value = "게시판에 해당하는 댓글을 조회합니다.")
    public ResponseEntity<List<CommentDto>> findByBoardId(@PathVariable Long boardId){
        return new ResponseEntity<>(commentService.findByBoardId(boardId), HttpStatus.OK);
    }

    @GetMapping("user/{userId}")
    @ApiOperation(value = "사용자가 남긴 댓글을 조회합니다. *(URL 잘 보고 할 것 comment/u/{userId})")
    public ResponseEntity<List<CommentDto>> findByUserId(@PathVariable Long userId){
        return new ResponseEntity<>(commentService.findByUserId(userId), HttpStatus.OK);
    }
}
