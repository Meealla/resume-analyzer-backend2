package webapp.resumeanalyzer.infrastructure.controller;


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
import webapp.resumeanalyzer.domain.model.ResumeFile;
import webapp.resumeanalyzer.domain.service.ResumeFileService;

/**
 * Тестовый класс для проверки функциональности ResumeFile.
 */
@RestController
@RequestMapping("/resumes/files")
public class ResumeFileTestController {

    private final ResumeFileService resumeFileService;

    @Autowired
    public ResumeFileTestController(ResumeFileService resumeFileService) {
        this.resumeFileService = resumeFileService;
    }

    //@RequestParam и setOwner заменить на привязку к конкретному авторизованному пользователю
    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadResumeFile(@RequestPart("file") MultipartFile file,
                                                   @RequestParam String ownersName) throws IOException {

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

    @GetMapping("/all")
    public ResponseEntity<List<ResumeFile>> getResumeFile() {
        if (resumeFileService.getAllResumeFiles().isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(resumeFileService.getAllResumeFiles());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResumeFile(@PathVariable String id) {
        if (resumeFileService.getResumeFileById(id) == null) {
            return ResponseEntity.badRequest().build();
        } else {
            resumeFileService.deleteResumeFile(resumeFileService.getResumeFileById(id));
            return ResponseEntity.ok("Файл удален!");
        }
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadResumeFile(@PathVariable String id) throws IOException {
        ResumeFile resumeFile = resumeFileService.getResumeFileById(id);
        Resource fileResource = new FileSystemResource(resumeFileService.getFile(id));

        return ResponseEntity.ok()
                .headers(resumeFileService.getHeaders(resumeFile))
                .contentLength(resumeFileService.getFile(id).length())
                .contentType(MediaType.parseMediaType(resumeFile.getFileType()))
                .body(fileResource);
    }

}
