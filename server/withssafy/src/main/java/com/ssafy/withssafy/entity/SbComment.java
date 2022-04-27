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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Builder
    public SbComment(Long id, StudyBoard studyBoard, User user, Long parent, String content){
        this.id = id;
        this.studyBoard = studyBoard;
        this.user = user;
        this.parent = parent;
        this.content = content;
    }
}