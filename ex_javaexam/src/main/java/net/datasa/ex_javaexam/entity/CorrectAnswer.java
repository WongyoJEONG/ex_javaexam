package net.datasa.ex_javaexam.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class CorrectAnswer {
    @Id
    private Integer questionNum; // 문제 번호 (PK)
    private String answer;       // 정답
}