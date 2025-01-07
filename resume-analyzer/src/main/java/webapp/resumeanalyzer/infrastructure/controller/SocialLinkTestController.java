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
import webapp.resumeanalyzer.domain.model.SocialLink;
import webapp.resumeanalyzer.domain.service.SocialLinkService;

/**
 * Тестовый класс для проверки функциональности SocialLink.
 */
@RestController
@RequestMapping("/socialLinks")
public class SocialLinkTestController {

    //сервис для выполнения бизнес-логики
    private final SocialLinkService socialLinkService;

    //конструктор для внедрения зависимости
    @Autowired
    public SocialLinkTestController(SocialLinkService socialLinkService) {
        this.socialLinkService = socialLinkService;
    }

    //метод для получения сущности по id
    @GetMapping("/{id}")
    public ResponseEntity<SocialLink> getSocialLink(@PathVariable String id) {
        SocialLink socialLink = socialLinkService.getSocialLink(id);
        if (socialLink == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(socialLink);
    }

    //метод для получения сущностей по слову-фильтру
    @GetMapping("/load")
    public List<SocialLink> loadSocialLinkByNameFilter(@RequestParam String nameFilter) {
        return socialLinkService.loadSocialLinkByNameFilter(nameFilter);
    }

    //метод добавления новой сущности
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<SocialLink> createSocialLink(@Valid @RequestBody SocialLink socialLink) {
        SocialLink savedSocialLink = socialLinkService.createSocialLink(socialLink);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSocialLink);
    }

    //метод удаления сущности по id
    @DeleteMapping("/{id}")
    public ResponseEntity<SocialLink> deleteSocialLink(@PathVariable String id) {
        socialLinkService.deleteSocialLink(id);
        return ResponseEntity.noContent().build();
    }

    //метод обновления сущности
    @PutMapping
    public ResponseEntity<SocialLink> updateSocialLink(@PathVariable String id,
            @Valid @RequestBody SocialLink socialLink) {
        try {
            socialLinkService.updateSocialLink(id, socialLink);
            return ResponseEntity.ok(socialLink);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}