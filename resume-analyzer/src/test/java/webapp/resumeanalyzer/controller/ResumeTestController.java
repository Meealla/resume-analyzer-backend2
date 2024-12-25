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
import webapp.resumeanalyzer.domain.model.Resume;
import webapp.resumeanalyzer.domain.service.ResumeService;

@RestController
@RequestMapping("/hobbies")
public class ResumeTestController {

    private final ResumeService resumeService;

    @Autowired
    public ResumeTestController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resume> getResume(@PathVariable String id) {
        Resume resume = resumeService.getResume(id);
        if (resume == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resume);
    }

    @GetMapping("/load")
    public List<Resume> loadResumeByNameFilter(@RequestParam String nameFilter) {
        return resumeService.loadResumeByNameFilter(nameFilter);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Resume> createResume(@Valid @RequestBody Resume resume) {
        Resume savedResume = resumeService.createResume(resume);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedResume);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Resume> deleteResume(@PathVariable String id) {
        resumeService.deleteResume(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Resume> updateResume(@PathVariable String id,
            @Valid @RequestBody Resume resume) {
        try {
            resumeService.updateResume(id, resume);
            return ResponseEntity.ok(resume);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}