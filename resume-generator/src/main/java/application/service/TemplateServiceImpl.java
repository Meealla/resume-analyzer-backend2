package application.service;

import domain.model.Template;
import infrastructure.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с шаблонамми резюме.
 * Данный класс представляет бизнесу-логику для реализации CRUD- операций.
 */
@Service
public class TemplateServiceImpl implements TemplateService {


    private final TemplateRepository templateRepository;

    /**
     * Конструктор для внедрения зависимости репозитория.
     * @param templateRepository Репозиторий для работы с шаблонами.
     */
    @Autowired
    public TemplateServiceImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    /**
     * Получение списка всех шаблонов.
     * @return Список всех шаблонов
     */

    @Override
    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    /**
     * Получение шаблона по уникальному идентификатору.
     * Если шаблон не найден, выбрасывается исключение.
     * @param id Идентификатор шаблона.
     * @return Шаблон с заданным идентификатором.э
     * @throws RuntimeException возникает исключение, если шаблон не найден.
     */

    @Override
    public Template getTemplateById(String id) {
       UUID uuid = UUID.fromString(id);
        return templateRepository.findById(uuid)
            .orElseThrow(() -> new RuntimeException("Шаблон не найден"));
    }

    /**
     * Создание нового шаблона.
     * @param template Объект шаблона, который будет сохранен.
     * @return Сохраненный шаблон.
     */

    @Override
    public Template createTemplate(Template template) {
        return templateRepository.save(template);
    }

    /**
     * Обновление существующего шаблона.
     * @param id       Уникальный идентификатор шаблона, который требуется обновить.
     * @param template Новый шаблон с обновленными данными.
     * @return Обновленный шаблон.
     * @throws RuntimeException возникает исключение, если шаблон не нацден.
     */

    @Override
    public Template updateTemplate(String id, Template template) {
        UUID uuid = UUID.fromString(id);
        if (!templateRepository.existsById(uuid)) {
            throw new RuntimeException();
        }
        template.setId(uuid);
        return templateRepository.save(template);
    }

    /**
     * Удаление шаблона.
     * @param id Уникальный идентификатор шаблона.
     * @throws RuntimeException возникает исключение, если шаблон не найден.
     */

    @Override
    public void deleteTemplate(String id) {
        UUID uuid = UUID.fromString(id);
        if (!templateRepository.existsById(uuid)) {
            throw new RuntimeException();
        }
        templateRepository.deleteById(uuid);
    }
}
