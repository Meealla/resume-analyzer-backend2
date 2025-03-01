package webapp.resumegenerator.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import webapp.resumegenerator.domain.model.Department;
import webapp.resumegenerator.domain.service.TemplateService;
import webapp.resumegenerator.domain.model.Template;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webapp.resumegenerator.domain.model.Template;
import webapp.resumegenerator.domain.service.TemplateService;

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
    private final TemplateService templateService;

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
     * Получение списка шаблонов с поддержкой пагинации.
     *
     * @param page Номер страницы.
     *
     * @param size Количество элементов на странице.
     * @return {@link ResponseEntity} с HTTP статусом 200 (OK) и страницей шаблонов.
     */
    @GetMapping
    public Page<Template> getTemplates(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return templateService.getAllTemplates(pageable);
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
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Template> createTemplate(@Valid @RequestBody Template template) {
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
     *
     * @param template Обновленный шаблон.
     * @return Обновленный шаблон.
     */
    @PutMapping(("/{id}"))
    public ResponseEntity<Template> updateTemplate(@PathVariable String id,
                                                   @Valid @RequestBody Template template) {
        try {
            templateService.updateTemplate(id, template);
            return ResponseEntity.ok(template);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Извлекает шаблоны, за указанный период.
     *
     * @param startDate Начальная дата диапазона.
     *
     * @param endDate Конечная дата диапазона.
     * @return Список шаблонов за указанный период.
     */
    @GetMapping("/data")
    public List<Template> getTemplateData(@RequestParam String startDate,
                                        @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return templateService.getTemplatesByDateRange(start, end);
    }

    /**
     * Проверка существует ли шаблон с данным именем.
     *
     * @param name Имя шаблона.
     * @return Статус 200, если существует, 404 если нет.
     */
    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsTemplateName(@RequestParam String name) {
        boolean exists = templateService.isTemplateNameExist(name);
        return ResponseEntity.ok(exists);
    }

    /**
     * Создает новую версию шаблона.
     *
     * @param id Id шаблона.
     * @return {@link ResponseEntity} с HTTP статусом 201 (Created) и созданной новой версией шаблона.
     */
    @PostMapping("/{id}/version")
    public ResponseEntity<Template> createNewTemplateVersion(@PathVariable String id) {
        Template template = templateService.getTemplateById(id);
        if (template == null) {
            return ResponseEntity.notFound().build();
        }
        Template newTemplate = templateService.createNewTemplateVersion(template);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTemplate);
    }

    /**
     * Находит все версии шаблона.
     *
     * @param id Id шаблона.
     * @return {@link ResponseEntity} с HTTP статусом 200 и списком всех версий шаблона.
     */
    @GetMapping("/{id}/versions")
    public ResponseEntity<List<Template>> getTemplateVersions(@PathVariable String id) {
        Template template = templateService.getTemplateById(id);
        if (template == null) {
            return ResponseEntity.notFound().build();
        }
        List<Template> listTemplate = templateService.findAllTemplateVersionsByName(template.getName());
        return ResponseEntity.ok(listTemplate);
    }

    /**
     * Обрабатывает исключений валидации.
     *
     * @param ex Содержит ошибки валидации.
     * @return {@link ResponseEntity} с картой ошибок (поле - сообщение).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Загрузить все шаблоны",
            description = "Возвращает список всех шаблонов или список всех шаблонов по принадлежности к отделу")
    @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    @GetMapping("/all")
    public ResponseEntity<List<Template>> getAllTemplates(
            @Parameter(description = "Идентификатор отдела к которому принадлежит резюме")
            @RequestParam(value = "department", required = false) Department department) {
        if (department == null) {
            return ResponseEntity.ok(templateService.getAllTemplates());
        } else {
            return ResponseEntity.ok(templateService.getAllTemplatesByDepartment(department));
        }
    }
}