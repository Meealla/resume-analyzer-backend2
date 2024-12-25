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
import webapp.resumeanalyzer.domain.model.PersonalData;
import webapp.resumeanalyzer.domain.service.PersonalDataService;

@RestController
@RequestMapping("/personal_data")
public class PersonalDataTestController {

    private final PersonalDataService personalDataService;

    @Autowired
    public PersonalDataTestController(PersonalDataService personalDataService) {
        this.personalDataService = personalDataService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonalData> getPersonalData(@PathVariable String id) {
        PersonalData personalData = personalDataService.getPersonalData(id);
        if (personalData == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personalData);
    }

    @GetMapping("/load")
    public List<PersonalData> loadPersonalDataByNameFilter(@RequestParam String nameFilter) {
        return personalDataService.loadPersonalDataByNameFilter(nameFilter);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<PersonalData> createPersonalData(
            @Valid @RequestBody PersonalData personalData) {
        PersonalData savedPersonalData = personalDataService.createPersonalData(personalData);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPersonalData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PersonalData> deletePersonalData(@PathVariable String id) {
        personalDataService.deletePersonalData(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<PersonalData> updatePersonalData(@PathVariable String id,
            @Valid @RequestBody PersonalData personalData) {
        try {
            personalDataService.updatePersonalData(id, personalData);
            return ResponseEntity.ok(personalData);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
