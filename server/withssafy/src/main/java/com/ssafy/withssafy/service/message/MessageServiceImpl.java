package com.ssafy.withssafy.service.message;

import com.ssafy.withssafy.entity.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{
    @Override
    public Message sendMessage(Long u_from, Long u_to, String content, String send_dt) {
        return null;
    }

    @Override
    public List<Message> findById(Long u_id) {
        return null;
    }

    @Override
    public List<Message> findAll() {
        return null;
    }
}
