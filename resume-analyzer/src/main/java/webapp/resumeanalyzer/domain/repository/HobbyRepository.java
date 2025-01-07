package webapp.resumeanalyzer.domain.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webapp.resumeanalyzer.domain.model.Hobby;

/**
 * Интерфейс репозитория для выполнения CRUD-операций для сущности Hobby в БД.
 */
@Repository
public interface HobbyRepository extends JpaRepository<Hobby, UUID> {

    //метод для поиска по слову-фильтру
    @Query("SELECT h FROM Hobby h WHERE lower(h.hobby) LIKE lower(concat('%', :hobby,'%'))")
    List<Hobby> findAllByHobbyContainingIgnoreCase(@Param("hobby") String hobby);
}