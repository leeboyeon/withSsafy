package com.ssafy.withssafy.service.message;

import com.ssafy.withssafy.entity.Message;
import com.ssafy.withssafy.entity.User;

import java.util.List;

public interface MessageService {
    public Message sendMessage(Long u_from, Long u_to, String content, String send_dt);
    public List<Message> findById(Long u_id);
    public List<Message> findAll();
}
