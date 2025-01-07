package webapp.resumeanalyzer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import webapp.resumeanalyzer.infrastructure.controller.EducationTestController;
import webapp.resumeanalyzer.domain.model.Education;
import webapp.resumeanalyzer.domain.service.EducationService;

/**
 * Тестовый класс для проверки функциональности {@link EducationTestController}.
 */
@SpringBootTest(classes = ResumeAnalyzerApplicationTests.class)
@AutoConfigureMockMvc
public class TestForEducationController {

    private final String uriTemp = "/educations";
    private final UUID generatedId = UUID.randomUUID();
    private final Education testJson = new Education(generatedId, "testDescription", "testPosition",
            "testFromYear", "testToYear", "TestName");

    /**
     * Сервис для работы с шаблонами.
     */
    @Mock
    private EducationService educationService;
    /**
     * Экземпляр контроллера {@link EducationTestController}, в который внедряется Mock сервис.
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
    public void testGetEducationById() throws Exception {
        mockMvc.perform(get(uriTemp + "/{id}", generatedId)).andExpect(status().isNotFound());
    }

    /**
     * Тест на проверку, что POST-запрос создает новый шаблон и возвращает его с присвоенным ему
     * id.
     *
     * @throws Exception Исключение, возникающее при выполнении запроса.
     */
    @Test
    @DisplayName("Проверка что при создании нового шаблона возвращается созданный шаблон")
    public void testCreateEducation() throws Exception {
        String tempJson = new ObjectMapper().writeValueAsString(testJson);

        when(educationService.createEducation(any(Education.class))).thenReturn(testJson);

        mockMvc.perform(MockMvcRequestBuilders.post(uriTemp).contentType(MediaType.APPLICATION_JSON)
                        .content(tempJson)).andExpect(status().isCreated()) // Expect 201 Created
                .andExpect(content().json(tempJson)).andDo(MockMvcResultHandlers.print());

        verify(educationService, times(1)).createEducation(any(Education.class));
    }

    /**
     * Тест на проверку, что DELETE-запрос выполняется успешно, при удалении шаблона.
     *
     * @throws Exception Исключение, возникающее при выполнении запроса.
     */
    @Test
    @DisplayName("Проверка что при удалении шаблона по id возвращается статус 204")
    public void testDeleteEducation() throws Exception {
        mockMvc.perform(delete(uriTemp + "/{id}", generatedId))
                .andExpect(status().isNoContent());
    }
}