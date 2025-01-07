package webapp.resumeanalyzer.domain.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webapp.resumeanalyzer.domain.model.Education;

/**
 * Интерфейс репозитория для выполнения CRUD-операций для сущности Education в БД.
 */
@Repository
public interface EducationRepository extends JpaRepository<Education, UUID> {

    //метод для поиска по слову-фильтру
    @Query("SELECT e FROM Education e WHERE lower(e.name) LIKE lower(concat('%', :name,'%'))")
    List<Education> findAllByNameContainingIgnoreCase(@Param("name") String name);
}