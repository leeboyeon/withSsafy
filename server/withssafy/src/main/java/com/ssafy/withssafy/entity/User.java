package com.ssafy.withssafy.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_user")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Size(max= 25)
    @Column(length = 25)
    private String email;

    @Size(max = 255)
    private String password;

    @Size(max = 50)
    @Column(length = 50)
    private String nickname;

    private int state;

    @Size(max = 255)
    private String device_token;

    @Size(max = 255)
    private String profileImage;

    private int auth;

    private int s_id;

    @Size(max = 20)
    @Column(length = 20)
    private String s_area;

    private int s_gen;


    public void encodePassword(PasswordEncoder passwordEncoder) { password = passwordEncoder.encode(password);}
}
