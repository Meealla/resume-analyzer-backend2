package webapp.resumeanalyzer.domain.service;

import java.util.List;
import webapp.resumeanalyzer.domain.model.PersonalData;

/**
 * Интерфейс CRUD методов для сервиса сущности PersonalData.
 */
public interface PersonalDataService {

    //создание
    PersonalData createPersonalData(PersonalData personalData);

    //обновление
    PersonalData updatePersonalData(String id, PersonalData personalData);

    //удаление
    void deletePersonalData(String id);

    //получение списка по слову-фильтру
    List<PersonalData> loadPersonalDataByNameFilter(String personalDataFilter);

    //получение по id
    PersonalData getPersonalDataById(String id);
}
