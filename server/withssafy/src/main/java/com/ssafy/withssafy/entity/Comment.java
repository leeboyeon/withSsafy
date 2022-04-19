package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "tbl_comment")
@Getter

@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private int parent;

    @Column
    private String content;

    @Builder
    public Comment(Board board, User user, int parent, String content){
        this.board = board;
        this.user = user;
        this.parent = parent;
        this.content = content;
    }
}
