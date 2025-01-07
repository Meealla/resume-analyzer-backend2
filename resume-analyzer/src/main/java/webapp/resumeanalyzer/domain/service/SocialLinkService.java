package webapp.resumeanalyzer.domain.service;

import java.util.List;
import webapp.resumeanalyzer.domain.model.SocialLink;

/**
 * Интерфейс CRUD методов для сервиса сущности SocialLink.
 */
public interface SocialLinkService {

    //создание
    SocialLink createSocialLink(SocialLink socialLink);

    //обновление
    SocialLink updateSocialLink(String id, SocialLink socialLink);

    //удаление
    void deleteSocialLink(String id);

    //получение списка по слову-фильтру
    List<SocialLink> loadSocialLinkByNameFilter(String nameFilter);

    //получение по id
    SocialLink getSocialLink(String id);
}
