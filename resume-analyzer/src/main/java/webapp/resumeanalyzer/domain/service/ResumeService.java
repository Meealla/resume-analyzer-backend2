package webapp.resumeanalyzer.domain.service;

import java.util.List;
import webapp.resumeanalyzer.domain.model.Resume;

/**
 * Интерфейс CRUD методов для сервиса сущности Resume.
 */
public interface ResumeService {

    //создание
    Resume createResume(Resume resume);

    //обновление
    Resume updateResume(String id, Resume resume);

    //удаление
    void deleteResume(String id);

    //получение списка по слову-фильтру
    List<Resume> loadResumeByNameFilter(String resumeFilter);

    //получение по id
    Resume getResumeById(String id);
}