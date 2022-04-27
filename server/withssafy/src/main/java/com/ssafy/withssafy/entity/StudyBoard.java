package com.ssafy.withssafy.entity;


import com.ssafy.withssafy.dto.studyboard.StudyBoardRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_study_board")
@Getter
@NoArgsConstructor
public class StudyBoard {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(length = 500, name = "photo_path")
    private String photoPath;

    @Column(name = "sb_limit")
    private int sbLimit;

    @Column(length = 500, name = "write_datetime")
    private String writeDateTime;

    @Column(name = "is_outing")
    private byte isOuting;

    public void updateStudyBoard(StudyBoardRequest studyBoardRequest){
        this.title = studyBoardRequest.getTitle();
        this.content = studyBoardRequest.getContent();
        this.category = studyBoardRequest.getCategory();
        this.area = studyBoardRequest.getArea();
        this.photoPath = studyBoardRequest.getPhotoPath();
        this.sbLimit = studyBoardRequest.getSbLimit();
        this.isOuting = studyBoardRequest.getIsOuting();
    }


    @Builder
    public StudyBoard(Long id, User user, String title, String content, String area, String photoPath, String category, int sbLimit, String writeDateTime, byte isOuting){
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.area = area;
        this.photoPath = photoPath;
        this.category = category;
        this.sbLimit = sbLimit;
        this.writeDateTime = writeDateTime;
        this.isOuting = isOuting;
    }
}