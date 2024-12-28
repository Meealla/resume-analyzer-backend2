package webapp.resumeanalyzer.domain.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webapp.resumeanalyzer.domain.model.Experience;

/**
 * Интерфейс репозитория для выполнения CRUD-операций для сущности Experience в БД.
 */
@Repository
public interface ExperienceRepository extends JpaRepository<Experience, UUID> {

    //метод для поиска по слову-фильтру
    @Query("SELECT e FROM Experience e WHERE lower(e.name) LIKE lower(concat('%', :name,'%'))")
    List<Experience> findAllByNameContainingIgnoreCase(@Param("name") String name);
}