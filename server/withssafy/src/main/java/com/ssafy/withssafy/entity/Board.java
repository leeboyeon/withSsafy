package com.ssafy.withssafy.entity;

import com.ssafy.withssafy.dto.board.BoardRequest;
import lombok.*;

import javax.persistence.*;

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

    @Column(name = "photo_path")
    private String photoPath;

    @Column(name = "write_dt")
    private String writeDateTime;

    public void updateBoard(BoardRequest boardRequest){
        title = boardRequest.getTitle();
        content = boardRequest.getContent();
        photoPath = boardRequest.getPhotoPath();
        writeDateTime = boardRequest.getWriteDateTime();
    }

    @Builder
    public Board(Long id, User user, BoardType type, String title, String content, String photoPath, String writeDateTime){
        this.id = id;
        this.user = user;
        this.type = type;
        this.title = title;
        this.content = content;
        this.photoPath = photoPath;
        this.writeDateTime = writeDateTime;
    }
}
