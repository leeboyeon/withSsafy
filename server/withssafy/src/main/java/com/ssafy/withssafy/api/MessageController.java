package com.ssafy.withssafy.api;

import com.ssafy.withssafy.dto.message.MessageDto;
import com.ssafy.withssafy.entity.Message;
import com.ssafy.withssafy.service.message.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Message(쪽지) 관리 API
 * @author Jueun
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
@Api(tags = "메세지(쪽지) API")
public class MessageController {
    @Autowired
    MessageService messageService;

    @PostMapping
    @ApiOperation(value = "특정 상대에게 메세지를 전송한다.")
    public ResponseEntity<MessageDto> sendMessage(@RequestBody MessageDto messageDto){
        return new ResponseEntity<>(messageService.sendMessage(messageDto), HttpStatus.OK);
    }

    @GetMapping("/receive")
    @ApiOperation(value = "특정 유저가 받은 모든 메세지를 가져온다.")
    public ResponseEntity<List<MessageDto>> findReceiveMessageByUid(@RequestParam("유저 id")Long id){
        return new ResponseEntity<>(messageService.findReceiveMessageByUid(id), HttpStatus.OK);
    }

    @GetMapping("/send")
    @ApiOperation(value = "특정 유저가 보낸 모든 메세지를 가져온다.")
    public ResponseEntity<List<MessageDto>> findSendMessageByUid(@RequestParam("유저 id")Long id){
        return new ResponseEntity<>(messageService.findSendMessageByUid(id), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "모든 메세지를 가져온다.")
    public ResponseEntity<List<MessageDto>> findAll(){
        return new ResponseEntity<>(messageService.findAll(), HttpStatus.OK);
    }
}
