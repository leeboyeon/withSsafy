package com.ssafy.withssafy.api;

import com.ssafy.withssafy.dto.message.MessageDto;
import com.ssafy.withssafy.service.message.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
@Api(tags = "메세지(쪽지) API")
public class MessageController {
    @Autowired
    MessageService messageService;

    @PostMapping
    @ApiOperation(value = "특정 상대에게 메세지를 전송한다.")
    public ResponseEntity<?> sendMessage(@RequestBody MessageDto messageDto){
        messageService.sendMessage(messageDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/receive")
    @ApiOperation(value = "특정 유저가 받은 모든 메세지를 가져온다.")
    public ResponseEntity<List<MessageDto>> findReceiveMessageByUid(@RequestParam Long id){
        return new ResponseEntity<>(messageService.findReceiveMessageByUid(id), HttpStatus.OK);
    }

    @GetMapping("/send")
    @ApiOperation(value = "특정 유저가 보낸 모든 메세지를 가져온다.")
    public ResponseEntity<List<MessageDto>> findSendMessageByUid(@RequestParam Long id){
        return new ResponseEntity<>(messageService.findSendMessageByUid(id), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "모든 메세지를 가져온다.")
    public ResponseEntity<List<MessageDto>> findAll(){
        return new ResponseEntity<>(messageService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/list/{id}")
    @ApiOperation(value = "내 메세지 목록 조회(상대유저별 최근 메세지 목록)")
    public ResponseEntity<List<MessageDto>> findMyMessageList(@PathVariable("id") Long id){
        return new ResponseEntity<>(messageService.findList(id), HttpStatus.OK);
    }

    @GetMapping("/list")
    @ApiOperation(value = "상대와 쪽지 주고 받은 목록")
    public ResponseEntity<List<MessageDto>> findChat(@RequestParam Long toId, @RequestParam Long fromId){
        return new ResponseEntity<>(messageService.findChatList(toId,fromId), HttpStatus.OK);
    }

    @GetMapping("/study/list")
    @ApiOperation(value = "상대와 공통으로 가입된 스터디 목록")
    public ResponseEntity<List<Long>> findCommonStudy(@RequestParam Long id1, @RequestParam Long id2){
        return new ResponseEntity<>(messageService.findCommonStudy(id1,id2), HttpStatus.OK);
    }
}
