package infrastructure.controller;

import application.service.TemplateService;
import domain.model.Template;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST-контроллер для управления шаблонами резюме.
 * Обрабатывает HTTP-запросы, связанные с операциями CRUD для сущности {@link Template}.
 */
@RestController
@RequestMapping("/templates")
public class TemplateController {
    /**
   * Сервис для выполнения бизнес логики.
   */
    private final
            TemplateService templateService;

    /**
   * Конструктор для внедрения зависимсоти TemplateService.
   *
   * @param templateService сервис для управления шаблонами.
   */

    @Autowired
    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    /**
   * Получение списка всех шаблонов.
     *
   * @return {@link ResponseEntity} с HTTP статусом 200 (OK) и списком шаблонов. Если шаблонов нет, возвращает пустой список.
   */

    @GetMapping
    public ResponseEntity<List<Template>> getTemplates() {
        List<Template> templates = templateService.getAllTemplates();
        if (templates.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(templates);
    }

    /**
   * Метод для получения шаблона по его уникальному идентификатору.
   *
   * @param id Уникальный идентификатор.
   * @return {@link ResponseEntity} с HTTP статусом 200 (OK) и шаблоном, или 404 (Not Found), если шаблон не найден
   */

    @GetMapping("/{id}")
    public ResponseEntity<Template> getTemplateById(@PathVariable String id) {
        Template template = templateService.getTemplateById(id);
        if (template == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(template);
    }

    /**
   * Метод для создания нового шаблона.
   *
   * @param template объект {@link Template}, содержащий данные для создания.
   * @return {@link ResponseEntity} с HTTP статусом 201 (Created) и созданным шаблоном.
   */

    @PostMapping
    public ResponseEntity<Template> createTemplate(@RequestBody Template template) {
        Template savedTemplate = templateService.createTemplate(template);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTemplate);
    }

    /**
   * Метод для удаления шаблона по его уникальному идентификатору.
   *
   * @param id Уникальный идентификатор шаблона.
   * @return {@link ResponseEntity} с HTTP статусом 204 (No Content), если удаление успешно.
   */
    @DeleteMapping("/{id}")
    public ResponseEntity<Template> deleteTemplate(@PathVariable String id) {
        templateService.deleteTemplate(id);
        return ResponseEntity.noContent().build();
    }

    /**
   * Обновляет существующий шаблон.
   *
   * @param id Уникальный идентификатор, обновленного шаблона.
   * @param template Обновленный шаблон.
   * @return Обновленный шаблон.
   */

    @PutMapping
    public ResponseEntity<Template> updateTemplate(@PathVariable String id,
                                                   @RequestBody Template template) {
        try {
            templateService.updateTemplate(id, template);
            return ResponseEntity.ok(template);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
