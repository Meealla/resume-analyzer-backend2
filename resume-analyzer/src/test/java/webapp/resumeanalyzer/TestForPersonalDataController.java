package webapp.resumeanalyzer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import webapp.resumeanalyzer.domain.model.PersonalData;
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
import webapp.resumeanalyzer.controller.PersonalDataTestController;
import webapp.resumeanalyzer.domain.service.PersonalDataService;

/**
 * Тестовый класс для проверки функциональности {@link PersonalDataTestController}.
 */
@SpringBootTest(classes = ResumeAnalyzerApplication.class)
@AutoConfigureMockMvc
public class TestForPersonalDataController {

    /**
     * Сервис для работы с шаблонами.
     */
    @Mock
    private PersonalDataService personalDataService;
    /**
     * Экземпляр контроллера {@link PersonalDataTestController}, в который внедряется мокированный
     * сервис.
     */
    @InjectMocks
    private PersonalDataTestController personalDataTestController;

    /**
     * Объект для выполнения HTTP-запросов и проверки ответов контроллера.
     */
    private MockMvc mockMvc;

    /**
     * Инициализация MockMvc перед выполнением каждого теста.
     */
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(personalDataTestController).build();
    }

    /**
     * Тест на проверку, что GET-запрос возвращает ошибку, если шаблон с указанным id не найден.
     *
     * @throws Exception Исключение, возникающее при выполнении запроса.
     */
    @Test
    @DisplayName("/personalData/{id} возвращает ошибку,если шаблон не найден")
    public void testGetPersonalData() throws Exception {
        mockMvc.perform(get("/personalData/{id}", UUID.randomUUID()))
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
    public void testCreatePersonalData() throws Exception {
        UUID generatedID = UUID.randomUUID();
        PersonalData inputPersonalData = new PersonalData(generatedID, "fullName1", "adress1",
                "bio1", "position1", 100L, "website1", "mail");
        PersonalData savedPersonalData = new PersonalData(generatedID, "fullName1", "adress1",
                "bio1", "position1", 100L, "website1", "mail");

        when(personalDataService.createPersonalData(any(PersonalData.class))).thenReturn(
                savedPersonalData);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/templates").contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"fullName\":\"fullName1\",\"adress\":\"adress1\",\"bio\":\"bio1\",\"position\":\"position1\",\"phone\":\"100L\",\"website\":\"website1\",\"mail\":\"mail1\"}"))
                .andExpect(status().isCreated()) // Expect 201 Created
                .andExpect(jsonPath("$.id").value(generatedID.toString()))
                .andExpect(jsonPath("$.description").value("description1"))
                .andExpect(jsonPath("$.position").value("position1"))
                .andExpect(jsonPath("$.from_year").value("from_year1"))
                .andExpect(jsonPath("$.to_year").value("to_year1"))
                .andExpect(jsonPath("$.name").value("name1")).andDo(MockMvcResultHandlers.print());

        verify(personalDataService, times(1)).createPersonalData(any(PersonalData.class));
    }

    /**
     * Тест на проверку, что DELETE-запрос выполняется успешно, при удалении шаблона.
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Проверка что при удалении шаблона по id возвращается статус 204")
    public void testDeletePersonalData() throws Exception {
        mockMvc.perform(delete("/personalData/{id}", UUID.randomUUID()))
                .andExpect(status().isNoContent());
    }
}