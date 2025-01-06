package webapp.resumeanalyzer.infrastructure.controller;

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

    //метод для получения сущности по id
    @GetMapping("/{id}")
    public ResponseEntity<Experience> getExperience(@PathVariable String id) {
        Experience experience = experienceService.getExperienceById(id);
        if (experience == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(experience);
    }

    //метод для получения сущностей по слову-фильтру
    @GetMapping("/load")
    public List<Experience> loadExperienceByNameFilter(@RequestParam String nameFilter) {
        return experienceService.loadExperienceByNameFilter(nameFilter);
    }

    //метод добавления новой сущности
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Experience> createExperience(@Valid @RequestBody Experience experience) {
        Experience savedExperience = experienceService.createExperience(experience);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExperience);
    }

    //метод удаления сущности по id
    @DeleteMapping("/{id}")
    public ResponseEntity<Experience> deleteExperience(@PathVariable String id) {
        experienceService.deleteExperience(id);
        return ResponseEntity.noContent().build();
    }

    //метод обновления сущности
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
