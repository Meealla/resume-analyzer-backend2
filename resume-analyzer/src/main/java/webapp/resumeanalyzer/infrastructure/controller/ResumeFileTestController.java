package webapp.resumeanalyzer.infrastructure.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import webapp.resumeanalyzer.domain.model.Resume;
import webapp.resumeanalyzer.domain.model.ResumeFile;
import webapp.resumeanalyzer.domain.service.ResumeFileService;

/**
 * Тестовый класс для проверки функциональности ResumeFile.
 */
@Tag(name = "ResumeFile API", description = "Управление файлами резюме")
@RestController
@RequestMapping("/resumes/files")
public class ResumeFileTestController {

    private final ResumeFileService resumeFileService;

    @Autowired
    public ResumeFileTestController(ResumeFileService resumeFileService) {
        this.resumeFileService = resumeFileService;
    }

    @Operation(summary = "Загрузка резюме в БД",
            description = "Загружает файл резюме в формате .pdf или .docx в БД")
    @ApiResponse(responseCode = "200", description = "Резюме успешно загружено",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resume.class)))
    @ApiResponse(responseCode = "400", description = "Недопустимый формат файла")
    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadResumeFile(@RequestPart("file") MultipartFile file,
                                                   @RequestParam String ownersName)
            throws IOException {

        if (file.getContentType().equals("application/pdf") ||
                file.getContentType()
                        .equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {

            resumeFileService.uploadResumeFile(
                    resumeFileService.createResumeFile(file, ownersName));
            return ResponseEntity.ok("Файл успешно загружен");
        } else {
            return ResponseEntity.badRequest()
                    .body("Недопустимый тип файла! Принимаются только файлы .docx и .pdf!");
        }
    }

    @Operation(summary = "Получить инфо о всех резюме файлах",
            description = "Возвращает информацию о всех файлах резюме хранящихся в БД")
    @ApiResponse(responseCode = "200", description = "Файлы найдены",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resume.class)))
    @ApiResponse(responseCode = "204", description = "Еще нет загруженных резюме")
    @GetMapping("/all")
    public ResponseEntity<List<ResumeFile>> getResumeFile() {
        if (resumeFileService.getAllResumeFiles().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(resumeFileService.getAllResumeFiles());
        }
    }

    @Operation(summary = "Удалить файл резюме по ID",
            description = "Удаление файла резюме из БД по ID")
    @ApiResponse(responseCode = "200", description = "Файл удален",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resume.class)))
    @ApiResponse(responseCode = "400", description = "Файл не найден")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResumeFile(@PathVariable String id) {
        if (resumeFileService.getResumeFileById(id) == null) {
            return ResponseEntity.badRequest().build();
        } else {
            resumeFileService.deleteResumeFile(resumeFileService.getResumeFileById(id));
            return ResponseEntity.ok("Файл удален!");
        }
    }

    @Operation(summary = "Загрузить файл резюме",
            description = "Загружает файл резюме из БД по идентификатору")
    @ApiResponse(responseCode = "200", description = "Загрузка файла",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resume.class)))
    @ApiResponse(responseCode = "400", description = "Файл не найден")
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadResumeFile(@PathVariable String id) throws IOException {
        if (resumeFileService.getResumeFileById(id) == null) {
            return ResponseEntity.badRequest().build();
        } else {

            ResumeFile resumeFile = resumeFileService.getResumeFileById(id);
            Resource fileResource = new FileSystemResource(resumeFileService.getFile(id));

            return ResponseEntity.ok()
                    .headers(resumeFileService.getHeaders(resumeFile))
                    .contentLength(resumeFileService.getFile(id).length())
                    .contentType(MediaType.parseMediaType(resumeFile.getFileType()))
                    .body(fileResource);
        }
    }
}
