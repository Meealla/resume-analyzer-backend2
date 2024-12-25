package webapp.resumeanalyzer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import webapp.resumeanalyzer.domain.model.Hobby;
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
import webapp.resumeanalyzer.controller.HobbyTestController;
import webapp.resumeanalyzer.domain.service.HobbyService;

/**
 * Тестовый класс для проверки функциональности {@link HobbyTestController}.
 */
@SpringBootTest(classes = ResumeAnalyzerApplication.class)
@AutoConfigureMockMvc
public class TestForHobbyController {

    /**
     * Сервис для работы с шаблонами.
     */
    @Mock
    private HobbyService hobbyService;
    /**
     * Экземпляр контроллера {@link HobbyTestController}, в который внедряется мокированный
     * сервис.
     */
    @InjectMocks
    private HobbyTestController hobbyTestController;

    /**
     * Объект для выполнения HTTP-запросов и проверки ответов контроллера.
     */
    private MockMvc mockMvc;

    /**
     * Инициализация MockMvc перед выполнением каждого теста.
     */
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(hobbyTestController).build();
    }

    /**
     * Тест на проверку, что GET-запрос возвращает ошибку, если шаблон с указанным id не найден.
     *
     * @throws Exception Исключение, возникающее при выполнении запроса.
     */
    @Test
    @DisplayName("/hobbys/{id} возвращает ошибку,если шаблон не найден")
    public void testGetHobby() throws Exception {
        mockMvc.perform(get("/hobbies/{id}", UUID.randomUUID()))
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
    public void testCreateHobby() throws Exception {
        UUID generatedID = UUID.randomUUID();
        Hobby inputHobby = new Hobby(generatedID, "hobby1");
        Hobby savedHobby = new Hobby(generatedID, "hobby1");

        when(hobbyService.createHobby(any(Hobby.class))).thenReturn(savedHobby);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/templates").contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"hobby\":\"hobby1\"}"))
                .andExpect(status().isCreated()) // Expect 201 Created
                .andExpect(jsonPath("$.id").value(generatedID.toString()))
                .andExpect(jsonPath("$.hobby").value("hobby1")).andDo(MockMvcResultHandlers.print());

        verify(hobbyService, times(1)).createHobby(any(Hobby.class));
    }

    /**
     * Тест на проверку, что DELETE-запрос выполняется успешно, при удалении шаблона.
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Проверка что при удалении шаблона по id возвращается статус 204")
    public void testDeleteHobby() throws Exception {
        mockMvc.perform(delete("/hobbies/{id}", UUID.randomUUID()))
                .andExpect(status().isNoContent());
    }
}