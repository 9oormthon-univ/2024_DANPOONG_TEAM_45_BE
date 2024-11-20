package com.codingland.apiservice.quiz.presentation;

import com.codingland.common.common.ApplicationResponse;
import com.codingland.domain.quiz.dto.RequestCreateQuizDto;
import com.codingland.domain.quiz.dto.RequestEditQuizDto;
import com.codingland.domain.quiz.dto.ResponseQuizDto;
import com.codingland.domain.quiz.dto.ResponseQuizListDto;
import com.codingland.domain.quiz.service.QuizService;
import com.codingland.domain.user.entity.User;
import com.codingland.security.annotation.UserResolver;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/quiz")
@RequiredArgsConstructor
@Tag(name = "[Quiz] 문제 API", description = "문제 생성, 문제 조회, 문제 수정, 문제 삭제")
public class QuizController {
    private final QuizService quizService;

    @PostMapping
    @Operation(summary = "퀴즈 생성", description = """
            (관리자용) 퀴즈를 생성할 때 사용합니다. Chapter를 id로 받으나,  아직 Chapter가 없을 경우 null로 두어도 무관합니다.
            level은 difficulty에 등록된 level만 사용할 수 있습니다.
            """)
    public ApplicationResponse<Void> createQuiz(
            @RequestBody RequestCreateQuizDto requestCreateQuizDto) {
        quizService.createQuiz(requestCreateQuizDto);
        return ApplicationResponse.ok(null);
    }

    @GetMapping("/{quiz_id}")
    @Operation(summary = "퀴즈 단 건 조회", description = """
            (사용자용) 퀴즈를 단 건 조회할 때 사용 됩니다.
            """)
    public ApplicationResponse<ResponseQuizDto> getQuiz(
            @PathVariable Long quiz_id,
            @UserResolver User user) {
        ResponseQuizDto result = quizService.findByOne(quiz_id, user.getUserId());
        return ApplicationResponse.ok(result);
    }

    @GetMapping("/all")
    @Operation(summary = "등록된 퀴즈를 모두 조회", description = """
            (관리자용) 등록된 퀴즈 목록을 모두 보고 싶을때 사용합니다.
            """)
    public ApplicationResponse<ResponseQuizListDto> getAllQuizzes() {
        ResponseQuizListDto result = quizService.findByMany();
        return ApplicationResponse.ok(result);
    }

    @PatchMapping
    @Operation(summary = "퀴즈 수정", description = """
            (관리자용) 특정 Quiz를 수정하거나 Chapter와의 연관관계를 수정하고 싶을때 사용합니다.
            """)
    public ApplicationResponse<Void> editQuiz(
            @RequestBody RequestEditQuizDto requestEditQuizDto) {
        quizService.editQuiz(requestEditQuizDto);
        return ApplicationResponse.ok(null);
    }

    @DeleteMapping("/{quiz_id}")
    @Operation(summary = "퀴즈 삭제", description = """
            (관리자용) 퀴즈를 삭제할 때 사용합니다.
            """)
    public ApplicationResponse<Void> deleteQuiz(
            @PathVariable Long quiz_id) {
        quizService.deleteQuiz(quiz_id);
        return ApplicationResponse.ok(null);
    }
}
