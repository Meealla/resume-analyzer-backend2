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
import webapp.resumeanalyzer.domain.model.Education;
import webapp.resumeanalyzer.domain.service.EducationService;

/**
 * Тестовый класс для проверки функциональности Education.
 */
@RestController
@RequestMapping("/educations")
public class EducationTestController {

    //сервис для выполнения бизнес-логики
    private final EducationService educationService;

    //конструктор для внедрения зависимости
    @Autowired
    public EducationTestController(EducationService educationService) {
        this.educationService = educationService;
    }

    //метод для получения сущности по id
    @GetMapping("/{id}")
    public ResponseEntity<Education> getEducation(@PathVariable String id) {
        Education education = educationService.getEducationById(id);
        if (education == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(education);
    }

    //метод для получения сущностей по слову-фильтру
    @GetMapping("/load")
    public List<Education> loadEducationByNameFilter(@RequestParam String nameFilter) {
        return educationService.loadEducationByNameFilter(nameFilter);
    }

    //метод добавления новой сущности
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Education> createEducation(@Valid @RequestBody Education education) {
        Education savedEducation = educationService.createEducation(education);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEducation);
    }

    //метод удаления сущности по id
    @DeleteMapping("/{id}")
    public ResponseEntity<Education> deleteEducation(@PathVariable String id) {
        educationService.deleteEducation(id);
        return ResponseEntity.noContent().build();
    }

    //метод обновления сущности
    @PutMapping
    public ResponseEntity<Education> updateEducation(@PathVariable String id,
            @Valid @RequestBody Education education) {
        try {
            educationService.updateEducation(id, education);
            return ResponseEntity.ok(education);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
