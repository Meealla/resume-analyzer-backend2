package webapp.deepseekservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import webapp.deepseek.application.service.DeepSeekServiceImpl;
import webapp.deepseek.domain.service.DeepSeekService;
import webapp.deepseek.infrastructure.client.OpenRouterApiClient;
import webapp.deepseek.infrastructure.client.OpenRouterRequest;
import webapp.deepseek.infrastructure.client.OpenRouterResponse;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DeepSeekServiceTest {

    @Mock
    private OpenRouterApiClient openRouterApiClient;

    @InjectMocks
    private DeepSeekServiceImpl deepSeekService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест на проверку успешного анализа резюме и возвращает корректный результат анализа.
     *
     * @throws Exception Исключение, возникающее при выполнении запроса.
     */
    @Test
    @DisplayName("Проверяет на корректный результат анализа")
    void testAnalyzeResume_Ok() {

        String resumeText = "Опыт работы: 5 лет. Навыки: Java, Spring.";
        String jobDescription = "Требования: опыт работы с Java, Spring.";

        OpenRouterResponse response = new OpenRouterResponse();
        OpenRouterResponse.Choice choice = new OpenRouterResponse.Choice();
        OpenRouterResponse.Message message = new OpenRouterResponse.Message();

        message.setContent("Оценка: 90");
        choice.setMessage(message);
        response.setChoices(Collections.singletonList(choice));

        when(openRouterApiClient.generateCompletion(any(), any(), any())).thenReturn(response);

        String result = deepSeekService.analyzeResume(resumeText, jobDescription);

        assertEquals("Оценка: 90", result);
        verify(openRouterApiClient, times(1)).generateCompletion(any(), any(), any());
    }

    /**
     * Проверка, что корректно обрабатывается исключение и возвращает сообщение об ошибке
     *
     * @throws Exception Исключение, возникающее при выполнении запроса.
     */
    @Test
    @DisplayName("Проверка, что корректно обрабатывается исключение и возвращает сообщение об ошибке")
    void testAnalyzeResume_Error() {
        String resumeText = "Опыт работы: 5 лет. Навыки: Java, Spring.";
        String jobDescription = "Требования: опыт работы с Java, Spring.";

        when(openRouterApiClient.generateCompletion(any(), any(), any())).thenThrow(new RuntimeException("API error"));

        String result = deepSeekService.analyzeResume(resumeText, jobDescription);

        assertEquals("Ошибка анализа: API error", result);
    }

    /**
     * Тест на проверку валидности текста резюме или описания вакансии.
     *
     * @throws Exception Исключение, возникающее при выполнении запроса.
     */
    @Test
    @DisplayName("Проверяет, что возвращается true для текста, содержащего ключевые слова.")
    void testIsValidResume_Valid() {
        String text = "Опыт работы: 5 лет. Навыки: Java, Spring.";
        assertTrue(deepSeekService.isValidResumeOrJobDescription(text));
    }

    /**
     * Тест на проверку невалидности текста резюме или описания вакансии.
     *
     * @throws Exception Исключение, возникающее при выполнении запроса.
     */
    @Test
    @DisplayName("Проверяет, что возвращается false для текста, не содержащего ключевые слова.")
    void testIsValidResume_Invalid() {
        String text = "Текст без ключевых слов.";
        assertFalse(deepSeekService.isValidResumeOrJobDescription(text));
    }
}