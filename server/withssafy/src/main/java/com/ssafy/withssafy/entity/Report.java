package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_report")
@Getter
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String content;

    @Builder
    public Report(Long id, Board board , Comment comment, User user, String content){
        this.id = id;
        this.board = board;
        this.comment = comment;
        this.user = user;
        this.content = content;
    }

    public void setReport(Board board, Comment comment, User user){
        this.board = board;
        this.comment = comment;
        this.user = user;
    }
}
