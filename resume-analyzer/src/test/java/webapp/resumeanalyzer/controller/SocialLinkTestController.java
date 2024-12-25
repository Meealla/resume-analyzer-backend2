package webapp.socialLinkanalyzer.controller;

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

@RestController
@RequestMapping("/sociallinks")
public class SocialLinkTestController {

    private final SocialLinkService socialLinkService;

    @Autowired
    public SocialLinkTestController(SocialLinkService socialLinkService) {
        this.socialLinkService = socialLinkService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocialLink> getSocialLink(@PathVariable String id) {
        SocialLink socialLink = socialLinkService.getSocialLink(id);
        if (socialLink == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(socialLink);
    }

    @GetMapping("/load")
    public List<SocialLink> loadSocialLinkByNameFilter(@RequestParam String nameFilter) {
        return socialLinkService.loadSocialLinkByNameFilter(nameFilter);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<SocialLink> createSocialLink(@Valid @RequestBody SocialLink socialLink) {
        SocialLink savedSocialLink = socialLinkService.createSocialLink(socialLink);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSocialLink);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SocialLink> deleteSocialLink(@PathVariable String id) {
        socialLinkService.deleteSocialLink(id);
        return ResponseEntity.noContent().build();
    }

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