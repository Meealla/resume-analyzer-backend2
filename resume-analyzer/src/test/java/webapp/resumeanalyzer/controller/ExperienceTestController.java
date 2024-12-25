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
import webapp.resumeanalyzer.domain.model.Experience;
import webapp.resumeanalyzer.domain.service.ExperienceService;

@RestController
@RequestMapping("/experiences")
public class ExperienceTestController {

    private final ExperienceService experienceService;

    @Autowired
    public ExperienceTestController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Experience> getExperience(@PathVariable String id) {
        Experience experience = experienceService.getExperience(id);
        if (experience == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(experience);
    }

    @GetMapping("/load")
    public List<Experience> loadExperienceByNameFilter(@RequestParam String nameFilter) {
        return experienceService.loadExperienceByNameFilter(nameFilter);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Experience> createExperience(@Valid @RequestBody Experience experience) {
        Experience savedExperience = experienceService.createExperience(experience);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExperience);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Experience> deleteExperience(@PathVariable String id) {
        experienceService.deleteExperience(id);
        return ResponseEntity.noContent().build();
    }

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
