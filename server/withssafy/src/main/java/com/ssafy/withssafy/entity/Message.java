package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_message")
@Getter
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
    @JoinColumn
    private User u_to;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
    @JoinColumn
    private User u_from;

    @Column
    private String content;

    @Column
    private String send_dt;

    @Builder
    public Message(User u_from, User u_to, String content, String send_dt){
        this.u_from = u_from;
        this.u_to = u_to;
        this.content = content;
        this.send_dt = send_dt;
    }
}