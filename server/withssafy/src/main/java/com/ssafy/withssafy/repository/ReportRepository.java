package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.Report;
import com.ssafy.withssafy.entity.SbComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Long> {

}
