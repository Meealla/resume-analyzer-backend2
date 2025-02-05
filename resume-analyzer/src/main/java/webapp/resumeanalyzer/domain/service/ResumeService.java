package webapp.resumeanalyzer.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    //получение резюме по ключевым словам
    public Page<Resume> searchResumes(String query, Pageable pageable);
}