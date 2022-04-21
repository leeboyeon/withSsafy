package com.ssafy.withssafy.dto.message;

import com.ssafy.withssafy.entity.User;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MessageDto {
    public Long id;
    public Long u_toId;
    public Long u_fromId;
    public String content;
    public String send_dt;
}
