package net.datasa.ex_javaexam.repository;

import net.datasa.ex_javaexam.entity.CorrectAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorrectAnswerRepository extends JpaRepository<CorrectAnswer, Integer> {
}