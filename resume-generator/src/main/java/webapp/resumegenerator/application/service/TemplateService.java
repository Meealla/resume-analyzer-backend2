package webapp.resumegenerator.application.service;

import webapp.resumegenerator.domain.model.Template;
import java.util.List;

/**
 * Интерфейс для сервиса шаблонов резюме.
 * В данном интерфейсе прописаны методы для работы с шаблонами резюме создание,
 * обновление, получение по id, удаление и получение всех шаблонов
 */
public interface TemplateService {
    /**
     * Получение списка всех шаблонов.
     *
     * @return Список шаблонов
     */
    public List<Template> getAllTemplates();

    /**
     * Получение шаблона по id.
     *
     * @param id Идентификатор шаблона.
     * @return Шаблон, соответствующий переданному id.
     * @throws RuntimeException Исключение, возникающее при условии что шаблон не найден.
     */
    @SuppressWarnings("checkstyle:CommentsIndentation")
    Template getTemplateById(String id);

    /*** Создание нового шаблона.
     *
     * @param template Объект шаблона, который будет сохранен.
     * @return Сохраненный шаблон.
     */
    Template createTemplate(Template template);

    /**
     * Обновление сущетсвующего шаблона.
     *
     * @param id       Уникальный идентификатор шаблона, который требуется обновить.
     * @param template Новый шаблон с обновленными данными.
     * @throws RuntimeException Исключение, возникающее если шаблон не найден.
     */
    void updateTemplate(String id, Template template);

    /**
     * Удаление шаблона.
     *
     * @param id Уникальный идентификатор шаблона.
     * @throws RuntimeException Исключение, возникающее если шаблон не найден.
     */
    void deleteTemplate(String id);
}
