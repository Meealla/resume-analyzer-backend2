package webapp.deepseekservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ResumeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Тест на проверку успешного анализа резюме.
     *
     * @throws Exception Исключение, возникающее при выполнении запроса.
     */
    @Test
    @DisplayName("Проверка успешного анализа резюме - статус 200")
    public void testAnalyzeResume_Success() throws Exception {
        mockMvc.perform(post("/api/resume/analyze")
                        .param("resumeText", "Опыт работы: 5 лет. Навыки: Java, Spring.")
                        .param("jobDescription", "Требования: опыт работы с Java, Spring.")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Оценка: 90"));
    }

    /**
     * Проверка, что возвращается статус 400, если текст резюме или описания вакансии не содержит ключевых слов.
     *
     * @throws Exception Исключение, возникающее при выполнении запроса.
     */
    @Test
    @DisplayName("Проверка обработки невалидного запроса - статус 400")
    public void testAnalyzeResume_InvalidInput() throws Exception {
        mockMvc.perform(post("/api/resume/analyze")
                        .param("resumeText", "Просто текст")
                        .param("jobDescription", "Требования: опыт работы с Java, Spring.")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Текст резюме или описания вакансии не соответствует ожидаемой тематике."));
    }

    /**
     * Тест на проверку обработки ошибки сервера.
     * Проверка, что возвращается статус 500, если возникает внутренняя ошибка сервера.
     *
     * @throws Exception Исключение, возникающее при выполнении запроса.
     */
    @Test
    @DisplayName("Проверка обработки ошибки сервера - статус 500")
    public void testAnalyzeResume_ServerError() throws Exception {
        mockMvc.perform(post("/api/resume/analyze")
                        .param("resumeText", "Опыт работы: 5 лет. Навыки: Java, Spring.")
                        .param("jobDescription", "Требования: опыт работы с Java, Spring.")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$").value("Ошибка обработки запроса: API error")); // Предполагаемое сообщение об ошибке
    }
}