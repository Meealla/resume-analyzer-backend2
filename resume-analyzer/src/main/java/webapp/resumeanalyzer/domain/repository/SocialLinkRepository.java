package webapp.resumeanalyzer.domain.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webapp.resumeanalyzer.domain.model.SocialLink;

/**
 * Интерфейс репозитория для выполнения CRUD-операций для сущности SocialLink в БД.
 */
@Repository
public interface SocialLinkRepository extends JpaRepository<SocialLink, UUID> {

    //метод для поиска по слову-фильтру
    @Query("SELECT s FROM SocialLink s WHERE lower(s.name) LIKE lower(concat('%', :name,'%'))")
    List<SocialLink> findAllByNameContainingIgnoreCase(@Param("name") String name);
}