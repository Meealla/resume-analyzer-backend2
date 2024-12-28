package webapp.resumeanalyzer.domain.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webapp.resumeanalyzer.domain.model.Resume;

/**
 * Интерфейс репозитория для выполнения CRUD-операций для сущности Resume в БД.
 */
@Repository
public interface ResumeRepository extends JpaRepository<Resume, UUID> {

    //метод для поиска по слову-фильтру
    @Query("SELECT DISTINCT r.id FROM Resume r " + "JOIN Experience e ON r.id = e.id WHERE "
            + ":keyword LIKE lower(r.experiences) OR :keyword LIKE lower(concat('%', :keyword,'%'))")
    List<Resume> findByKeywordIgnoreCase(@Param("keyword") String keyword);
}
