package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tbl_schedule")
@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;



    @Column(length = 25)
    private String end_date;

    @Column(length = 50)
    private String title;

    @Column(length = 250)
    private String memo;

    @Column(length = 25)
    private String start_date;
    
    @Builder
    public Schedule(User user, String end_date, String title, String memo, String start_date){
        this.user = user;
        this.end_date = end_date;
        this.title = title;
        this.memo = memo;
        this.start_date = start_date;
    }
}
