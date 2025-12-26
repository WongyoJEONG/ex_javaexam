package net.datasa.ex_javaexam.service;

import net.datasa.ex_javaexam.dto.ExamResultDto;
import net.datasa.ex_javaexam.entity.CorrectAnswer;
import net.datasa.ex_javaexam.entity.ExamResult;
import net.datasa.ex_javaexam.repository.CorrectAnswerRepository;
import net.datasa.ex_javaexam.repository.ExamResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamResultRepository examResultRepository;
    private final CorrectAnswerRepository correctAnswerRepository;


    public void submitExam(ExamResultDto dto) {

        // 1. 중복 응시 체크 (요구사항: 이메일이 이미 존재하면 저장하지 않고 되돌아옴)
        if (examResultRepository.existsByEmail(dto.getEmail())) {
            // 요구사항에 명시된 에러 문구 포맷: "이미 응시한 이메일입니다. 이메일주소"
            throw new IllegalStateException("이미 응시한 이메일입니다. " + dto.getEmail());
        }

        // 2. 정답지 가져오기 (DB 조회)
        // 문제 번호 순서대로 가져오기 위해 Sort 사용
        List<CorrectAnswer> correctAnswers = correctAnswerRepository.findAll(Sort.by("questionNum"));

        // 3. 채점 (1문항당 20점)
        int score = 0;

        // DB에서 가져온 정답과 DTO의 입력값 비교
        if (isCorrect(dto.getAns1(), correctAnswers.get(0))) score += 20;
        if (isCorrect(dto.getAns2(), correctAnswers.get(1))) score += 20;
        if (isCorrect(dto.getAns3(), correctAnswers.get(2))) score += 20;
        if (isCorrect(dto.getAns4(), correctAnswers.get(3))) score += 20;
        if (isCorrect(dto.getAns5(), correctAnswers.get(4))) score += 20;

        // 4. 결과 저장
        ExamResult examResult = new ExamResult();
        examResult.setName(dto.getName());
        examResult.setEmail(dto.getEmail());
        examResult.setPassword(dto.getPassword());
        examResult.setAns1(dto.getAns1());
        examResult.setAns2(dto.getAns2());
        examResult.setAns3(dto.getAns3());
        examResult.setAns4(dto.getAns4());
        examResult.setAns5(dto.getAns5());
        examResult.setScore(score); // 계산된 점수 저장

        examResultRepository.save(examResult);
    }

    // 정답 비교 편의 메서드
    private boolean isCorrect(String userAnswer, CorrectAnswer correctAnswer) {
        // null 체크 및 공백 제거 후 비교 (주관식 대소문자 등 조건이 없다면 equals 사용)
        return userAnswer != null && userAnswer.trim().equals(correctAnswer.getAnswer());
    }

    // 목록 조회
    public List<ExamResult> findAll(Sort sort) {
        return examResultRepository.findAll(sort);
    }

    // 단건 조회 (삭제 폼용)
    public ExamResult findById(Long id) {
        return examResultRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
    }

    // 삭제 처리 (비밀번호 확인 로직 포함)
    public boolean deleteResult(Long id, String password) {
        ExamResult result = findById(id);

        // 비밀번호 비교
        if (result.getPassword().equals(password)) {
            examResultRepository.delete(result);
            return true; // 삭제 성공
        }
        return false; // 비밀번호 불일치
    }
}