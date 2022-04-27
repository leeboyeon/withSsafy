package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.RecruitLikeManagement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitLikeManagementRepository extends JpaRepository<RecruitLikeManagement,Long> {
    RecruitLikeManagement findByRecruitIdAndUserId(Long recruitId, Long userId);
}
