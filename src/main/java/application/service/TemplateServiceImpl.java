package application.service;

import domain.model.Template;
import infrastructure.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;


@Service public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;

    @Autowired public TemplateServiceImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Override public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    @Override public Template getTemplateById(String id) {
        return templateRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Шаблон не найден"));
    }

    @Override public Template createTemplate(Template template) {
        return templateRepository.save(template);
    }

    @Override public Template updateTemplate(String id, Template template) {
        if (!templateRepository.existsById(id)) {
            throw new RuntimeException();
        }
        template.setId(UUID.fromString(id));
        return templateRepository.save(template);
    }

    @Override public void deleteTemplate(String id) {
        if (!templateRepository.existsById(id)) {
            throw new RuntimeException("смапиртьолб");
        }
        templateRepository.deleteById(id);
    }
}
