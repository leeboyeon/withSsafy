package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tbl_board")
@Getter
@NoArgsConstructor

public class Board {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private BoardType type;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String photo_path;

    @Column
    private String write_dt;

    @Builder
    public Board(User user, BoardType type, String title, String content, String photo_path, String write_dt){
        this.user = user;
        this.type = type;
        this.title = title;
        this.content = content;
        this.photo_path = photo_path;
        this.write_dt = write_dt;
    }
}
