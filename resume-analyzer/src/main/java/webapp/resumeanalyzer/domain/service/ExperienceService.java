package webapp.resumeanalyzer.domain.service;

import java.util.List;
import webapp.resumeanalyzer.domain.model.Experience;

/**
 * Интерфейс CRUD методов для сервиса сущности Experience.
 */
public interface ExperienceService {

    //создание
    Experience createExperience(Experience experience);

    //обновление
    Experience updateExperience(String id, Experience experience);

    //удаление
    void deleteExperience(String id);

    //получение списка по слову-фильтру
    List<Experience> loadExperienceByNameFilter(String nameFilter);

    //получение по id
    Experience getExperienceById(String id);
}
