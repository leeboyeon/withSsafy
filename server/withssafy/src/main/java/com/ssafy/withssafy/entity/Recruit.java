package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_recruit")
@Getter
@NoArgsConstructor
public class Recruit {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String title;

    @Column(length = 10)
    private String startDate;

    @Column(length = 10)
    private String endDate;

    @Column(length = 500)
    private String content;

    @Column(length = 50)
    private String career;

    @Column(length = 50)
    private String education;

    @Builder
    public Recruit(String name, String title, String startDate, String endDate, String content, String career, String education) {
        this.name = name;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.content = content;
        this.career = career;
        this.education = education;
    }
}
