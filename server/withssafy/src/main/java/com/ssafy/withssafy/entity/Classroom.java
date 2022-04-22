package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_classroom")
@Getter
@NoArgsConstructor
public class Classroom {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private int generation;

    @Column
    private String area;

    @Column
    private String classroomDescription;

    @Builder
    public Classroom(int generation, String area, String classroomDescription){
        this.generation = generation;
        this.area = area;
        this.classroomDescription = classroomDescription;
    }
}
