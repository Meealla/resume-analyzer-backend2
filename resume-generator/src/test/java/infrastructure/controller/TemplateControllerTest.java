package infrastructure.controller;

import application.service.TemplateService;
import domain.model.Template;
import org.junit.jupiter.api.BeforeEach;
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
import ru.kata.project.resumegenerator.ResumeGeneratorApplication;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ResumeGeneratorApplication.class) @AutoConfigureMockMvc
public class TemplateControllerTest {
	@Mock private TemplateService templateService;

	@InjectMocks private TemplateController templateController;

	private MockMvc mockMvc;

	@BeforeEach public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(templateController).build();
	}

	@Test public void testGetTemplates_emptyList() throws Exception {

		when(templateService.getAllTemplates()).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/templates")).andExpect(status().isOk())
			.andExpect(content().json("[]")).andDo(MockMvcResultHandlers.print());
	}

	@Test public void testGetTemplates_nonEmptyList() throws Exception {

		Template template1 = new Template("Template1", "Description1", "Content1");
		Template template2 = new Template("Template2", "Description2", "Content2");

		List<Template> templates = List.of(template1, template2);


		when(templateService.getAllTemplates()).thenReturn(templates);


		mockMvc.perform(get("/templates"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].name").value("Template1"))
			.andExpect(jsonPath("$[1].name").value("Template2"))
			.andDo(MockMvcResultHandlers.print());
	}


	@Test public void testGetTemplateById() throws Exception {
		mockMvc.perform(get("/templates/{id}", UUID.randomUUID())).andExpect(status().isNotFound());
	}

	@Test public void testCreateTemplate() throws Exception {
		UUID generatedID = UUID.randomUUID();
		Template inputTemplate = new Template("Template1", "Description1", "Content1");
		Template savedTemplate = new Template("Template1", "Description1", "Content1");
		savedTemplate.setId(generatedID);


		when(templateService.createTemplate(any(Template.class))).thenReturn(savedTemplate);


		mockMvc.perform(
				MockMvcRequestBuilders.post("/templates").contentType(MediaType.APPLICATION_JSON)
					.content(
						"{\"name\":\"Template1\",\"description\":\"Description1\",\"content\":\"Content1\"}"))
			.andExpect(status().isCreated()) // Expect 201 Created
			.andExpect(jsonPath("$.name").value("Template1"))
			.andExpect(jsonPath("$.description").value("Description1"))
			.andExpect(jsonPath("$.id").value(generatedID.toString()))
			.andDo(MockMvcResultHandlers.print());


		verify(templateService, times(1)).createTemplate(any(Template.class));
	}

	@Test public void testDeleteTemplate() throws Exception {
		mockMvc.perform(delete("/templates/{id}", UUID.randomUUID()))
			.andExpect(status().isNoContent());
	}
}
