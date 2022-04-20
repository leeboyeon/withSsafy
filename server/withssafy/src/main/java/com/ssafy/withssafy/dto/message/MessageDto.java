package com.ssafy.withssafy.dto.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MessageDto {
    public Long id;
    public Long u_to_id;
    public Long u_from_id;
    public String content;
    public String send_dt;
}
