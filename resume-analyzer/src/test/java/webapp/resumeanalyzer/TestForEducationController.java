package webapp.resumeanalyzer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import webapp.resumeanalyzer.domain.model.Education;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import webapp.resumeanalyzer.controller.EducationTestController;
import webapp.resumeanalyzer.domain.service.EducationService;

/**
 * Тестовый класс для проверки функциональности
 * {@link EducationTestController}.
 */
@SpringBootTest(classes = ResumeAnalyzerApplication.class)
@AutoConfigureMockMvc
public class TestForEducationController {

    /**
     * Сервис для работы с шаблонами.
     */
    @Mock
    private EducationService educationService;
    /**
     * Экземпляр контроллера
     * {@link EducationTestController}, в который внедряется
     * мокированный сервис.
     */
    @InjectMocks
    private EducationTestController educationTestController;

    /**
     * Объект для выполнения HTTP-запросов и проверки ответов контроллера.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Инициализация MockMvc перед выполнением каждого теста.
     */
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(educationTestController).build();
    }

    /**
     * Тест на проверку, что GET-запрос возвращает ошибку, если шаблон с указанным id не найден.
     *
     * @throws Exception Исключение, возникающее при выполнении запроса.
     */
    @Test
    @DisplayName("/educations/{id} возвращает ошибку,если шаблон не найден")
    public void testGetEducation() throws Exception {
        mockMvc.perform(get("/educations/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    /**
     * Тест на проверку, что POST-запрос создает новый шаблон и возвращает его с присвоенным ему
     * id.
     *
     * @throws Exception исключение, возникающее при выполнении запрос.
     */
    @Test
    @DisplayName("Проверка что при создании нового шаблона возвращается созданный шаблон")
    public void testCreateEducation() throws Exception {
        UUID generatedID = UUID.randomUUID();
        Education inputEducation = new Education(generatedID, "description1", "position1",
                "from_year1", "to_year1", "name1");
        Education savedEducation = new Education(generatedID, "description1", "position1",
                "from_year1", "to_year1", "name1");

        when(educationService.createEducation(any(Education.class))).thenReturn(savedEducation);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/templates").contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"description\":\"description1\",\"position\":\"position1\",\"from_year\":\"from_year1\",\"to_year\":\"to_year1\",\"name\":\"name1\"}"))
                .andExpect(status().isCreated()) // Expect 201 Created
                .andExpect(jsonPath("$.id").value(generatedID.toString()))
                .andExpect(jsonPath("$.description").value("description1"))
                .andExpect(jsonPath("$.position").value("position1"))
                .andExpect(jsonPath("$.from_year").value("from_year1"))
                .andExpect(jsonPath("$.to_year").value("to_year1"))
                .andExpect(jsonPath("$.name").value("name1")).andDo(MockMvcResultHandlers.print());

        verify(educationService, times(1)).createEducation(any(Education.class));
    }

    /**
     * Тест на проверку, что DELETE-запрос выполняется успешно, при удалении шаблона.
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Проверка что при удалении шаблона по id возвращается статус 204")
    public void testDeleteEducation() throws Exception {
        mockMvc.perform(delete("/educations/{id}", UUID.randomUUID()))
                .andExpect(status().isNoContent());
    }
}