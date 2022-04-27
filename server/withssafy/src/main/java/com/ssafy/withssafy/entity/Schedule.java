package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_schedule")
@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classRoom_id")
    private ClassRoom classRoom;

    @Column
    private LocalDateTime endDate;

    @Column(length = 50)
    private String title;

    @Column(length = 250)
    private String memo;

    @Column(length = 25)
    private LocalDateTime startDate;
    
    @Builder
    public Schedule(User user, LocalDateTime endDate, String title, String memo, LocalDateTime startDate){
        this.user = user;
        this.endDate = endDate;
        this.title = title;
        this.memo = memo;
        this.startDate = startDate;
    }
}
