package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_user")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @Size(max = 25)
    @Column(length = 25, unique = true, name = "user_id")
    private String userId;

    @Column(name = "student_id")
    private String studentId;

    @Size(max = 255)
    private String password;

    @Column
    private String name;

    @Column
    private int state;

    @Size(max = 255)
    @Column(name = "device_token")
    private String deviceToken;

    @Builder
    public User(Long id, Classroom classroom, String userId, String studentId, String password, String name, int state,
                String deviceToken) {
        this.id = id;
        this.classroom = classroom;
        this.userId = userId;
        this.studentId = studentId;
        this.password = password;
        this.name = name;
        this.state = state;
        this.deviceToken = deviceToken;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }
}
