package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.lang.reflect.Member;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = "SELECT * FROM tbl_user WHERE u_id=:u_id", nativeQuery = true)
    User findByUid(String u_id);

    @Query(value = "DELETE FROM tbl_user WHERE u_id=:u_id", nativeQuery = true)
    boolean deleteByUid(String u_id);

    @Query(value = "UPDATE tbl_user SET password=:password WHERE email=:email", nativeQuery = true)
    User updatePasswordByEmail(String email, String password);
}
