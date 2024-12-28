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
import java.util.HashSet;
import java.util.Set;
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
import webapp.resumeanalyzer.controller.ResumeTestController;
import webapp.resumeanalyzer.domain.model.Education;
import webapp.resumeanalyzer.domain.model.Experience;
import webapp.resumeanalyzer.domain.model.Hobby;
import webapp.resumeanalyzer.domain.model.PersonalData;
import webapp.resumeanalyzer.domain.model.Resume;
import webapp.resumeanalyzer.domain.model.SocialLink;
import webapp.resumeanalyzer.domain.service.ResumeService;

/**
 * Тестовый класс для проверки функциональности {@link ResumeTestController}.
 */
@SpringBootTest(classes = ResumeAnalyzerApplicationTests.class)
@AutoConfigureMockMvc
public class TestForResumeController {

    private final String uriTemp = "/resumes";
    private final UUID generatedId = UUID.randomUUID();

    private final PersonalData testPersonalData = new PersonalData(UUID.randomUUID(),
            "testFullName", "testAddress", "testBio", "testPosition", 1000L, "testWebsite",
            "test@mail.com");
    private final Resume testJson = new Resume(generatedId, testPersonalData,
            createTestEducationSet(), createTestExperienceSet(), createTestSocialLinkSet(),
            createTestHobbySet());
    /**
     * Сервис для работы с шаблонами.
     */
    @Mock
    private ResumeService resumeService;
    /**
     * Экземпляр контроллера {@link ResumeTestController}, в который внедряется Mock сервис.
     */
    @InjectMocks
    private ResumeTestController resumeTestController;
    /**
     * Объект для выполнения HTTP-запросов и проверки ответов контроллера.
     */
    private MockMvc mockMvc;

    private Set<Education> createTestEducationSet() {
        Set<Education> testEducationSet = new HashSet<>();
        testEducationSet.add(new Education(UUID.randomUUID(), "testDescription1", "testPosition1",
                "testFromYear1", "testToYear1", "testName1"));
        testEducationSet.add(new Education(UUID.randomUUID(), "testDescription2", "testPosition2",
                "testFromYear2", "testToYear2", "testName2"));
        return testEducationSet;
    }

    private Set<Experience> createTestExperienceSet() {
        Set<Experience> testExperienceSet = new HashSet<>();
        testExperienceSet.add(new Experience(UUID.randomUUID(), "testDescription1", "testPosition1",
                "testFromYear1", "testToYear1", "TestName1"));
        testExperienceSet.add(new Experience(UUID.randomUUID(), "testDescription2", "testPosition2",
                "testFromYear2", "testToYear2", "TestName2"));
        return testExperienceSet;
    }

    private Set<SocialLink> createTestSocialLinkSet() {
        Set<SocialLink> testSocialLinkSet = new HashSet<>();
        testSocialLinkSet.add(new SocialLink(UUID.randomUUID(), "testLink1", "testName1"));
        testSocialLinkSet.add(new SocialLink(UUID.randomUUID(), "testLink2", "testName2"));
        return testSocialLinkSet;
    }

    private Set<Hobby> createTestHobbySet() {
        Set<Hobby> testHobbySet = new HashSet<>();
        testHobbySet.add(new Hobby(UUID.randomUUID(), "testHobby1"));
        testHobbySet.add(new Hobby(UUID.randomUUID(), "testHobby2"));
        return testHobbySet;
    }

    /**
     * Инициализация MockMvc перед выполнением каждого теста.
     */
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(resumeTestController).build();
    }

    /**
     * Тест на проверку, что GET-запрос возвращает ошибку, если шаблон с указанным id не найден.
     *
     * @throws Exception Исключение, возникающее при выполнении запроса.
     */
    @Test
    @DisplayName(uriTemp + "/{id} возвращает ошибку,если шаблон не найден")
    public void testGetResume() throws Exception {
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
    public void testCreateResume() throws Exception {
        String tempJson = new ObjectMapper().writeValueAsString(testJson);
        when(resumeService.createResume(any(Resume.class))).thenReturn(testJson);

        mockMvc.perform(MockMvcRequestBuilders.post(uriTemp).contentType(MediaType.APPLICATION_JSON)
                        .content(tempJson)).andExpect(status().isCreated()) // Expect 201 Created
                .andExpect(content().json(tempJson)).andDo(MockMvcResultHandlers.print());

        verify(resumeService, times(1)).createResume(any(Resume.class));
    }

    /**
     * Тест на проверку, что DELETE-запрос выполняется успешно, при удалении шаблона.
     *
     * @throws Exception Исключение, возникающее при выполнении запроса.
     */
    @Test
    @DisplayName("Проверка что при удалении шаблона по id возвращается статус 204")
    public void testDeleteResume() throws Exception {
        mockMvc.perform(delete(uriTemp + "/{id}", generatedId)).andExpect(status().isNoContent());
    }
}