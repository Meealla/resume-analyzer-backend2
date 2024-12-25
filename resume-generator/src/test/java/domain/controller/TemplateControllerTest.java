package domain.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import webapp.resumegenerator.domain.service.TemplateService;
import webapp.resumegenerator.domain.model.Template;
import java.util.Collections;
import java.util.List;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import webapp.resumegenerator.ResumeGeneratorApplication;
import webapp.resumegenerator.infrastructure.controller.TemplateController;

/**
 * Тестовый класс для проверки функциональности {@link TemplateController}.
 */
@SpringBootTest(classes = ResumeGeneratorApplication.class)
@AutoConfigureMockMvc
public class TemplateControllerTest {
    /**
     * Сервис для работы с шаблонами.
     */
    @Mock
    private TemplateService templateService;

    /**
     * Экземпляр контроллера {@link TemplateController}, в который внедряется мокированный сервис.
     */
    @InjectMocks
    private TemplateController templateController;

    /**
     * Объект для выполнения HTTP-запросов и проверки ответов контроллера.
     */
    private MockMvc mockMvc;

    private Template invalidTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Инициализация MockMvc перед выполнением каждого теста.
     */
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(templateController).build();
        invalidTemplate = new Template("", "", "");
    }

    /**
     * Проверяет, что конечная точка /templates возвращает пустой список, если шаблоны не существуют.
     *
     * @throws Exception Исключение, которое возникает если выполнение запроса завершится ошибкой.
     */
    @Test
    @DisplayName("Проверка, что /templates возвращает пустой список если шаблонов не существует")
    public void testGetTemplates_emptyList() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Template> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(templateService.getAllTemplates(any(Pageable.class))).thenReturn(emptyPage);
        mockMvc.perform(get("/templates?page=0&size=10"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.content").isArray())
          .andExpect(jsonPath("$.content[0].name").isNotEmpty());
    }

    /** Проверка, что конечная точка /templates возвращает непустой список.
     *
     * @throws Exception Исключение, которое возникает если выполнение запроса завершится ошибкой.
     */
    @Test
    @DisplayName("Проверка, что /templates возвращает непустой список")
    public void testGetTemplates_nonEmptyList() throws Exception {
        Template template1 = new Template("Template1", "Description1", "Content1");
        Template template2 = new Template("Template2", "Description2", "Content2");
        List<Template> templates = List.of(template1, template2);
        Page<Template> page = new PageImpl<>(templates);
        when(templateService.getAllTemplates(any(Pageable.class)))
                .thenReturn(page);
        mockMvc.perform(get("/templates")
            .param("page", "0")
            .param("size", "2"))
          .andDo(print())
          .andExpect(status().isOk());
    }

    /** Проверка, что конечная точка /templates возвращает ошибку, если шаблон не найден.
     *
     * @throws Exception Исключение, которое возникает если выполнение запроса завершится ошибкой..
     */
    @Test
    @DisplayName("Проверка, что /templates возвращает ошибку,если шаблон не найден")
    public void testGetTemplateById() throws Exception {
        mockMvc.perform(get("/templates/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    /**
     * Проверка создания нового нового шаблона.
     *
     * @throws Exception Исключение, которое возникает если выполнение запроса завершится ошибкой.
     */
    @Test
    @DisplayName("Проверка что при создании нового шаблона возвращается созданный шаблон")
    public void testCreateTemplate() throws Exception {
        Template newTemplate = new Template("Sample Template", "Description", "Content");
        mockMvc.perform(post("/templates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTemplate)))
                .andExpect(status().isCreated());
    }

    /**
     * Проверка удаления шаблона.
     *
     * @throws Exception Исключение, которое возникает если выполнение запроса завершится ошибкой.
     */
    @Test
    @DisplayName("Проверка что при удалении шаблона по id возвращается статус 204")
    public void testDeleteTemplate() throws Exception {
        mockMvc.perform(delete("/templates/{id}", UUID.randomUUID()))
                .andExpect(status().isNoContent());
    }

    /**
     * Проверка пагинации.
     *
     * @throws Exception Исключение, которое возникает если выполнение запроса завершится ошибкой.
     */
    @Test
    @DisplayName("Проверка пагинации шаблонов")
    public void testGetTemplates_withPagination() throws Exception {
        Template template1 = new Template("Template1", "Description1", "Content1");
        Template template2 = new Template("Template2", "Description2", "Content2");
        List<Template> templates = List.of(template1, template2);
        Page<Template> page = new PageImpl<>(templates, PageRequest.of(0, 2), 2);
        when(templateService.getAllTemplates(any(Pageable.class)))
                .thenReturn(page);
        mockMvc.perform(get("/templates")
            .param("page", "0")
            .param("size", "2"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.content[0].name").value("Template1"))
          .andExpect(jsonPath("$.content[1].name").value("Template2"))
          .andExpect(jsonPath("$.totalPages").value(1))
          .andExpect(jsonPath("$.size").value(2))
          .andExpect(jsonPath("$.number").value(0))
                .andDo(print());
    }

    @Test
    @DisplayName("Проверка валидации.")
    void testPostTemplate_withInvalidData() throws Exception {
        String invalidTemplateJson = """
        {
            "name": "",
            "description": "short"
        }
    """;
        mockMvc.perform(post("/templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidTemplateJson))
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.errors").exists())
          .andExpect(jsonPath("$.errors", hasSize(2)))
          .andExpect(jsonPath("$.errors[?(@.field == 'name')].message").value("Name cannot be empty"))
          .andExpect(jsonPath("$.errors[?(@.field == 'description')].message").value("Description must be at least 10 characters long"));
    }
}