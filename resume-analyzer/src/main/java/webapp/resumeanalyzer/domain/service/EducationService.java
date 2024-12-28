package webapp.resumeanalyzer.domain.service;

import java.util.List;
import webapp.resumeanalyzer.domain.model.Education;

/**
 * Интерфейс CRUD методов для сервиса сущности Education.
 */
public interface EducationService {

    //создание
    Education createEducation(Education education);

    //обновление
    Education updateEducation(String id, Education education);

    //удаление
    void deleteEducation(String id);

    //получение списка по слову-фильтру
    List<Education> loadEducationByNameFilter(String nameFilter);

    //получение по id
    Education getEducationById(String id);
}
