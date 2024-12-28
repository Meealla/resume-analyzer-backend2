package webapp.resumeanalyzer.controller;

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
import webapp.resumeanalyzer.domain.model.Hobby;
import webapp.resumeanalyzer.domain.service.HobbyService;

/**
 * Тестовый класс для проверки функциональности Hobby.
 */
@RestController
@RequestMapping("/hobbies")
public class HobbyTestController {

    //сервис для выполнения бизнес-логики
    private final HobbyService hobbyService;

    //конструктор для внедрения зависимости
    @Autowired
    public HobbyTestController(HobbyService hobbyService) {
        this.hobbyService = hobbyService;
    }

    //метод для получения сущности по id
    @GetMapping("/{id}")
    public ResponseEntity<Hobby> getHobby(@PathVariable String id) {
        Hobby hobby = hobbyService.getHobbyById(id);
        if (hobby == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hobby);
    }

    //метод для получения сущностей по слову-фильтру
    @GetMapping("/load")
    public List<Hobby> loadHobbyByNameFilter(@RequestParam String nameFilter) {
        return hobbyService.loadHobbyByNameFilter(nameFilter);
    }

    //метод добавления новой сущности
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Hobby> createHobby(@Valid @RequestBody Hobby hobby) {
        Hobby savedHobby = hobbyService.createHobby(hobby);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHobby);
    }

    //метод удаления сущности по id
    @DeleteMapping("/{id}")
    public ResponseEntity<Hobby> deleteHobby(@PathVariable String id) {
        hobbyService.deleteHobby(id);
        return ResponseEntity.noContent().build();
    }

    //метод обновления сущности
    @PutMapping
    public ResponseEntity<Hobby> updateHobby(@PathVariable String id,
            @Valid @RequestBody Hobby hobby) {
        try {
            hobbyService.updateHobby(id, hobby);
            return ResponseEntity.ok(hobby);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}