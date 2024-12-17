package webapp.resumegenerator.application.service;

import webapp.resumegenerator.domain.model.Template;
import webapp.resumegenerator.infrastructure.repository.TemplateRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с шаблонамми резюме.
 * Данный класс представляет бизнес-логику для реализации CRUD- операций.
 */
@Service
public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository templateRepository;

    /**
     * Конструктор для внедрения зависимости репозитория.
     *
     * @param templateRepository Репозиторий для работы с шаблонами.
     */
    @Autowired
    public TemplateServiceImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    /**
     * Получение списка всех шаблонов.
     *
     * @return Список всех шаблонов
     */
    @Override
    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    /**
     * Получение шаблона по уникальному идентификатору.
     * Если шаблон не найден, выбрасывается исключение.
     *
     * @param id Идентификатор шаблона.
     * @return Шаблон с заданным идентификатором.э
     * @throws RuntimeException возникает исключение, если шаблон не найден.
     */
    @Override
    public Template getTemplateById(String id) {
        UUID uuid = generateUUID(id);
        return templateRepository.findById(uuid)
      .orElseThrow(() -> new RuntimeException("Шаблон не найден"));
    }

    /**
     * Создание нового шаблона.
     *
     * @param template Объект шаблона, который будет сохранен.
     * @return Сохраненный шаблон.
     */
    @Override
    public Template createTemplate(Template template) {
        return templateRepository.save(template);
    }

    /**
     * Обновление существующего шаблона.
     *
     * @param id       Уникальный идентификатор шаблона, который требуется обновить.
     * @param template Новый шаблон с обновленными данными.
     * @throws RuntimeException возникает исключение, если шаблон не нацден.
     */
    @Override
    public void updateTemplate(String id, Template template) {
        UUID uuid = generateUUID(id);
        if (!templateRepository.existsById(uuid)) {
            throw new RuntimeException();
        }
        template.setId(uuid);
        templateRepository.save(template);
    }

    /**
     * Удаление шаблона.
     *
     * @param id Уникальный идентификатор шаблона.
     * @throws RuntimeException возникает исключение, если шаблон не найден.
     */
    @Override
    public void deleteTemplate(String id) {
        UUID uuid = generateUUID(id);
        if (!templateRepository.existsById(uuid)) {
            throw new RuntimeException();
        }
        templateRepository.deleteById(uuid);
    }

    /**
     * Преобразует строковый идентификатор в UUID.
     *
     * @param id Строковый идентификатор.
     * @return Объект UUID.
     */
    private UUID generateUUID(String id) {
        return UUID.fromString(id);
    }
}
