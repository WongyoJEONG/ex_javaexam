package net.datasa.ex_javaexam.repository;

import net.datasa.ex_javaexam.entity.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {
    // 이메일 존재 여부 확인 메서드 (JPA가 자동으로 쿼리 생성)
    boolean existsByEmail(String email);
}