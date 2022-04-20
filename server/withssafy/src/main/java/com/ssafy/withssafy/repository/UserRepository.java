package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.lang.reflect.Member;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT * FROM tbl_user WHERE u_id=:u_id")
    User findByUid(String u_id);

    @Query("DELETE FROM tbl_user WHERE u_id=:u_id")
    boolean deleteByUid(String u_id);

    @Query("UPDATE tbl_user SET password=:password WHERE email=:email")
    User updatePasswordByEmail(String email, String password);
}
