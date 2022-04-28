package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_board_type")
@Getter
@NoArgsConstructor

public class BoardType {
    @Id @GeneratedValue
    private Long id;

    @Column
    private String type;

    @Builder
    public BoardType(Long id, String type){
        this.id = id;
        this.type = type;
    }
}
