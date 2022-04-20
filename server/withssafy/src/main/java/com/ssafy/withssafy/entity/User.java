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

    @Size(max= 25)
    @Column(length = 25, unique = true)
    private String u_id;

    @Size(max = 255)
    private String password;

    private int state;

    @Size(max = 255)
    private String device_token;

    private int auth;

    private int s_id;

    @Size(max = 20)
    @Column(length = 20)
    private String s_area;

    private int s_gen;

    @Builder
    public User(Long id, String u_id, String password, int state,
                String device_token, int auth, int s_id, String s_area, int s_gen){
        this.id = id;
        this.u_id = u_id;
        this.password = password;
        this.state = state;
        this.device_token = device_token;
        this.auth = auth;
        this.s_id = s_id;
        this.s_area = s_area;
        this.s_gen = s_gen;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) { password = passwordEncoder.encode(password);}
}
