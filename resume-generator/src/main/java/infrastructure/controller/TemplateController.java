package infrastructure.controller;

import application.service.TemplateService;
import domain.model.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController @RequestMapping("/templates") public class TemplateController {

	private final TemplateService templateService;

	@Autowired public TemplateController(TemplateService templateService) {
		this.templateService = templateService;
	}

	@GetMapping public ResponseEntity<List<Template>> getTemplates() {
		List<Template> templates = templateService.getAllTemplates();
		if (templates.isEmpty()) {
			return ResponseEntity.ok(Collections.emptyList());
		}
		return ResponseEntity.ok(templates);
	}

	@GetMapping("/{id}") public ResponseEntity<Template> getTemplateById(@PathVariable String id) {
		Template template = templateService.getTemplateById(id);
		if (template == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(template);
	}

	@PostMapping public ResponseEntity<Template> createTemplate(@RequestBody Template template) {
		Template savedTemplate = templateService.createTemplate(template);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedTemplate);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Template> deleteTemplate(@PathVariable String id) {
		templateService.deleteTemplate(id);
		return ResponseEntity.noContent().build();
	}
}
