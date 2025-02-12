package webapp.deepseek.infrastructure.controller;

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
@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final DeepSeekService deepSeekService;

    @Autowired
    public ResumeController(DeepSeekService deepSeekService) {
        this.deepSeekService = deepSeekService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeResume(
            @RequestParam("resumeText") String resumeText,
            @RequestParam("jobDescription") String jobDescription) {

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