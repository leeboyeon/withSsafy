package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "tbl_sb_comment")
@Getter
@NoArgsConstructor
public class SbComment {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sb_id")
    private StudyBoard studyBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Long parent;

    @Column(length = 500)
    private String content;

    @Column
    private String write_dt;

    @Builder
    public SbComment(Long id, StudyBoard studyBoard, User user, Long parent, String content, String write_dt){
        this.id = id;
        this.studyBoard = studyBoard;
        this.user = user;
        this.parent = parent;
        this.content = content;
        this.write_dt = write_dt;
    }
}