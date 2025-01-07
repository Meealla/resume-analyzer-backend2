package webapp.resumeanalyzer.domain.service;

import java.util.List;
import webapp.resumeanalyzer.domain.model.Hobby;

/**
 * Интерфейс CRUD методов для сервиса сущности Hobby.
 */
public interface HobbyService {

    //создание
    Hobby createHobby(Hobby hobby);

    //обновление
    Hobby updateHobby(String id, Hobby hobby);

    //удаление
    void deleteHobby(String id);

    //получение списка по слову-фильтру
    List<Hobby> loadHobbyByNameFilter(String hobbyFilter);

    //получение по id
    Hobby getHobbyById(String id);
}