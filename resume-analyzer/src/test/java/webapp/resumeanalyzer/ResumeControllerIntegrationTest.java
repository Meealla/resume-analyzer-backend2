package webapp.resumeanalyzer;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import webapp.resumeanalyzer.domain.model.PersonalData;
import webapp.resumeanalyzer.domain.model.Resume;
import webapp.resumeanalyzer.domain.repository.ResumeRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ResumeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResumeRepository resumeRepository;

    @BeforeEach
    public void setUp() {
        resumeRepository.deleteAll();

        PersonalData personalData1 = new PersonalData();
        personalData1.setFull_name("TestName1");
        personalData1.setBio("TestBio1");
        personalData1.setPosition("TestPosition1");

        PersonalData personalData2 = new PersonalData();
        personalData2.setFull_name("TestName2");
        personalData2.setBio("TestBio2");
        personalData2.setPosition("TestPosition2");

        Resume resume1 = new Resume();
        resume1.setPersonalData(personalData1);

        Resume resume2 = new Resume();
        resume2.setPersonalData(personalData2);

        resumeRepository.save(resume1);
        resumeRepository.save(resume2);
    }

    /**
     * Тест на проверку, что при поиске по ключевому слову резюме находится
     *
     * @throws Exception Исключение, возникающее при выполнении запроса.
     */
    @Test
    @DisplayName("Проверка, что возвращение по ключевому слову резюме работает - статус 200")
    public void testSearchResumes() throws Exception {
        mockMvc.perform(get("/resumes/search")
                        .param("query", "TestBio1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].personalData.fullName").value("TestName1"));
    }

    /**
     * Тест на проверку, что по запросу не найдено совпадений
     *
     * @throws Exception Исключение, возникающее при выполнении запроса.
     */
    @Test
    @DisplayName("Проверка, что возвращается ошибка, если совпадений по запросу не найдено")
    public void testSearchResumes_noResults() throws Exception {
        mockMvc.perform(get("/resumes/search")
                        .param("query", "Test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(0));
    }

    /**
     * Тест на проверку, что запрос пустой и выходит исключение
     *
     * @throws Exception Исключение, возникающее при выполнении запроса.
     */
    @Test
    @DisplayName("Проверка, что возвращается ошибка, если запрос пустой - статус 400")
    public void testSearchResumes_emptyQuery() throws Exception {
        mockMvc.perform(get("/resumes/search")
                        .param("query", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
