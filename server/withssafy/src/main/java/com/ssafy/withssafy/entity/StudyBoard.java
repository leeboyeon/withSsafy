package com.ssafy.withssafy.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tbl_study_board")
@Getter
@Setter
@NoArgsConstructor
public class StudyBoard {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 50)
    private String title;

    @Column(length = 500)
    private String content;

    @Column
    private String category;

    @Column
    private String area;

    @Column(length = 500)
    private String photo_path;

    @Column
    private int sb_limit;

    @Column(length = 500)
    private String write_dt;

    @Column
    private byte is_outing;

    @Builder
    public StudyBoard(Long id, User user, String title, String content, String area, String photo_path, String category, int sb_limit, String write_dt, byte is_outing){
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.area = area;
        this.photo_path = photo_path;
        this.category = category;
        this.sb_limit = sb_limit;
        this.write_dt = write_dt;
        this.is_outing = is_outing;
    }
}