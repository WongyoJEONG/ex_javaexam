package net.datasa.ex_javaexam.controller;

import java.util.List;
import net.datasa.ex_javaexam.dto.ExamResultDto;
import net.datasa.ex_javaexam.entity.ExamResult;
import net.datasa.ex_javaexam.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor // 의존성 주입
public class ExamController {

    private final ExamService examService;

    // 1. 시험 응시 화면 이동
    @GetMapping("/exam")
    public String exam(Model model) {
        // 빈 객체를 보내야 th:object="${examDto}"가 바인딩 할 수 있음
        model.addAttribute("examDto", new ExamResultDto());
        return "exam_form";
    }

    // 2. 시험 제출 처리
    @PostMapping("/exam/submit")
    public String submit(@ModelAttribute ExamResultDto examDto, Model model) {
        try {
            // 서비스에 채점 및 저장 요청
            examService.submitExam(examDto);

            // 성공하면 결과 조회 페이지로 리다이렉트
            return "redirect:/result";

        } catch (IllegalStateException e) {
            // 예외 발생 (이미 응시한 이메일 등)
            // 요구사항: 에러 메시지를 보여주고, 입력값은 그대로 유지되어야 함

            model.addAttribute("msg", e.getMessage()); // 서비스에서 던진 에러 메시지("이미 응시한 이메일입니다...")
            model.addAttribute("examDto", examDto);    // 사용자가 입력했던 내용 다시 전달

            return "/exam_form"; // 다시 시험 화면으로 복귀
        }
    }

    // 3. 결과 목록 조회 (정렬 기능 포함)
    @GetMapping("/result")
    public String result(@RequestParam(required = false, defaultValue = "date") String sort, Model model) {
        Sort sortObj;

        // 성적순(내림차순), 이름순(오름차순), 응시일순(오름차순)
        if ("score".equals(sort)) {
            sortObj = Sort.by(Sort.Direction.DESC, "score");
        } else if ("name".equals(sort)) {
            sortObj = Sort.by(Sort.Direction.ASC, "name");
        } else {
            sortObj = Sort.by(Sort.Direction.ASC, "examDate"); // 기본값: 날짜순
        }

        List<ExamResult> list = examService.findAll(sortObj);
        model.addAttribute("list", list);
        return "result";
    }

    // 4. 삭제 폼 이동 (비밀번호 입력 화면)
    @GetMapping("/delete/{id}")
    public String deleteForm(@PathVariable Long id, Model model) {
        // 삭제할 대상을 조회해서 화면에 보여줌 (이름, 이메일, 날짜 확인용)
        ExamResult result = examService.findById(id);
        model.addAttribute("exam", result);
        return "delete_form";
    }

    // 5. 삭제 처리
    @PostMapping("/delete")
    public String delete(@RequestParam Long id, @RequestParam String password, Model model) {
        boolean isDeleted = examService.deleteResult(id, password);

        if (isDeleted) {
            return "redirect:/result"; // 삭제 성공 시 목록으로
        } else {
            // 비밀번호 틀림 -> 다시 삭제 화면으로 + 에러 메시지
            ExamResult result = examService.findById(id);
            model.addAttribute("exam", result);
            model.addAttribute("msg", "비밀번호가 일치하지 않습니다.");
            return "delete_form";
        }
    }
}