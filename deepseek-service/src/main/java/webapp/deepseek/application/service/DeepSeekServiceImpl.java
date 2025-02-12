package webapp.deepseek.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import webapp.deepseek.client.OpenRouterApiClient;
import webapp.deepseek.client.OpenRouterRequest;
import webapp.deepseek.client.OpenRouterResponse;
import webapp.deepseek.domain.ChatMessage;
import webapp.deepseek.domain.service.DeepSeekService;

import java.util.List;

/**
 * • Основной сервис для анализа резюме.
 * • Получает данные от пользователя, формирует запрос к AI, обрабатывает ответ
 */
@Service
@Slf4j
public class DeepSeekServiceImpl implements DeepSeekService {

    private final OpenRouterApiClient openRouterApiClient;

    @Value("${openrouter.api-key}")
    private String apiKey;

    @Value("${openrouter.model-name}")
    private String aiModel;

    public DeepSeekServiceImpl(OpenRouterApiClient openRouterApiClient) {
        this.openRouterApiClient = openRouterApiClient;
    }

    @Override
    public String analyzeResume(String resumeText, String jobDescription) {
        String prompt = createPrompt(resumeText, jobDescription);

        OpenRouterRequest request = createRequest(prompt);

        try {
            OpenRouterResponse response = openRouterApiClient.generateCompletion(
                    "Bearer " + apiKey,
                    aiModel,
                    request
            );
            if (response != null && !response.getChoices().isEmpty()) {
                return response.getChoices().getFirst().getMessage().getContent();
            }
            return "Не удалось проанализировать резюме.";
        } catch (Exception e) {
            log.error("API error: ", e);
            return "Ошибка анализа: " + e.getMessage();
        }
    }

    @Override
    public boolean isValidResumeOrJobDescription(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        String lowerCaseText = text.toLowerCase();

        return lowerCaseText.contains("опыт работы") ||
               lowerCaseText.contains("навыки") ||
               lowerCaseText.contains("образование") ||
               lowerCaseText.contains("должностные обязанности") ||
               lowerCaseText.contains("требования") ||
               lowerCaseText.contains("резюме") ||
               lowerCaseText.contains("вакансия");
    }

    private OpenRouterRequest createRequest(String prompt) {
        OpenRouterRequest request = new OpenRouterRequest();
        request.setModel(aiModel);
        request.setMessages(List.of(new ChatMessage("user", prompt)));
        return request;
    }

    private String createPrompt(String resumeText, String jobDescription) {
        return "Вы полезный помощник, который анализирует резюме на предмет соответствия должности. " +
               "Учитывая следующее резюме и описание должности, дайте оценку (0-100) " +
               "того, насколько хорошо резюме соответствует описанию должности, и объясните свои доводы. " +
               "Resume:\n" + resumeText + "\n\nJob Description:\n" + jobDescription;
    }
}
