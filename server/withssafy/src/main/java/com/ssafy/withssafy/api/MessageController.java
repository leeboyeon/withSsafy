package com.ssafy.withssafy.api;

import com.ssafy.withssafy.entity.Message;
import com.ssafy.withssafy.service.message.MessageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageService messageService;

    @PostMapping
    @ApiOperation(value = "특정 상대에게 메세지를 전송한다.")
    public ResponseEntity<Message> sendMessage(@RequestParam("보내는 사람")Long u_from, @RequestParam("받는 사람")Long u_to, @RequestParam("내용")String content, @RequestParam("보낸 시간")String send_dt){
        return new ResponseEntity<>(messageService.sendMessage(u_from, u_to, content, send_dt), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "특정 유저의 메세지를 모두 가져온다.")
    public ResponseEntity<List<Message>> getMessagesById(@RequestParam("유저 id")Long id){
        return new ResponseEntity<>(messageService.findById(id), HttpStatus.OK);
    }
}
