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
import webapp.resumeanalyzer.domain.model.Education;
import webapp.resumeanalyzer.domain.service.EducationService;

@RestController
@RequestMapping("/educations")
public class EducationTestController {

    private final EducationService educationService;

    @Autowired
    public EducationTestController(EducationService educationService) {
        this.educationService = educationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Education> getEducation(@PathVariable String id) {
        Education education = educationService.getEducation(id);
        if (education == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(education);
    }

    @GetMapping("/load")
    public List<Education> loadEducationByNameFilter(@RequestParam String nameFilter) {
        return educationService.loadEducationByNameFilter(nameFilter);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Education> createEducation(@Valid @RequestBody Education education) {
        Education savedEducation = educationService.createEducation(education);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEducation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Education> deleteEducation(@PathVariable String id) {
        educationService.deleteEducation(id);
        return ResponseEntity.noContent().build();
    }

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
