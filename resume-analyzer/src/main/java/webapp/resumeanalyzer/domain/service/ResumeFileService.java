package webapp.resumeanalyzer.domain.service;


import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;
import webapp.resumeanalyzer.domain.model.ResumeFile;

/**
 * Интерфейс CRUD методов для сервиса сущности ResumeFile.
 */
public interface ResumeFileService {

    // Метод для загрузки резюме в БД
    void uploadResumeFile(ResumeFile resumeFile);

    // Метод для получения ResumeFile по названию файла
    ResumeFile getResumeFileByFilename(String filename);

    //Метод для получения ResumeFile по ид
    ResumeFile getResumeFileById(String id);

    // Метод для получения ResumeFile по имени владельца. (В дальнейшем заменить на поиск по пользователю)
    List<ResumeFile> getResumeFileByOwner(String owner);

    //Метод для удаления ResumeFile
    void deleteResumeFile (ResumeFile resumeFile);

    //Получение всех ResumeFile
    List<ResumeFile> getAllResumeFiles();

    //Создание экземпляра ResumeFile из файла
    ResumeFile createResumeFile(MultipartFile file, String ownersName) throws IOException;

    //Метод для получения файла из ResumeFile
    File getFile(String id) throws IOException;

    //Конвертация имени
    String convertCyrilic(ResumeFile resumeFile);

    //Получение заголовков для скачивания файла
    HttpHeaders getHeaders(ResumeFile resumeFile);

}
