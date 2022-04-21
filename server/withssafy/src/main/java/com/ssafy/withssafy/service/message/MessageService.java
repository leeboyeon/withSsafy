package com.ssafy.withssafy.service.message;

import com.ssafy.withssafy.dto.message.MessageDto;
import com.ssafy.withssafy.entity.Message;
import com.ssafy.withssafy.entity.User;

import java.util.List;

public interface MessageService {
    public MessageDto sendMessage(MessageDto messageDto);
    public List<MessageDto> findReceiveMessageByUid(Long u_id);
    public List<MessageDto> findAll();
    public List<MessageDto> findSendMessageByUid(Long id);
}
