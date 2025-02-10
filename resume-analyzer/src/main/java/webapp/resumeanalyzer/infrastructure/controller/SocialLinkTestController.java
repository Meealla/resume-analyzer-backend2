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
import webapp.resumeanalyzer.domain.model.SocialLink;
import webapp.resumeanalyzer.domain.service.SocialLinkService;

/**
 * Тестовый класс для проверки функциональности SocialLink.
 */
@Tag(name = "SocialLink API", description = "Управление социальными ссылками")
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

    @Operation(summary = "Получить социальную ссылку по ID",
            description = "Возвращает социальную ссылку по указанному идентификатору.")
    @ApiResponse(responseCode = "200", description = "Ссылка найдена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SocialLink.class)))
    @ApiResponse(responseCode = "404", description = "Ссылка не найдена")
    @GetMapping("/{id}")
    public ResponseEntity<SocialLink> getSocialLink(@PathVariable String id) {
        SocialLink socialLink = socialLinkService.getSocialLink(id);
        if (socialLink == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(socialLink);
    }

    @Operation(summary = "Загрузить социальные ссылки по фильтру",
            description = "Возвращает список социальных ссылок, соответствующих указанному фильтру.")
    @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    @GetMapping("/load")
    public List<SocialLink> loadSocialLinkByNameFilter(@RequestParam String nameFilter) {
        return socialLinkService.loadSocialLinkByNameFilter(nameFilter);
    }

    @Operation(summary = "Создать новую социальную ссылку",
            description = "Создает новую социальную ссылку и возвращает её.")
    @ApiResponse(responseCode = "201", description = "Ссылка успешно создана",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SocialLink.class)))
    @ApiResponse(responseCode = "400", description = "Некорректные данные для создания ссылки")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<SocialLink> createSocialLink(@Valid @RequestBody SocialLink socialLink) {
        SocialLink savedSocialLink = socialLinkService.createSocialLink(socialLink);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSocialLink);
    }

    @Operation(summary = "Удалить социальную ссылку по ID",
            description = "Удаляет социальную ссылку с указанным идентификатором.")
    @ApiResponse(responseCode = "204", description = "Ссылка успешно удалена")
    @ApiResponse(responseCode = "404", description = "Ссылка не найдена")
    @DeleteMapping("/{id}")
    public ResponseEntity<SocialLink> deleteSocialLink(@PathVariable String id) {
        socialLinkService.deleteSocialLink(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Обновить существующую социальную ссылку",
            description = "Обновляет данные существующей социальной ссылки по указанному идентификатору.")
    @ApiResponse(responseCode = "200", description = "Ссылка успешно обновлена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SocialLink.class)))
    @ApiResponse(responseCode = "400", description = "Некорректные данные для обновления")
    @ApiResponse(responseCode = "404", description = "Ссылка не найдена")
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