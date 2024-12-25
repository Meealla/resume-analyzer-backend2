package webapp.resumeanalyzer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import webapp.resumeanalyzer.domain.model.SocialLink;
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
import webapp.resumeanalyzer.controller.SocialLinkTestController;
import webapp.resumeanalyzer.domain.service.SocialLinkService;

/**
 * Тестовый класс для проверки функциональности {@link webapp.socialLinkanalyzer.controller.SocialLinkTestController}.
 */
@SpringBootTest(classes = ResumeAnalyzerApplication.class)
@AutoConfigureMockMvc
public class TestForSocialLinkController {

    /**
     * Сервис для работы с шаблонами.
     */
    @Mock
    private SocialLinkService socialLinkService;
    /**
     * Экземпляр контроллера {@link webapp.socialLinkanalyzer.controller.SocialLinkTestController}, в который внедряется мокированный
     * сервис.
     */
    @InjectMocks
    private webapp.socialLinkanalyzer.controller.SocialLinkTestController socialLinkTestController;

    /**
     * Объект для выполнения HTTP-запросов и проверки ответов контроллера.
     */
    private MockMvc mockMvc;

    /**
     * Инициализация MockMvc перед выполнением каждого теста.
     */
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(socialLinkTestController).build();
    }

    /**
     * Тест на проверку, что GET-запрос возвращает ошибку, если шаблон с указанным id не найден.
     *
     * @throws Exception Исключение, возникающее при выполнении запроса.
     */
    @Test
    @DisplayName("/socialLinks/{id} возвращает ошибку,если шаблон не найден")
    public void testGetSocialLink() throws Exception {
        mockMvc.perform(get("/sociallinks/{id}", UUID.randomUUID()))
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
    public void testCreateSocialLink() throws Exception {
        UUID generatedID = UUID.randomUUID();
        SocialLink inputSocialLink = new SocialLink(generatedID, "link1", "name1");
        SocialLink savedSocialLink = new SocialLink(generatedID, "link1", "name1");

        when(socialLinkService.createSocialLink(any(SocialLink.class))).thenReturn(savedSocialLink);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/templates").contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"link\":\"link1\", \"name\":\"name1\"}"))
                .andExpect(status().isCreated()) // Expect 201 Created
                .andExpect(jsonPath("$.id").value(generatedID.toString()))
                .andExpect(jsonPath("$.link").value("link1"))
                .andExpect(jsonPath("$.name").value("name1")).andDo(MockMvcResultHandlers.print());

        verify(socialLinkService, times(1)).createSocialLink(any(SocialLink.class));
    }

    /**
     * Тест на проверку, что DELETE-запрос выполняется успешно, при удалении шаблона.
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Проверка что при удалении шаблона по id возвращается статус 204")
    public void testDeleteSocialLink() throws Exception {
        mockMvc.perform(delete("/sociallinks/{id}", UUID.randomUUID()))
                .andExpect(status().isNoContent());
    }
}