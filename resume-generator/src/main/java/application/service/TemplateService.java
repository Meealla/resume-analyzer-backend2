package application.service;

import domain.model.Template;

import java.util.List;

public interface TemplateService {

    public List<Template> getAllTemplates();

    public Template getTemplateById(String id);

    public Template createTemplate(Template template);

    public Template updateTemplate(String id, Template template);

    public void deleteTemplate(String id);

}
