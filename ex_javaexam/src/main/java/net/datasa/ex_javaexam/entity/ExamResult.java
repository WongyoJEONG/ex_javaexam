package net.datasa.ex_javaexam.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 입력을 위해 필요
public class ExamResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    private String ans1;
    private String ans2;
    private String ans3;
    private String ans4;
    private String ans5;

    private int score;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime examDate;
}