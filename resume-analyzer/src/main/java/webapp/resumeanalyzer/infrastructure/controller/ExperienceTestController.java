package webapp.resumeanalyzer.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webapp.resumeanalyzer.domain.model.Experience;
import webapp.resumeanalyzer.domain.service.ExperienceService;

/**
 * Тестовый класс для проверки функциональности Experience.
 */
@Tag(name = "Experience API", description = "Управление сущностями Experience")
@RestController
@RequestMapping("/experiences")
public class ExperienceTestController {

    //сервис для выполнения бизнес-логики
    private final ExperienceService experienceService;

    //конструктор для внедрения зависимости
    @Autowired
    public ExperienceTestController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @Operation(summary = "Получить сущность Experience по ID",
            description = "Возвращает сущность Experience по указанному идентификатору.")
    @ApiResponse(responseCode = "200", description = "Сущность найдена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Experience.class)))
    @ApiResponse(responseCode = "404", description = "Сущность не найдена")
    @GetMapping("/{id}")
    public ResponseEntity<Experience> getExperience(@PathVariable String id) {
        Experience experience = experienceService.getExperienceById(id);
        if (experience == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(experience);
    }

    @Operation(summary = "Загрузить сущности Experience по фильтру",
            description = "Возвращает список сущностей Experience, соответствующих указанному фильтру по имени.")
    @ApiResponse(responseCode = "200", description = "Успешное выполнение",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    @GetMapping("/load")
    public List<Experience> loadExperienceByNameFilter(@RequestParam String nameFilter) {
        return experienceService.loadExperienceByNameFilter(nameFilter);
    }

    @Operation(summary = "Создать новую сущность Experience",
            description = "Создает новую сущность Experience и возвращает её.")
    @ApiResponse(responseCode = "201", description = "Сущность успешно создана",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Experience.class)))
    @ApiResponse(responseCode = "400", description = "Некорректные данные для создания сущности")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Experience> createExperience(@Valid @RequestBody Experience experience) {
        Experience savedExperience = experienceService.createExperience(experience);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExperience);
    }

    @Operation(summary = "Удалить сущность Experience по ID",
            description = "Удаляет сущность Experience с указанным идентификатором.")
    @ApiResponse(responseCode = "204", description = "Сущность успешно удалена")
    @ApiResponse(responseCode = "404", description = "Сущность не найдена")
    @DeleteMapping("/{id}")
    public ResponseEntity<Experience> deleteExperience(@PathVariable String id) {
        experienceService.deleteExperience(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Обновить существующую сущность Experience",
            description = "Обновляет данные сущности Experience с указанным идентификатором.")
    @ApiResponse(responseCode = "200", description = "Сущность успешно обновлена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Experience.class)))
    @ApiResponse(responseCode = "400", description = "Некорректные данные для обновления")
    @ApiResponse(responseCode = "404", description = "Сущность не найдена")
    @PutMapping
    public ResponseEntity<Experience> updateExperience(@PathVariable String id,
            @Valid @RequestBody Experience experience) {
        try {
            experienceService.updateExperience(id, experience);
            return ResponseEntity.ok(experience);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
