package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.lang.reflect.Member;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = "SELECT * FROM tbl_user WHERE u_id=:u_id", nativeQuery = true)
    User findByUid(String u_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_user SET password=:password WHERE id=:id", nativeQuery = true)
    void updatePasswordById(Long id, String password);

    @Query(value = "SELECT * FROM tbl_user WHERE u_id=:u_id AND password=:password", nativeQuery = true)
    User login(String u_id, String password);
}
