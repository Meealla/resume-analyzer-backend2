package webapp.resumeanalyzer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import webapp.resumeanalyzer.domain.model.Experience;
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
import webapp.resumeanalyzer.controller.ExperienceTestController;
import webapp.resumeanalyzer.domain.service.ExperienceService;

/**
 * Тестовый класс для проверки функциональности {@link ExperienceTestController}.
 */
@SpringBootTest(classes = ResumeAnalyzerApplication.class)
@AutoConfigureMockMvc
public class TestForExperienceController {

    /**
     * Сервис для работы с шаблонами.
     */
    @Mock
    private ExperienceService experienceService;
    /**
     * Экземпляр контроллера {@link ExperienceTestController}, в который внедряется мокированный
     * сервис.
     */
    @InjectMocks
    private ExperienceTestController experienceTestController;

    /**
     * Объект для выполнения HTTP-запросов и проверки ответов контроллера.
     */
    private MockMvc mockMvc;

    /**
     * Инициализация MockMvc перед выполнением каждого теста.
     */
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(experienceTestController).build();
    }

    /**
     * Тест на проверку, что GET-запрос возвращает ошибку, если шаблон с указанным id не найден.
     *
     * @throws Exception Исключение, возникающее при выполнении запроса.
     */
    @Test
    @DisplayName("/experiences/{id} возвращает ошибку,если шаблон не найден")
    public void testGetExperience() throws Exception {
        mockMvc.perform(get("/experiences/{id}", UUID.randomUUID()))
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
    public void testCreateExperience() throws Exception {
        UUID generatedID = UUID.randomUUID();
        Experience inputExperience = new Experience(generatedID, "description1", "position1",
                "from_year1", "to_year1", "name1");
        Experience savedExperience = new Experience(generatedID, "description1", "position1",
                "from_year1", "to_year1", "name1");

        when(experienceService.createExperience(any(Experience.class))).thenReturn(savedExperience);

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

        verify(experienceService, times(1)).createExperience(any(Experience.class));
    }

    /**
     * Тест на проверку, что DELETE-запрос выполняется успешно, при удалении шаблона.
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Проверка что при удалении шаблона по id возвращается статус 204")
    public void testDeleteExperience() throws Exception {
        mockMvc.perform(delete("/experiences/{id}", UUID.randomUUID()))
                .andExpect(status().isNoContent());
    }
}