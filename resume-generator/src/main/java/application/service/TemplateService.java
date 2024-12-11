package application.service;

import domain.model.Template;
import java.time.LocalDate;
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
   **/
  public List<Template> getAllTemplates();

  /**
   * Получение шаблона по id.
   *
   * @param id Идентификатор шаблона.
   * @return Шаблон, соответствующий переданному id.
   * @throws RuntimeException Исключение, возникающее при условии что шаблон не найден.
   */

  public Template getTemplateById(String id);

  /*** Создание нового шаблона.
   *
   * @param template Объект шаблона, который будет сохранен.
   * @return Сохраненный шаблон.
   */

  public Template createTemplate(Template template);

  /**
   * Обновление сущетсвующего шаблона.
   *
   * @param id       Уникальный идентификатор шаблона, который требуется обновить.
   * @param template Новый шаблон с обновленными данными.
   * @return Обновленный шаблон.
   * @throws RuntimeException Исключение, возникающее если шаблон не найден.
   */
  public Template updateTemplate(String id, Template template);

  /**
   * Удаление шаблона.
   *
   * @param id Уникальный идентификатор шаблона.
   * @throws RuntimeException Исключение, возникающее если шаблон не найден.
   */
  public void deleteTemplate(String id);

  /**
   * Список шаблонов, дата которых находится в указанном диапазоне дат.
   *
   * @param startDate Начальная дата диапазона.
   * @param endDate   Конечная дата диапазона.
   * @return Список шаблонов, входящих в указанный диапазон дат.
   */
}
