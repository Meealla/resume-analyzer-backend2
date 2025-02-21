package webapp.deepseek.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webapp.deepseek.domain.service.DeepSeekService;
/**
 * REST-контроллер для анализа резюме.
 * Предоставляет эндпоинты для обработки запросов, связанных с анализом резюме,
 * отвечает за получение данных от клиента,
 * передачу их в сервис и возврат результата анализа.
 */
@Tag(name = "Resume Analysis", description = "API для анализа и оценки резюме")
@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final DeepSeekService deepSeekService;

    @Autowired
    public ResumeController(DeepSeekService deepSeekService) {
        this.deepSeekService = deepSeekService;
    }

    @PostMapping("/analyze")
    @Operation(summary = "Анализ резюме", description = "Анализирует резюме на соответствие описанию вакансии")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный анализ резюме"),
            @ApiResponse(responseCode = "400", description = "Неверный запрос: текст резюме или описания вакансии не соответствует ожидаемой тематике"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<String> analyzeResume(
            @Parameter(description = "Текст резюме", required = true) @RequestParam("resumeText") String resumeText,
            @Parameter(description = "Описание вакансии", required = true) @RequestParam("jobDescription") String jobDescription) {

        if (!deepSeekService.isValidResumeOrJobDescription(resumeText) || !deepSeekService.isValidResumeOrJobDescription(jobDescription)) {
            return ResponseEntity.badRequest().body("Текст резюме или описания вакансии не соответствует ожидаемой тематике.");
        }

        try {
            String analysisResult = deepSeekService.analyzeResume(resumeText, jobDescription);
            return ResponseEntity.ok(analysisResult);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка обработки запроса: " + e.getMessage());
        }
    }
}