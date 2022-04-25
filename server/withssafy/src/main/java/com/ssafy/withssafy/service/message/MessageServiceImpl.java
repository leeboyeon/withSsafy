package com.ssafy.withssafy.service.message;

import com.ssafy.withssafy.dto.message.MessageDto;
import com.ssafy.withssafy.entity.Message;
import com.ssafy.withssafy.repository.MessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public MessageDto sendMessage(MessageDto messageDto) {
        Message message = modelMapper.map(messageDto, Message.class);
        messageRepository.save(message);
        return messageDto;
    }

    @Override
    public List<MessageDto> findReceiveMessageByUid(Long id) {
        List<Message> messages = messageRepository.findReceiveMessageByUid(id);
        return messages.stream().map(message -> modelMapper.map(message, MessageDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<MessageDto> findAll() {
        List<Message> messages = messageRepository.findAll();
        return messages.stream().map(message -> modelMapper.map(message, MessageDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<MessageDto> findSendMessageByUid(Long id) {
        List<Message> messages = messageRepository.findSendMessageByUid(id);
        return messages.stream().map(message -> modelMapper.map(message, MessageDto.class)).collect(Collectors.toList());
    }
}
